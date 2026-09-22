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
}
