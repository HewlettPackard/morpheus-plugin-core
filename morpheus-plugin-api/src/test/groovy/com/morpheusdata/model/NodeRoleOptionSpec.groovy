package com.morpheusdata.model

import spock.lang.Specification

class NodeRoleOptionSpec extends Specification {

	void "every property round-trips through its accessor"() {
		when:
		def option = new NodeRoleOption(role: 'controller', name: 'Controller', available: false, reason: 'would leave 2 controllers', minimumDrives: 0)

		then:
		option.role == 'controller'
		option.name == 'Controller'
		option.available == false
		option.reason == 'would leave 2 controllers'
		option.minimumDrives == 0
	}

	void "role accepts each StorageServerNodePersona name as its code"() {
		expect:
		new NodeRoleOption(role: persona.name()).role == persona.name()

		where:
		persona << StorageServerNodePersona.values()
	}
}
