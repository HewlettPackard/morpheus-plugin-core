package com.morpheusdata

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.PluginDescriptorException
import com.morpheusdata.core.PluginManager
import spock.lang.Specification

import java.util.jar.JarOutputStream
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

        and: "class loader hierarchy is correct"
        thePlugin.class.classLoader.parent == manager.pluginManagerClassLoader
        manager.pluginManagerClassLoader.parent == manager.mainLoader

        when: "deregister the plugin"
        def registeredPlugin = manager.plugins.first()
        manager.deregisterPlugin(registeredPlugin)

        then:
        manager.plugins.size() == 0
    }

    void "inspectPlugin reads legacy manifest plugins with no dependencies"() {
        given:
        File jarFile = new File(this.class.classLoader.getResource("morpheus-plugin-infoblox-1.3.5-all.jar").toURI())

        when:
        def descriptor = PluginManager.inspectPlugin(jarFile)

        then:
        descriptor.schemaVersion == 1
        descriptor.pluginClass == 'com.morpheusdata.infoblox.InfobloxPlugin'
        descriptor.code == 'infoblox-plugin'
        descriptor.version == '1.3.5'
        descriptor.dependencies.empty
    }

    void "inspectPlugin reads dependency ranges without loading the plugin class"() {
        given:
        File jarFile = writeDescriptorJar("""schemaVersion: 1
pluginClass: com.example.DoesNotExistPlugin
code: morpheus-cluster-lifecycle
version: 1.3.0
dependencies:
  - code: morpheus-networking
    version:
      min: 2.4.0
      max: 3.0.0
  - code: morpheus-storage
    version:
      min: 5.1.0
""")

        when:
        def descriptor = PluginManager.inspectPlugin(jarFile)

        then:
        descriptor.pluginClass == 'com.example.DoesNotExistPlugin'
        descriptor.dependencies*.code == ['morpheus-networking', 'morpheus-storage']
        descriptor.dependencies[0].minVersion == '2.4.0'
        descriptor.dependencies[0].maxVersion == '3.0.0'
        descriptor.dependencies[1].minVersion == '5.1.0'
        descriptor.dependencies[1].maxVersion == null

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects obsolete dependency version bound names"() {
        given:
        File jarFile = writeDescriptorJar("""schemaVersion: 1
pluginClass: com.example.MyPlugin
code: my-plugin
version: 1.0.0
dependencies:
  - code: required-plugin
    version:
      minInclusive: 2.4.0
""")

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains("must use version bounds 'min' and 'max'")

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects unsupported descriptor schemas"() {
        given:
        File jarFile = writeDescriptorJar("""schemaVersion: 2
pluginClass: com.example.MyPlugin
code: my-plugin
version: 1.0.0
dependencies: []
""")

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message.contains('Unsupported plugin descriptor schemaVersion 2')

        cleanup:
        jarFile.delete()
    }

    void "inspectPlugin rejects self dependencies"() {
        given:
        File jarFile = writeDescriptorJar("""schemaVersion: 1
pluginClass: com.example.MyPlugin
code: my-plugin
version: 1.0.0
dependencies:
  - code: my-plugin
""")

        when:
        PluginManager.inspectPlugin(jarFile)

        then:
        def error = thrown(PluginDescriptorException)
        error.message == "Plugin 'my-plugin' cannot depend on itself"

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
}
