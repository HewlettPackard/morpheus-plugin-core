package com.morpheusdata.core.providers

import com.morpheusdata.model.HostDrive
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.StorageServerNode
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

import java.lang.reflect.Modifier

class StorageProviderDriveAdditionSpec extends Specification {

	void "every facet method is abstract, so a provider implementing the facet implements all of it"() {
		when:
		def methods = StorageProviderDriveAddition.declaredMethods.findAll { !it.synthetic }

		then:
		methods*.name as Set == ['listUnclaimedDrives', 'addDrives', 'refreshDriveAddition'] as Set
		methods.every { Modifier.isAbstract(it.modifiers) && !it.default }
	}

	void "a provider overriding the facet is dispatched to"() {
		given:
		def provider = new ImplementedDriveAdditionProvider()

		when:
		def drives = provider.listUnclaimedDrives(new StorageServer(), new StorageServerNode(), [:])
		def refreshed = provider.refreshDriveAddition(new StorageServer(), new StorageOperation(code: 'add-1'))

		then:
		drives.success
		drives.data*.uniqueId == ['wwn-1']
		refreshed.success
		refreshed.data.code == 'add-1'
		refreshed.data.percentComplete == 50
		refreshed.data.opEntry*.name == ['claiming', 'rebalancing']
	}

	private static class ImplementedDriveAdditionProvider implements StorageProviderDriveAddition {
		@Override
		ServiceResponse<Collection<HostDrive>> listUnclaimedDrives(StorageServer storageServer, StorageServerNode node, Map opts) {
			def drive = new HostDrive()
			drive.uniqueId = 'wwn-1'
			return ServiceResponse.success([drive] as Collection<HostDrive>)
		}

		@Override
		ServiceResponse<StorageOperation> addDrives(StorageServer storageServer, StorageServerNode node, Collection<String> driveIds, StorageOperationRequest request) {
			return ServiceResponse.success(new StorageOperation(code: 'add-1', state: StorageOperation.OpState.READY))
		}

		@Override
		ServiceResponse<StorageOperation> refreshDriveAddition(StorageServer storageServer, StorageOperation operation) {
			operation.state = StorageOperation.OpState.IN_PROGRESS
			operation.percentComplete = 50
			operation.opEntry = [
				new StorageOperation.OpEntry(name: 'claiming', state: StorageOperation.StepState.PASSED),
				new StorageOperation.OpEntry(name: 'rebalancing')
			]
			return ServiceResponse.success(operation)
		}
	}
}
