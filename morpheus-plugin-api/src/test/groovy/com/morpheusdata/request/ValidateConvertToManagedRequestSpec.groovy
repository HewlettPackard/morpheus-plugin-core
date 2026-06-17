package com.morpheusdata.request

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.Instance
import spock.lang.Specification

class ValidateConvertToManagedRequestSpec extends Specification {

	void "default construction initializes fields to null"() {
		when:
		def request = new ValidateConvertToManagedRequest()

		then:
		request.server == null
		request.osTypeId == null
		request.instance == null
		request.opts == null
		request.sshHost == null
		request.sshUsername == null
		request.sshPassword == null
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

	void "setOsTypeId / getOsTypeId roundtrip"() {
		given:
		def request = new ValidateConvertToManagedRequest()

		when:
		request.setOsTypeId(42L)

		then:
		request.getOsTypeId() == 42L
		request.osTypeId == 42L
	}

	void "osTypeId field access and setter are consistent"() {
		given:
		def request = new ValidateConvertToManagedRequest()

		when:
		request.osTypeId = 99L

		then:
		request.getOsTypeId() == 99L
		request.osTypeId == 99L
	}

	void "setInstance / getInstance roundtrip"() {
		given:
		def request = new ValidateConvertToManagedRequest()
		def instance = new Instance(id: 5)

		when:
		request.setInstance(instance)

		then:
		request.getInstance().is(instance)
		request.getInstance().id == 5
	}

	void "instance field access and setter are consistent"() {
		given:
		def request = new ValidateConvertToManagedRequest()
		def instance = new Instance(id: 10)

		when:
		request.instance = instance

		then:
		request.getInstance().is(instance)
		request.instance.is(instance)
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

	void "typed ssh getters and setters use class properties"() {
		given:
		def request = new ValidateConvertToManagedRequest()

		when:
		request.setSshHost('10.0.0.1')
		request.setSshUsername('admin')
		request.setSshPassword('secret')

		then:
		request.getSshHost() == '10.0.0.1'
		request.getSshUsername() == 'admin'
		request.getSshPassword() == 'secret'
		request.sshHost == '10.0.0.1'
		request.sshUsername == 'admin'
		request.sshPassword == 'secret'
	}
}
