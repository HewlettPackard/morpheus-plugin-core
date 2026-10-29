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

A jar manifest must also be created to tell the PluginManager which class to load for the plugin. For example:

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

Required plugins are runtime relationships between installed Morpheus plugins. Declare them separately through the Morpheus Gradle plugin:

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

When `requiredPlugins` is configured, the Gradle plugin requires `Plugin-Class`, `Plugin-Version`, and `Morpheus-Code` in `META-INF/MANIFEST.MF` and writes the requirements as compact JSON in the `Morpheus-Required-Plugins` attribute:

```text
Morpheus-Required-Plugins: [{"code":"morpheus-networking","minVersion":"2.4.0","maxVersion":"3.0.0"},{"code":"morpheus-storage","minVersion":"5.1.0"}]
```

Manifest writers may fold long attribute values across continuation lines; the manifest API reconstructs the complete JSON value when reading it. Required-plugin version bounds are optional and use dotted numeric versions. The supported range is `minVersion <= installedVersion < maxVersion`. Existing `plugin.yml` and `META-INF/morpheus.properties` identity descriptors remain supported, but required plugins are declared only through `Morpheus-Required-Plugins` in the JAR manifest.

At load time, Morpheus orders plugins so every required plugin is initialized before the plugins that require it. Plugins with missing or incompatible required plugins are not initialized, independent plugins can still load, and required-plugin cycles are reported as load errors.

### Library Dependencies

Libraries your plugin requires, such as cloud SDKs, are scoped to the plugin classpath. The `PluginManager` creates a new classpath for each plugin loaded so classes in one plugin do not interfere with Morpheus or other loaded plugins.
