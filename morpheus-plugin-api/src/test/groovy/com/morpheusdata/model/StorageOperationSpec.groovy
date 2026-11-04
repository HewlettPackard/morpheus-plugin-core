package com.morpheusdata.model

import spock.lang.Specification

class StorageOperationSpec extends Specification {

	void "OpState carries exactly the states the design defines"() {
		expect:
		StorageOperation.OpState.values()*.name() == ['READY', 'PENDING', 'IN_PROGRESS', 'FAILED', 'COMPLETED', 'UNKNOWN']
	}

	void "StepState carries exactly PASSED and FAILED"() {
		expect:
		StorageOperation.StepState.values()*.name() == ['PASSED', 'FAILED']
	}

	void "a new operation has an empty, mutable step list and no state"() {
		when:
		def op = new StorageOperation()
		op.opEntry << new StorageOperation.OpEntry(name: 'claiming')

		then:
		op.opEntry*.name == ['claiming']
		op.state == null
		op.overridable == null
	}

	void "a new step has an empty, mutable message list"() {
		when:
		def entry = new StorageOperation.OpEntry(name: 'evacuating')
		entry.message << 'headroom 30%'

		then:
		entry.message == ['headroom 30%']
		entry.state == null
	}

	void "every property round-trips through its accessor"() {
		given:
		def started = new Date(1000L)
		def completed = new Date(2000L)

		when:
		def op = new StorageOperation(
			code: 'node-role-7', name: 'Edit Node Role', refType: 'StorageServerNode', refId: 7L,
			state: StorageOperation.OpState.FAILED, statusMessage: 'quorum', percentComplete: 0,
			overridable: true, retryAfterSeconds: 30L, startedAt: started, completedAt: completed,
			opEntry: [new StorageOperation.OpEntry(name: 'reserving', message: ['no headroom'], state: StorageOperation.StepState.FAILED)]
		)

		then:
		op.code == 'node-role-7'
		op.name == 'Edit Node Role'
		op.refType == 'StorageServerNode'
		op.refId == 7L
		op.state == StorageOperation.OpState.FAILED
		op.statusMessage == 'quorum'
		op.percentComplete == 0
		op.overridable
		op.retryAfterSeconds == 30L
		op.startedAt == started
		op.completedAt == completed
		op.opEntry.size() == 1
		op.opEntry[0].message == ['no headroom']
		op.opEntry[0].state == StorageOperation.StepState.FAILED
	}
}
