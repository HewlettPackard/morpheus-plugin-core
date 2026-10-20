package com.morpheusdata.core.providers

import com.morpheusdata.model.StorageConfigurationConstraints
import com.morpheusdata.model.StorageOperation
import com.morpheusdata.model.StorageServer
import com.morpheusdata.request.StorageOperationRequest
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification
import spock.lang.Unroll

class StorageProviderConfigurationSpec extends Specification {

	void "getConfigurationConstraints defaults to an unsuccessful 'not supported' response"() {
		when:
		def result = new DefaultConfigurationProvider().getConfigurationConstraints(new StorageServer(), [:])

		then:
		!result.success
		result.error == 'Configuration not supported'
		result.data == null
	}

	@Unroll
	void "applyConfiguration defaults to an unsuccessful 'not supported' response for #opType"() {
		when:
		def result = new DefaultConfigurationProvider().applyConfiguration(new StorageServer(), new StorageOperationRequest(opType: opType))

		then:
		!result.success
		result.error == 'Configuration not supported'
		result.data == null

		where:
		opType << StorageOperationRequest.OpType.values()
	}

	void "refreshConfiguration defaults to an unsuccessful 'not supported' response"() {
		when:
		def result = new DefaultConfigurationProvider().refreshConfiguration(new StorageServer(), new StorageOperation(code: 'op-1'))

		then:
		!result.success
		result.error == 'Configuration not supported'
	}

	void "a provider overriding the facet is dispatched to"() {
		given:
		def provider = new ImplementedConfigurationProvider()
		def request = new StorageOperationRequest(
			opType: StorageOperationRequest.OpType.DRY_RUN,
			config: [rcAddress: '10.0.0.2'],
			previousConfig: [rcAddress: '10.0.0.1'],
			changedKeys: ['rcAddress'])

		when:
		def constraints = provider.getConfigurationConstraints(new StorageServer(), [:])
		def applied = provider.applyConfiguration(new StorageServer(), request)
		def refreshed = provider.refreshConfiguration(new StorageServer(), applied.data)

		then:
		constraints.success
		constraints.data.fixedFields == [adminAddress: 'set at install']
		applied.success
		applied.data.opEntry*.name == ['node-1', 'node-2']
		applied.data.opEntry*.message.flatten() == ['rcAddress 10.0.0.1 -> 10.0.0.2', 'rcAddress 10.0.0.1 -> 10.0.0.2']
		refreshed.data.state == StorageOperation.OpState.IN_PROGRESS
		refreshed.data.opEntry*.state == [StorageOperation.StepState.PASSED, null]
	}

	private static class DefaultConfigurationProvider implements StorageProviderConfiguration {}

	private static class ImplementedConfigurationProvider implements StorageProviderConfiguration {
		@Override
		ServiceResponse<StorageConfigurationConstraints> getConfigurationConstraints(StorageServer storageServer, Map opts) {
			return ServiceResponse.success(new StorageConfigurationConstraints(fixedFields: [adminAddress: 'set at install']))
		}

		@Override
		ServiceResponse<StorageOperation> applyConfiguration(StorageServer storageServer, StorageOperationRequest request) {
			def operation = new StorageOperation(code: 'config-1', state: StorageOperation.OpState.READY)
			operation.opEntry = ['node-1', 'node-2'].collect { node ->
				new StorageOperation.OpEntry(name: node, message: request.changedKeys.collect { "${it} ${request.previousConfig[it]} -> ${request.config[it]}".toString() })
			}
			return ServiceResponse.success(operation)
		}

		@Override
		ServiceResponse<StorageOperation> refreshConfiguration(StorageServer storageServer, StorageOperation operation) {
			operation.state = StorageOperation.OpState.IN_PROGRESS
			operation.opEntry[0].state = StorageOperation.StepState.PASSED
			return ServiceResponse.success(operation)
		}
	}
}
