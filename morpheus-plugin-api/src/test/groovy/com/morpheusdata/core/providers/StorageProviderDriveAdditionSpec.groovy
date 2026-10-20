package com.morpheusdata.core.providers

import com.morpheusdata.model.HostDrive
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.StorageServerNode
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class StorageProviderDriveAdditionSpec extends Specification {

	void "listUnclaimedDrives defaults to an unsuccessful 'not supported' response"() {
		when:
		def result = new DefaultDriveAdditionProvider().listUnclaimedDrives(new StorageServer(), new StorageServerNode(), [:])

		then:
		!result.success
		result.error == 'Drive addition not supported'
	}

	void "addDrives defaults to an unsuccessful 'not supported' response for #opType"() {
		when:
		def result = new DefaultDriveAdditionProvider().addDrives(new StorageServer(), new StorageServerNode(), ['d1'], new StorageOperationRequest(opType: opType))

		then:
		!result.success
		result.error == 'Drive addition not supported'
		result.data == null

		where:
		opType << StorageOperationRequest.OpType.values()
	}

	void "refreshDriveAddition defaults to an unsuccessful 'not supported' response"() {
		when:
		def result = new DefaultDriveAdditionProvider().refreshDriveAddition(new StorageServer(), new StorageOperation(code: 'op-1'))

		then:
		!result.success
		result.error == 'Drive addition not supported'
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

	private static class DefaultDriveAdditionProvider implements StorageProviderDriveAddition {}

	private static class ImplementedDriveAdditionProvider implements StorageProviderDriveAddition {
		@Override
		ServiceResponse<Collection<HostDrive>> listUnclaimedDrives(StorageServer storageServer, StorageServerNode node, Map opts) {
			def drive = new HostDrive()
			drive.uniqueId = 'wwn-1'
			return ServiceResponse.success([drive] as Collection<HostDrive>)
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
