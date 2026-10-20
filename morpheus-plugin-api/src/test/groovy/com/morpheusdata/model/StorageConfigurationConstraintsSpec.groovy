package com.morpheusdata.model

import spock.lang.Specification

class StorageConfigurationConstraintsSpec extends Specification {

	void "fixedFields defaults to an empty mutable map"() {
		given:
		def constraints = new StorageConfigurationConstraints()

		when:
		constraints.fixedFields.adminAddress = 'set at install'

		then:
		constraints.fixedFields == [adminAddress: 'set at install']
	}

	void "fixedFields round-trips through its accessor and keeps key order"() {
		when:
		def constraints = new StorageConfigurationConstraints(fixedFields: [rcAddress: 'partner bound', dataAddress: 'in use by hosts'])

		then:
		constraints.fixedFields.keySet() as List == ['rcAddress', 'dataAddress']
		constraints.fixedFields.rcAddress == 'partner bound'
	}
}
