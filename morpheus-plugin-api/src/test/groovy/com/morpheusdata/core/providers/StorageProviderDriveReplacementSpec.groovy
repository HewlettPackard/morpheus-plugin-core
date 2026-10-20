package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.HostDrive
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.StorageServerNodeDisk
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification
import spock.lang.Unroll

class StorageProviderDriveReplacementSpec extends Specification {

	@Unroll
	void "evacuateDrive defaults to an unsuccessful 'not supported' response for #opType"() {
		when:
		def result = new DefaultDriveReplacementProvider().evacuateDrive(new StorageServer(), new StorageServerNodeDisk(), new StorageOperationRequest(opType: opType))

		then:
		!result.success
		result.error == 'Drive replacement not supported'
		result.data == null

		where:
		opType << StorageOperationRequest.OpType.values()
	}

	@Unroll
	void "unclaimDrive defaults to an unsuccessful 'not supported' response for #opType"() {
		when:
		def result = new DefaultDriveReplacementProvider().unclaimDrive(new StorageServer(), new StorageServerNodeDisk(), new StorageOperationRequest(opType: opType))

		then:
		!result.success
		result.error == 'Drive replacement not supported'
		result.data == null

		where:
		opType << StorageOperationRequest.OpType.values()
	}

	void "identifyReplacement defaults to an unsuccessful 'not supported' response"() {
		when:
		def result = new DefaultDriveReplacementProvider().identifyReplacement(new StorageServer(), new StorageServerNodeDisk(), [new HostDrive()], [:])

		then:
		!result.success
		result.error == 'Drive replacement not supported'
		result.data == null
	}

	@Unroll
	void "claimDrive defaults to an unsuccessful 'not supported' response for #opType"() {
		when:
		def result = new DefaultDriveReplacementProvider().claimDrive(new StorageServer(), new ComputeServer(), new HostDrive(), new StorageOperationRequest(opType: opType))

		then:
		!result.success
		result.error == 'Drive replacement not supported'
		result.data == null

		where:
		opType << StorageOperationRequest.OpType.values()
	}

	void "refreshDriveReplacement and cancelDriveReplacement default to an unsuccessful 'not supported' response"() {
		given:
		def provider = new DefaultDriveReplacementProvider()
		def operation = new StorageOperation(code: 'op-1')

		when:
		def refreshed = provider.refreshDriveReplacement(new StorageServer(), operation)
		def cancelled = provider.cancelDriveReplacement(new StorageServer(), operation)

		then:
		!refreshed.success
		refreshed.error == 'Drive replacement not supported'
		!cancelled.success
		cancelled.error == 'Drive replacement not supported'
	}

	void "a provider overriding the facet is dispatched to"() {
		given:
		def provider = new ImplementedDriveReplacementProvider()
		def removed = new StorageServerNodeDisk(uniqueId: 'wwn-old')
		def reseated = new HostDrive()
		reseated.uniqueId = 'wwn-old'
		def fitted = new HostDrive()
		fitted.uniqueId = 'wwn-new'

		when:
		def evacuated = provider.evacuateDrive(new StorageServer(), removed, new StorageOperationRequest(opType: StorageOperationRequest.OpType.DRY_RUN))
		def identified = provider.identifyReplacement(new StorageServer(), removed, [reseated, fitted], [:])
		def refreshed = provider.refreshDriveReplacement(new StorageServer(), evacuated.data)

		then:
		evacuated.success
		evacuated.data.opEntry*.name == ['evacuating', 'unclaiming', 'awaiting-swap', 'identifying', 'claiming', 'rebuilding']
		identified.success
		identified.data.uniqueId == 'wwn-new'
		refreshed.success
		refreshed.data.state == StorageOperation.OpState.IN_PROGRESS
		refreshed.data.opEntry.find { it.name == 'awaiting-swap' }.state == null
	}

	private static class DefaultDriveReplacementProvider implements StorageProviderDriveReplacement {}

	private static class ImplementedDriveReplacementProvider implements StorageProviderDriveReplacement {
		@Override
		ServiceResponse<StorageOperation> evacuateDrive(StorageServer storageServer, StorageServerNodeDisk disk, StorageOperationRequest request) {
			def operation = new StorageOperation(code: 'replace-1', refType: 'StorageServerNodeDisk', state: StorageOperation.OpState.READY)
			operation.opEntry = ['evacuating', 'unclaiming', 'awaiting-swap', 'identifying', 'claiming', 'rebuilding'].collect {
				new StorageOperation.OpEntry(name: it, state: StorageOperation.StepState.PASSED)
			}
			return ServiceResponse.success(operation)
		}

		@Override
		ServiceResponse<HostDrive> identifyReplacement(StorageServer storageServer, StorageServerNodeDisk removed, Collection<HostDrive> discovered, Map opts) {
			def replacement = discovered.find { it.uniqueId != removed.uniqueId }
			return replacement ? ServiceResponse.success(replacement) : ServiceResponse.error('Drive was reseated, not replaced')
		}

		@Override
		ServiceResponse<StorageOperation> refreshDriveReplacement(StorageServer storageServer, StorageOperation operation) {
			operation.state = StorageOperation.OpState.IN_PROGRESS
			operation.opEntry.find { it.name == 'awaiting-swap' }.state = null
			return ServiceResponse.success(operation)
		}
	}
}
