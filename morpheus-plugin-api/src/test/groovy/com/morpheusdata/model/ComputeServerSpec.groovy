package com.morpheusdata.model

import spock.lang.Specification

class ComputeServerSpec extends Specification {

	void "maxSockets defaults to null"() {
		when:
		def server = new ComputeServer()

		then:
		server.maxSockets == null
	}

	void "maxSockets can be set and retrieved"() {
		given:
		def server = new ComputeServer()

		when:
		server.maxSockets = 2L

		then:
		server.maxSockets == 2L
		server.isDirty('maxSockets')
	}

	void "systemUuid defaults to null"() {
		when:
		def server = new ComputeServer()

		then:
		server.systemUuid == null
	}

	void "systemUuid is stored unchanged and marked dirty"() {
		given:
		def server = new ComputeServer()

		when:
		server.systemUuid = 'ABC'

		then:
		server.systemUuid == 'ABC'
		server.isDirty('systemUuid')
	}
}
