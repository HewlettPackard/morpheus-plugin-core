package com.morpheusdata.request

import com.morpheusdata.model.Process
import spock.lang.Specification

class StorageOperationRequestSpec extends Specification {

	void "OpType carries exactly UPDATE and DRY_RUN"() {
		expect:
		StorageOperationRequest.OpType.values()*.name() == ['UPDATE', 'DRY_RUN']
	}

	void "a new request has no fields set"() {
		when:
		def request = new StorageOperationRequest()

		then:
		request.opType == null
		request.process == null
		request.overridden == null
		request.overriddenBy == null
		request.config == null
		request.previousConfig == null
		request.changedKeys == null
	}

	void "every property round-trips through its accessor"() {
		given:
		def process = new Process()

		when:
		def request = new StorageOperationRequest(
			opType: StorageOperationRequest.OpType.UPDATE, process: process, overridden: true, overriddenBy: 'admin',
			config: [adminAddress: '10.0.0.2'], previousConfig: [adminAddress: '10.0.0.1'], changedKeys: ['adminAddress']
		)

		then:
		request.opType == StorageOperationRequest.OpType.UPDATE
		request.process.is(process)
		request.overridden
		request.overriddenBy == 'admin'
		request.config == [adminAddress: '10.0.0.2']
		request.previousConfig == [adminAddress: '10.0.0.1']
		request.changedKeys == ['adminAddress']
	}
}
