package com.morpheusdata

import com.morpheusdata.core.PluginDescriptor
import com.morpheusdata.core.PluginLoadResolver
import com.morpheusdata.core.RequiredPlugin
import spock.lang.Specification

class PluginLoadResolverSpec extends Specification {

	void "orders required plugins before dependents"() {
		given:
		def networking = descriptor('networking', '2.4.0')
		def storage = descriptor('storage', '5.1.0')
		def cluster = descriptor('cluster', '1.0.0', [
			requiredPlugin('networking', '2.4.0', '3.0.0'),
			requiredPlugin('storage')
		])

		when:
		def plan = PluginLoadResolver.resolve([cluster, storage, networking])

		then:
		plan.loadOrder*.code == ['storage', 'networking', 'cluster']
		plan.failures.isEmpty()
	}

	void "reports missing required plugins without blocking independent plugins"() {
		given:
		def missingRequirement = descriptor('cluster', '1.0.0', [requiredPlugin('networking')])
		def independent = descriptor('independent', '1.0.0')

		when:
		def plan = PluginLoadResolver.resolve([missingRequirement, independent])

		then:
		plan.loadOrder*.code == ['independent']
		plan.getFailure(missingRequirement) == "Plugin 'cluster' requires plugin 'networking', but it is not available"
	}

	void "reports incompatible required plugin versions"() {
		given:
		def networking = descriptor('networking', '3.0.0')
		def cluster = descriptor('cluster', '1.0.0', [requiredPlugin('networking', '2.4.0', '3.0.0')])

		when:
		def plan = PluginLoadResolver.resolve([cluster, networking])

		then:
		plan.loadOrder*.code == ['networking']
		plan.getFailure(cluster) ==
			"Plugin 'cluster' requires plugin 'networking' version >= 2.4.0 and < 3.0.0, but version 3.0.0 is available"
	}

	void "detects required plugin cycles and reports affected dependents"() {
		given:
		def pluginA = descriptor('plugin-a', '1.0.0', [requiredPlugin('plugin-b')])
		def pluginB = descriptor('plugin-b', '1.0.0', [requiredPlugin('plugin-a')])
		def dependent = descriptor('dependent', '1.0.0', [requiredPlugin('plugin-a')])
		def independent = descriptor('independent', '1.0.0')

		when:
		def plan = PluginLoadResolver.resolve([dependent, pluginA, pluginB, independent])

		then:
		plan.loadOrder*.code == ['independent']
		plan.getFailure(pluginA).contains('Required plugin cycle detected')
		plan.getFailure(pluginB).contains('Required plugin cycle detected')
		plan.getFailure(dependent).contains("requires plugin 'plugin-a', which could not be loaded")
	}

	void "accepts required plugins already loaded by another manager"() {
		given:
		def networking = descriptor('networking', '2.5.0')
		def cluster = descriptor('cluster', '1.0.0', [requiredPlugin('networking', '2.4.0', '3.0.0')])

		when:
		def plan = PluginLoadResolver.resolve([cluster], [networking])

		then:
		plan.loadOrder*.code == ['cluster']
		plan.failures.isEmpty()
	}

	private static PluginDescriptor descriptor(String code, String version, List<RequiredPlugin> requiredPlugins = []) {
		new PluginDescriptor(1, "com.example.${code.replace('-', '')}.Plugin", code, version, requiredPlugins)
	}

	private static RequiredPlugin requiredPlugin(String code, String minVersion = null, String maxVersion = null) {
		new RequiredPlugin(code, minVersion, maxVersion)
	}
}
