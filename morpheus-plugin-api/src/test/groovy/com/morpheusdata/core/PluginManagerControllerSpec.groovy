package com.morpheusdata.core

import com.morpheusdata.web.PluginController
import spock.lang.Specification

class PluginManagerControllerSpec extends Specification {
	void "plugin with a controller and no custom renderer registers and deregisters"() {
		given:
		def manager = new PluginManager(Mock(MorpheusContext))
		def jarFile = File.createTempFile("controller-plugin", ".jar")
		jarFile.deleteOnExit()
		def descriptor = new PluginDescriptor(PluginDescriptor.CURRENT_SCHEMA_VERSION,
			ControllerNoRendererPlugin.name, "controllerNoRendererPlugin", "1.0.0", [])

		when:
		def plugin = manager.registerPlugin(ControllerNoRendererPlugin, jarFile, descriptor)

		then:
		noExceptionThrown()
		!plugin.hasCustomRender()
		manager.plugins.contains(plugin)
		manager.renderer.templateLoaders.size() == 2

		when:
		manager.deregisterPlugin(plugin)

		then:
		manager.plugins.isEmpty()
		manager.renderer.templateLoaders.size() == 1
	}
}

class ControllerNoRendererPlugin extends Plugin {
	@Override
	String getCode() {
		return "controllerNoRendererPlugin"
	}

	@Override
	void initialize() {
		this.setControllers([[:] as PluginController])
	}

	@Override
	void onDestroy() {
	}
}
