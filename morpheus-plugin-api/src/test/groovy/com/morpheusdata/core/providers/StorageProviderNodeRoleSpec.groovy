package com.morpheusdata.core.providers

import com.morpheusdata.model.NodeRoleOption
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.StorageServerNode
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

import java.lang.reflect.Modifier

class StorageProviderNodeRoleSpec extends Specification {

	void "every facet method is abstract, so a provider implementing the facet implements all of it"() {
		when:
		def methods = StorageProviderNodeRole.declaredMethods.findAll { !it.synthetic }

		then:
		methods*.name as Set == ['getNodeRoleConstraints', 'applyNodeRole', 'refreshNodeRole'] as Set
		methods.every { Modifier.isAbstract(it.modifiers) && !it.default }
	}

	void "a provider overriding the facet is dispatched to and receives the request unchanged"() {
		given:
		def provider = new ImplementedNodeRoleProvider()
		def request = new StorageOperationRequest(opType: StorageOperationRequest.OpType.DRY_RUN, overridden: false)

		when:
		def options = provider.getNodeRoleConstraints(new StorageServer(), new StorageServerNode(), [:])
		def applied = provider.applyNodeRole(new StorageServer(), new StorageServerNode(), 'storage', ['d1', 'd2'], request)

		then:
		options.success
		options.data*.role == ['symmetric', 'controller']
		options.data.find { it.role == 'controller' }.available == false
		applied.success
		applied.data.state == StorageOperation.OpState.READY
		applied.data.opEntry*.name == ['reserving', 'claiming', 'applying', 'resyncing']
		provider.lastRequest.is(request)
		provider.lastRole == 'storage'
		provider.lastDriveIds == ['d1', 'd2']
	}

	private static class ImplementedNodeRoleProvider implements StorageProviderNodeRole {
		StorageOperationRequest lastRequest
		String lastRole
		Collection<String> lastDriveIds

		@Override
		ServiceResponse<Collection<NodeRoleOption>> getNodeRoleConstraints(StorageServer storageServer, StorageServerNode node, Map opts) {
			return ServiceResponse.success([
				new NodeRoleOption(role: 'symmetric', name: 'Symmetric', available: true, minimumDrives: 1),
				new NodeRoleOption(role: 'controller', name: 'Controller', available: false, reason: 'quorum', minimumDrives: 0)
			] as Collection<NodeRoleOption>)
		}

		@Override
		ServiceResponse<StorageOperation> applyNodeRole(StorageServer storageServer, StorageServerNode node, String role, Collection<String> driveIds, StorageOperationRequest request) {
			lastRequest = request
			lastRole = role
			lastDriveIds = driveIds
			def op = new StorageOperation(code: 'node-role-1', state: StorageOperation.OpState.READY)
			op.opEntry = ['reserving', 'claiming', 'applying', 'resyncing'].collect {
				new StorageOperation.OpEntry(name: it, state: StorageOperation.StepState.PASSED)
			}
			return ServiceResponse.success(op)
		}

		@Override
		ServiceResponse<StorageOperation> refreshNodeRole(StorageServer storageServer, StorageOperation operation) {
			return ServiceResponse.success(operation)
		}
	}
}
