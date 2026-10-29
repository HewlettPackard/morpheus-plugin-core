package com.morpheusdata.views

import spock.lang.Specification

class HandlebarsRendererSpec extends Specification {
	void "template loaders can be added and removed by class loader"() {
		given:
		def renderer = new HandlebarsRenderer()
		def pluginClassLoader = new URLClassLoader([] as URL[])

		when:
		renderer.addTemplateLoader(pluginClassLoader)

		then:
		renderer.templateLoaders.size() == 2

		when:
		renderer.removeTemplateLoader(pluginClassLoader)

		then:
		renderer.templateLoaders.size() == 1
	}
}
