package com.morpheusdata.model

import spock.lang.Specification

class NetworkRouterSpec extends Specification {
	void "setUniqueId stores value and marks property dirty"() {
		given:
		def router = new NetworkRouter()

		when:
		router.setUniqueId('router-unique-1')

		then:
		router.getUniqueId() == 'router-unique-1'
		router.isDirty('uniqueId')
		router.getDirtyPropertyValues().get('uniqueId') == 'router-unique-1'
	}
}
