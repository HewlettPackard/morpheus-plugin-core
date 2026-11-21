package com.morpheusdata.model

import spock.lang.Specification

class HostLifecycleRequestSpec extends Specification {

	void "a new request has no fields set"() {
		when:
		def request = new HostLifecycleRequest()

		then:
		request.cluster == null
		request.trigger == null
		request.evacuated == null
		request.overridden == null
		request.overriddenBy == null
		request.config == null
	}

	void "every property round-trips through its accessor"() {
		given:
		def cluster = new ComputeServerGroup()

		when:
		def request = new HostLifecycleRequest(
			cluster: cluster, trigger: 'maintenance', evacuated: true, overridden: true, overriddenBy: 'admin',
			config: [reason: 'patching']
		)

		then:
		request.cluster.is(cluster)
		request.trigger == 'maintenance'
		request.evacuated
		request.overridden
		request.overriddenBy == 'admin'
		request.config == [reason: 'patching']
	}
}
