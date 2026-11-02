package com.morpheusdata.gradle

import groovy.json.JsonSlurper
import org.gradle.testkit.runner.GradleRunner
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

	void "requiredPlugins DSL writes JSON to the JAR manifest"() {
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
			minVersion = '${minimum}'
			maxVersion = '${maximum}'
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
		JarFile jarFile = new JarFile(new File(projectDir, 'build/libs/descriptor-test-1.3.0.jar'))
		def requiredPlugins = new JsonSlurper().parseText(
			jarFile.manifest.mainAttributes.getValue('Morpheus-Required-Plugins')
		)

		then:
		requiredPlugins == [
			[code: 'morpheus-networking', minVersion: minimum, maxVersion: maximum],
			[code: 'morpheus-storage', minVersion: '5.1.0']
		]
		jarFile.getEntry('META-INF/morpheus-plugin.yml') == null
		jarFile.manifest.mainAttributes.getValue('Plugin-Class') == 'com.example.ClusterLifecyclePlugin'
		jarFile.manifest.mainAttributes.getValue('Plugin-Version') == '1.3.0'
		jarFile.manifest.mainAttributes.getValue('Morpheus-Code') == 'morpheus-cluster-lifecycle'

		cleanup:
		jarFile?.close()

		where:
		minimum          | maximum
		'2.4.0'          | '3.0.0'
		'2.4.0.dev'      | '3.0.0-SNAPSHOT'
	}

	void "manifest generation rejects a plugin requiring itself"() {
		given:
		def requiredPlugins = new MorpheusRequiredPlugins()
		requiredPlugins.plugin('my-plugin')

		when:
		requiredPlugins.toManifestJson('my-plugin')

		then:
		def error = thrown(org.gradle.api.GradleException)
		error.message == "Plugin 'my-plugin' cannot require itself"
	}

	void "manifest generation rejects an invalid required plugin version range"() {
		given:
		def requiredPlugins = new MorpheusRequiredPlugins()
		requiredPlugins.plugin('required-plugin') {
			minVersion = minimum
			maxVersion = maximum
		}

		when:
		requiredPlugins.toManifestJson('my-plugin')

		then:
		def error = thrown(org.gradle.api.GradleException)
		error.message == "Required plugin 'required-plugin' must have minVersion lower than maxVersion"

		where:
		minimum          | maximum
		'2.0.0'          | '1.0.0'
		'2.0.0.dev'      | '2.0-SNAPSHOT'
		'2.0.0-SNAPSHOT' | '1.9.9.dev'
	}

	void "manifest generation rejects malformed qualified bound #version"() {
		given:
		def requiredPlugins = new MorpheusRequiredPlugins()
		requiredPlugins.plugin('required-plugin') {
			minVersion = version
		}

		when:
		requiredPlugins.toManifestJson('my-plugin')

		then:
		def error = thrown(org.gradle.api.GradleException)
		error.message.contains('minVersion must be a dotted numeric version')

		where:
		version << ['dev', '1.0..dev', '1.0-', '1.0-SNAPSHOT!']
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
