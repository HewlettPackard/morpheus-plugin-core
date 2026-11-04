package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.HostDrive
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.StorageServerNodeDisk
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

import java.lang.reflect.Modifier

class StorageProviderDriveReplacementSpec extends Specification {

	void "every facet method is abstract, so a provider implementing the facet implements all of it"() {
		when:
		def methods = StorageProviderDriveReplacement.declaredMethods.findAll { !it.synthetic }

		then:
		methods*.name as Set == ['evacuateDrive', 'unclaimDrive', 'identifyReplacement', 'claimDrive', 'refreshDriveReplacement', 'cancelDriveReplacement'] as Set
		methods.every { Modifier.isAbstract(it.modifiers) && !it.default }
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
		ServiceResponse<StorageOperation> unclaimDrive(StorageServer storageServer, StorageServerNodeDisk disk, StorageOperationRequest request) {
			return ServiceResponse.success(new StorageOperation(code: 'replace-1', state: StorageOperation.OpState.PENDING))
		}

		@Override
		ServiceResponse<StorageOperation> claimDrive(StorageServer storageServer, ComputeServer server, HostDrive drive, StorageOperationRequest request) {
			return ServiceResponse.success(new StorageOperation(code: 'replace-1', state: StorageOperation.OpState.IN_PROGRESS))
		}

		@Override
		ServiceResponse<StorageOperation> cancelDriveReplacement(StorageServer storageServer, StorageOperation operation) {
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
