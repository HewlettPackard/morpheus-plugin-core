package com.morpheusdata

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.PluginDescriptorException
import com.morpheusdata.core.PluginManager
import com.morpheusdata.core.PluginResolutionException
import groovy.json.JsonOutput
import spock.lang.Specification

import java.util.jar.Attributes
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import java.util.zip.ZipEntry

class PluginManagerSpec extends Specification {
    void "Register and Deregister plugin from jar"() {
        given: "A MorpheusContext & jar"
        def context = Mock(MorpheusContext)
        String pathToJar = this.class.classLoader.getResource("morpheus-plugin-infoblox-1.3.5-all.jar").path

        when: "create a plugin manager"
        def manager = new PluginManager(context)

        then:
        manager.plugins.size() == 0
        noExceptionThrown()

        when: "register a plugin by jar"
        manager.registerPlugin(pathToJar)

        then:
        manager.plugins.size() == 1
        def thePlugin = manager.plugins.first()

        and: "the classloaders are not the same"
        manager.mainLoader != thePlugin.class.classLoader
        manager.pluginManagerClassLoader != thePlugin.class.classLoader
        thePlugin.descriptor.code == 'infoblox-plugin'

        and: "class loader hierarchy is correct"
        thePlugin.class.classLoader.parent == manager.pluginManagerClassLoader
        manager.pluginManagerClassLoader.parent == manager.mainLoader

        when: "deregister the plugin"
        def registeredPlugin = manager.plugins.first()
        manager.deregisterPlugin(registeredPlugin)

        then:
        manager.plugins.size() == 0
    }

    void "inspectPlugin reads legacy manifest plugins with no required plugins"() {
        given:
        File jarFile = new File(this.class.classLoader.getResource("morpheus-plugin-infoblox-1.3.5-all.jar").toURI())

        when:
        def descriptor = PluginManager.inspectPlugin(jarFile)

        then:
        descriptor.schemaVersion == 1
        descriptor.pluginClass == 'com.morpheusdata.infoblox.InfobloxPlugin'
        descriptor.code == 'infoblox-plugin'
        descriptor.version == '1.3.5'
        descriptor.requiredPlugins.empty
    }

    void "inspectPlugin reads required plugin ranges without loading the plugin class"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.DoesNotExistPlugin',
            'morpheus-cluster-lifecycle',
            '1.3.0',
            JsonOutput.toJson([
                [code: 'morpheus-networking', minVersion: '2.4.0', maxVersion: '3.0.0'],
                [code: 'morpheus-storage', minVersion: '5.1.0']
            ])
        )

        when:
        def descriptor = PluginManager.inspectPlugin(jarFile)

        then:
        descriptor.pluginClass == 'com.example.DoesNotExistPlugin'
        descriptor.requiredPlugins*.code == ['morpheus-networking', 'morpheus-storage']
        descriptor.requiredPlugins[0].minVersion == '2.4.0'
        descriptor.requiredPlugins[0].maxVersion == '3.0.0'
        descriptor.requiredPlugins[1].minVersion == '5.1.0'
        descriptor.requiredPlugins[1].maxVersion == null

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin does not read the removed separate descriptor"() {
        given:
        File jarFile = writeDescriptorJar("""schemaVersion: 1
pluginClass: com.example.MyPlugin
code: my-plugin
version: 1.0.0
""")

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains('No plugin descriptor found')

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects obsolete required plugin version shapes"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.MyPlugin',
            'my-plugin',
            '1.0.0',
            JsonOutput.toJson([[code: 'required-plugin', version: [minInclusive: '2.4.0']]])
        )

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains("must use version fields 'minVersion' and 'maxVersion'")

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects malformed required plugin JSON"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.MyPlugin',
            'my-plugin',
            '1.0.0',
            '[{"code":"required-plugin"}'
        )

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains('Unable to parse META-INF/MANIFEST.MF Morpheus-Required-Plugins JSON')

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects invalid required plugin JSON entries"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.MyPlugin',
            'my-plugin',
            '1.0.0',
            requiredPluginsJson
        )

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains(expectedError)

        cleanup:
        jarFile.delete()

        where:
        requiredPluginsJson                                                        | expectedError
        JsonOutput.toJson([code: 'required-plugin'])                              | 'must contain a JSON array'
        JsonOutput.toJson([[:]])                                                   | "missing required 'Morpheus-Required-Plugins[0].code' field"
        JsonOutput.toJson([[code: 'required-plugin'], [code: 'required-plugin']])  | "declares duplicate required plugin 'required-plugin'"
        JsonOutput.toJson([[code: 'required-plugin', minimumVersion: '1.0.0']])    | "contains unsupported field 'minimumVersion'"
        JsonOutput.toJson([[code: 'required-plugin', minVersion: 'version-one']]) | 'minVersion must be a dotted numeric version'
        JsonOutput.toJson([[code: 'required-plugin', minVersion: '2.0.0',
                            maxVersion: '1.0.0']])                                  | 'must have minVersion lower than maxVersion'
    }

    void "inspectPlugin requires manifest identity when required plugins are declared"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.MyPlugin',
            null,
            '1.0.0',
            JsonOutput.toJson([])
        )

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains('missing required Morpheus-Code attribute')

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects a plugin requiring itself"() {
        given:
        File jarFile = writeManifestDescriptorJar(
            'com.example.MyPlugin',
            'my-plugin',
            '1.0.0',
            JsonOutput.toJson([[code: 'my-plugin']])
        )

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message == "Plugin 'my-plugin' cannot require itself"

        cleanup:
        jarFile.delete()
    }

    void "registerPlugin refuses to load a plugin with a missing required plugin"() {
        given:
        def context = Mock(MorpheusContext)
        def manager = new PluginManager(context)
        File jarFile = writeManifestDescriptorJar(
            'com.example.DoesNotExistPlugin',
            'my-plugin',
            '1.0.0',
            JsonOutput.toJson([[code: 'missing-plugin']])
        )

        when:
        manager.registerPlugin(jarFile.absolutePath)

        then:
        def error = thrown(PluginResolutionException)
        error.message == "Plugin 'my-plugin' requires plugin 'missing-plugin', but it is not available"
        manager.plugins.empty

        cleanup:
        jarFile.delete()
    }

    private static File writeDescriptorJar(String descriptor) {
        File jarFile = File.createTempFile('plugin-descriptor', '.jar')
        def output = new JarOutputStream(new FileOutputStream(jarFile))
        output.putNextEntry(new ZipEntry('META-INF/morpheus-plugin.yml'))
        output.write(descriptor.bytes)
        output.closeEntry()
        output.close()
        return jarFile
    }

    private static File writeManifestDescriptorJar(String pluginClass, String code, String version,
                                                   String requiredPluginsJson = null) {
        File jarFile = File.createTempFile('plugin-manifest', '.jar')
        def manifest = new Manifest()
        def attributes = manifest.mainAttributes
        attributes.put(Attributes.Name.MANIFEST_VERSION, '1.0')
        attributes.putValue('Plugin-Class', pluginClass)
        if(code != null) {
            attributes.putValue('Morpheus-Code', code)
        }
        if(version != null) {
            attributes.putValue('Plugin-Version', version)
        }
        if(requiredPluginsJson != null) {
            attributes.putValue('Morpheus-Required-Plugins', requiredPluginsJson)
        }
        new JarOutputStream(new FileOutputStream(jarFile), manifest).close()
        return jarFile
    }
}
