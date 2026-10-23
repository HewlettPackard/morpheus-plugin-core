package com.morpheusdata.core.providers

import spock.lang.Specification

class ProvisionProviderReconfigureOptionTypesSpec extends Specification {

	void "reconfigure option types default to an empty collection"() {
		given:
		// A spy of the interface runs the real default method
		ProvisionProvider provider = Spy(ProvisionProvider)

		when:
		def optionTypes = provider.getReconfigureOptionTypes()

		then:
		optionTypes != null
		optionTypes.isEmpty()
	}
}
