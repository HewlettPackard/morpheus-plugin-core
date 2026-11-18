package com.morpheusdata.core

import spock.lang.Specification

import java.util.jar.Attributes
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

class PluginManagerLoadFailureSpec extends Specification {
	private File missingClassJar() {
		File jar = File.createTempFile("missing-class-plugin", ".jar")
		jar.deleteOnExit()
		Manifest manifest = new Manifest()
		manifest.mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0")
		manifest.mainAttributes.putValue("Plugin-Class", "com.example.DoesNotExistPlugin")
		manifest.mainAttributes.putValue("Plugin-Version", "1.0.0")
		manifest.mainAttributes.putValue("Morpheus-Code", "missingClassPlugin")
		new JarOutputStream(new FileOutputStream(jar), manifest).close()
		return jar
	}

	void "registerPluginOrThrow reports the cause when the plugin class cannot be loaded"() {
		given:
		def manager = new PluginManager(Mock(MorpheusContext))

		when:
		manager.registerPluginOrThrow(missingClassJar().absolutePath)

		then:
		def e = thrown(PluginLoadException)
		e.message.contains("com.example.DoesNotExistPlugin")
		e.cause instanceof ClassNotFoundException
		manager.plugins.isEmpty()
	}

	void "registerPlugin keeps returning null when the plugin class cannot be loaded"() {
		given:
		def manager = new PluginManager(Mock(MorpheusContext))

		expect:
		manager.registerPlugin(missingClassJar().absolutePath) == null
		manager.plugins.isEmpty()
	}

	void "registerPluginOrThrow still rejects an invalid descriptor with its original exception"() {
		given:
		def manager = new PluginManager(Mock(MorpheusContext))

		when:
		manager.registerPluginOrThrow(new File("does-not-exist.jar").absolutePath)

		then:
		thrown(PluginDescriptorException)
	}
}
