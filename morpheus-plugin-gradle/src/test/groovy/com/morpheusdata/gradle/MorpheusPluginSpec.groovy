package com.morpheusdata.gradle

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

import java.util.jar.JarFile

class MorpheusPluginSpec extends Specification {

	File projectDir

	def setup() {
		projectDir = File.createTempDir('morpheus-plugin-gradle', 'spec')
	}

	def cleanup() {
		projectDir?.deleteDir()
	}

	void "requiredPlugins DSL generates canonical YAML from existing manifest attributes"() {
		given:
		new File(projectDir, 'settings.gradle').text = "rootProject.name = 'descriptor-test'\n"
		new File(projectDir, 'build.gradle').text = """
plugins {
	id 'java'
	id 'com.morpheusdata.morpheus-plugin-gradle'
}

version = '1.3.0'

jar {
	manifest {
		attributes(
			'Plugin-Class': 'com.example.ClusterLifecyclePlugin',
			'Plugin-Version': version,
			'Morpheus-Code': 'morpheus-cluster-lifecycle'
		)
	}
}

morpheusPlugin {
	requiredPlugins {
		plugin('morpheus-networking') {
			minVersion = '2.4.0'
			maxVersion = '3.0.0'
		}
		plugin('morpheus-storage') {
			minVersion = '5.1.0'
		}
	}
}
"""

		when:
		GradleRunner.create()
			.withProjectDir(projectDir)
			.withPluginClasspath()
			.withArguments('jar', '--stacktrace')
			.build()
		File descriptorFile = new File(projectDir, 'build/generated/morpheus-plugin/META-INF/morpheus-plugin.yml')
		JarFile jarFile = new JarFile(new File(projectDir, 'build/libs/descriptor-test-1.3.0.jar'))

		then:
		descriptorFile.text == """schemaVersion: 1
pluginClass: 'com.example.ClusterLifecyclePlugin'
code: 'morpheus-cluster-lifecycle'
version: '1.3.0'
dependencies:
  - code: 'morpheus-networking'
    version:
      min: '2.4.0'
      max: '3.0.0'
  - code: 'morpheus-storage'
    version:
      min: '5.1.0'
"""
		jarFile.getEntry('META-INF/morpheus-plugin.yml') != null
		jarFile.manifest.mainAttributes.getValue('Plugin-Class') == 'com.example.ClusterLifecyclePlugin'
		jarFile.manifest.mainAttributes.getValue('Plugin-Version') == '1.3.0'
		jarFile.manifest.mainAttributes.getValue('Morpheus-Code') == 'morpheus-cluster-lifecycle'

		cleanup:
		jarFile?.close()
	}

	void "descriptor generation rejects a self dependency"() {
		given:
		def project = ProjectBuilder.builder().withProjectDir(projectDir).build()
		def task = project.tasks.create('generateDescriptor', GenerateMorpheusPluginDescriptor)
		task.schemaVersion = 1
		task.pluginClass = 'com.example.MyPlugin'
		task.code = 'my-plugin'
		task.version = '1.0.0'
		task.dependencies = [new MorpheusPluginDependency(code: 'my-plugin')]
		task.outputFile = new File(projectDir, 'morpheus-plugin.yml')

		when:
		task.generate()

		then:
		def error = thrown(org.gradle.api.GradleException)
		error.message == "Plugin 'my-plugin' cannot depend on itself"
	}

	void "requiredPlugins DSL requires plugin identity in the JAR manifest"() {
		given:
		new File(projectDir, 'settings.gradle').text = "rootProject.name = 'descriptor-test'\n"
		new File(projectDir, 'build.gradle').text = """
plugins {
	id 'java'
	id 'com.morpheusdata.morpheus-plugin-gradle'
}

version = '1.3.0'

jar {
	manifest {
		attributes(
			'Plugin-Class': 'com.example.ClusterLifecyclePlugin',
			'Plugin-Version': version
		)
	}
}

morpheusPlugin {
	requiredPlugins {
		plugin('morpheus-networking')
	}
}
"""

		when:
		def result = GradleRunner.create()
			.withProjectDir(projectDir)
			.withPluginClasspath()
			.withArguments('jar', '--stacktrace')
			.buildAndFail()

		then:
		result.output.contains("JAR manifest attribute 'Morpheus-Code' must be specified when morpheusPlugin.requiredPlugins is configured")
	}
}
