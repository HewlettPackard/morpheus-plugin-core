package com.morpheusdata.model

import spock.lang.Specification

class HostLifecycleResultSpec extends Specification {

	void "a new result has no fields set"() {
		when:
		def result = new HostLifecycleResult()

		then:
		result.overridable == null
		result.health == null
		result.resyncPercent == null
		result.retryAfterSeconds == null
	}

	void "every property round-trips through its accessor"() {
		when:
		def result = new HostLifecycleResult(overridable: true, health: 'resyncing', resyncPercent: 42, retryAfterSeconds: 30L)

		then:
		result.overridable
		result.health == 'resyncing'
		result.resyncPercent == 42
		result.retryAfterSeconds == 30L
	}
}
