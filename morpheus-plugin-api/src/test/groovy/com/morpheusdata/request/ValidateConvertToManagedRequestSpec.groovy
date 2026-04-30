package com.morpheusdata.request

import com.morpheusdata.model.ComputeServer
import spock.lang.Specification

class ValidateConvertToManagedRequestSpec extends Specification {

	void "default construction initializes fields to null"() {
		when:
		def request = new ValidateConvertToManagedRequest()

		then:
		request.server == null
		request.opts == null
	}

	void "setServer / getServer roundtrip"() {
		given:
		def request = new ValidateConvertToManagedRequest()
		def server = new ComputeServer(id: 7)

		when:
		request.setServer(server)

		then:
		request.getServer().is(server)
		request.getServer().id == 7
	}

	void "setOpts / getOpts roundtrip"() {
		given:
		def request = new ValidateConvertToManagedRequest()
		def opts = [sshHost: '192.168.1.1', sshUsername: 'root']

		when:
		request.setOpts(opts)

		then:
		request.getOpts() == [sshHost: '192.168.1.1', sshUsername: 'root']
	}

	void "field access and setter access are consistent"() {
		given:
		def server = new ComputeServer(id: 1)
		def opts = [sshKeyPairId: 99]
		def request = new ValidateConvertToManagedRequest()

		when:
		request.server = server
		request.opts = opts

		then:
		request.getServer().is(server)
		request.getOpts() == opts
	}
}
