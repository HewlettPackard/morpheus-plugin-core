package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.HostLifecycleRequest
import com.morpheusdata.model.HostLifecycleResult
import com.morpheusdata.model.StorageServer
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification
import spock.lang.Unroll

class StorageProviderHostLifecycleSpec extends Specification {

	private static final List<String> METHOD_NAMES = [
		'validateHostMaintenance', 'enterHostMaintenance', 'exitHostMaintenance',
		'validateHostPowerOff', 'prepareHostPowerOff', 'resumeHostAfterPowerOn',
		'validateHostRemoval', 'releaseHost'
	]

	@Unroll
	void "#methodName defaults to a successful no-op"() {
		given:
		def provider = new UnimplementedHostLifecycleProvider()

		when:
		def response = provider."$methodName"(new StorageServer(), new ComputeServer(), new HostLifecycleRequest())

		then:
		response.success
		response.data == null

		where:
		methodName << METHOD_NAMES
	}

	void "the facet declares exactly the eight lifecycle methods"() {
		when:
		def methods = StorageProviderHostLifecycle.declaredMethods.findAll { !it.synthetic }

		then:
		methods*.name as Set == METHOD_NAMES as Set
		methods.every { it.default }
	}

	void "a provider overriding the facet is dispatched to and receives the request unchanged"() {
		given:
		def provider = new ImplementedHostLifecycleProvider()
		def storageServer = new StorageServer()
		def host = new ComputeServer()
		def request = new HostLifecycleRequest(trigger: 'maintenance', overridden: false)

		when:
		def response = provider.enterHostMaintenance(storageServer, host, request)

		then:
		response.success
		response.data.health == 'healthy'
		provider.lastStorageServer.is(storageServer)
		provider.lastHost.is(host)
		provider.lastRequest.is(request)
	}

	private static class UnimplementedHostLifecycleProvider implements StorageProviderHostLifecycle {
	}

	private static class ImplementedHostLifecycleProvider implements StorageProviderHostLifecycle {
		StorageServer lastStorageServer
		ComputeServer lastHost
		HostLifecycleRequest lastRequest

		@Override
		ServiceResponse<HostLifecycleResult> enterHostMaintenance(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
			lastStorageServer = storageServer
			lastHost = host
			lastRequest = request
			return ServiceResponse.success(new HostLifecycleResult(health: 'healthy'))
		}
	}
}
