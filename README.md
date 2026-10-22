# Morpheus Core Plugin Library

This library provides a common framework for extending the Morpheus Ops Orchestration Tool. This includes providing plugin extensions for adding various integrations such as Cloud Integrations, Service Level Integrations or Providers for things like DNS, IPAM, Config Management, UI Extensions, Etc.

The plugin architecture requires Morpheus 5 or later.


## Creating a Plugin

Ensure you are using a version of the plugin that is compatible with your version of Morpheus. See the releases tab for more info.

### Setup a new Gradle project

The plugin API is published to Maven Central, include it in your gradle project:

```gradle
dependencies {
	compileOnly 'com.morpheusdata:morpheus-plugin-api:1.1.1'
}
```
### Documentation

Please refer to the [official documentation](https://developer.morpheusdata.com/docs) for more up to date guides on how to create a plugin.

Maintainers can follow the [release process](docs/release-process.md) to publish plugin core releases.

### Implement your plugin

Create and implement the `Plugin` class.

### Packaging

Plugins are packaged by using the `shadowJar` gradle task. This task bundles all dependencies required for the plugin.

Keep the plugin identity and class-loading metadata in the standard JAR manifest:

build.gradle
```groovy
jar {
	manifest {
		attributes(
			'Plugin-Class': 'com.example.ClusterLifecyclePlugin',
			'Plugin-Version': version,
			'Morpheus-Code': 'morpheus-cluster-lifecycle'
		)
	}
}
```

Plugin prerequisites are runtime relationships between installed Morpheus plugins, not Java library dependencies. Declare them separately through the Morpheus Gradle plugin:

```groovy
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
```

When `requiredPlugins` is configured, the Gradle plugin requires `Plugin-Class`, `Plugin-Version`, and `Morpheus-Code` in the JAR manifest and combines them with these prerequisites to generate `META-INF/morpheus-plugin.yml`. Dependency version bounds are optional and use dotted numeric versions. The supported range is `minVersion <= installedVersion < maxVersion`. Existing `plugin.yml`, `META-INF/morpheus.properties`, and `META-INF/MANIFEST.MF` descriptors remain supported.

### Library Dependencies

Libraries your plugin requires, such as cloud SDKs, are scoped to the plugin classpath. The `PluginManager` creates a new classpath for each plugin loaded so classes in one plugin do not interfere with Morpheus or other loaded plugins.
