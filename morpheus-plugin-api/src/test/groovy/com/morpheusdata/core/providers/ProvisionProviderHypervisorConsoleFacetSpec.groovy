package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.HypervisorConsoleConnection
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class ProvisionProviderHypervisorConsoleFacetSpec extends Specification {

	void "legacy hypervisor console facet is deprecated"() {
		when:
		def annotation = ProvisionProvider.HypervisorConsoleFacet.getAnnotation(Deprecated)

		then:
		annotation != null
		annotation.since() == '1.3.4'
		!annotation.forRemoval()
	}

	void "typed hypervisor console facet returns structured connection response"() {
		given:
		def provider = new TestHypervisorConsoleFacetV2Provider()

		when:
		ServiceResponse<HypervisorConsoleConnection> response = provider.getNoVNCConsoleConnection(new ComputeServer())

		then:
		response.success
		response.data.url == 'wss://console.example/vnc'
		response.data.protocols == ['plain.kubevirt.io']
		response.data.toMap() == [url: 'wss://console.example/vnc', protocols: ['plain.kubevirt.io']]
	}

	static class TestHypervisorConsoleFacetV2Provider implements ProvisionProvider.HypervisorConsoleFacetV2 {
		@Override
		ServiceResponse<HypervisorConsoleConnection> getNoVNCConsoleConnection(ComputeServer server) {
			def connection = new HypervisorConsoleConnection()
			connection.url = 'wss://console.example/vnc'
			connection.protocols = ['plain.kubevirt.io']
			return ServiceResponse.success(connection)
		}
	}
}
