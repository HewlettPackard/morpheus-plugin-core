package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.Instance
import com.morpheusdata.model.Workload
import com.morpheusdata.request.ResizeRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

/**
 * The V1 resize facets' {@code hasConfigChanges} hooks default to {@code false}, so V1 providers that don't
 * override them keep today's reconfigure behavior.
 */
class ResizeFacetHasConfigChangesSpec extends Specification {

	void "HostProvisionProvider.ResizeFacet hasConfigChanges defaults to false"() {
		given:
		def provider = new V1HostResizeProvider()
		def resizeRequest = new ResizeRequest(maxMemory: 4096L, maxCores: 2L, coresPerSocket: 1L)

		expect:
		!provider.hasConfigChanges(new ComputeServer(), resizeRequest, [config: [bootDelay: '5']])
	}

	void "WorkloadProvisionProvider.ResizeFacet hasConfigChanges defaults to false"() {
		given:
		def provider = new V1WorkloadResizeProvider()
		def resizeRequest = new ResizeRequest(maxMemory: 4096L, maxCores: 2L, coresPerSocket: 1L)

		expect:
		!provider.hasConfigChanges(new Instance(), new Workload(), resizeRequest, [config: [bootDelay: '5']])
	}

	static class V1HostResizeProvider implements HostProvisionProvider.ResizeFacet {
		@Override
		ServiceResponse resizeServer(ComputeServer server, ResizeRequest resizeRequest, Map opts) {
			return ServiceResponse.success()
		}
	}

	static class V1WorkloadResizeProvider implements WorkloadProvisionProvider.ResizeFacet {
		@Override
		ServiceResponse resizeWorkload(Instance instance, Workload workload, ResizeRequest resizeRequest, Map opts) {
			return ServiceResponse.success()
		}
	}
}
