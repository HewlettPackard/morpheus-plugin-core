package com.morpheusdata.core.providers

import com.morpheusdata.model.*
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification
import spock.lang.Unroll

class ServerFirmwareProviderSpec extends Specification {

	void "firmware capability defaults match the design"() {
		given:
		def provider = new MinimalFirmwareProvider()

		expect:
		provider.supportsStandaloneUpdate()
		!provider.supportsStaging()
		provider.requiresReboot()
	}

	@Unroll
	void "firmware SPI preserves the typed signature of #name"() {
		when:
		def method = ServerFirmwareProvider.getDeclaredMethod(name, parameterTypes as Class[])

		then:
		method.genericReturnType.typeName == returnType
		method.varArgs == varargs
		!method.default

		where:
		name                         | parameterTypes                                | returnType                                                                | varargs
		'getSupportedClusterTypeCodes' | []                                            | "${Collection.name}<${String.name}>"                                       | false
		'validateFirmwareUpdate'      | [UpdateDefinition, ComputeServer[]]           | "${ServiceResponse.name}<${UpdateOperation.name}>"                         | true
		'executeFirmwareUpdate'       | [UpdateDefinition, ComputeServer[]]           | "${ServiceResponse.name}<${UpdateOperation.name}>"                         | true
		'postFirmwareUpdate'          | [UpdateDefinition, ComputeServer[]]           | "${ServiceResponse.name}<${UpdateOperation.name}>"                         | true
		'rollbackFirmwareUpdate'      | [UpdateDefinition, ComputeServer[]]           | "${ServiceResponse.name}<${UpdateOperation.name}>"                         | true
		'refreshFirmwareUpdate'       | [UpdateOperation, ComputeServer[]]            | "${ServiceResponse.name}<${UpdateOperation.name}>"                         | true
		'collectFirmwareInventory'   | [ComputeServer]                               | "${ServiceResponse.name}<${FirmwareInventory.name}>"                       | false
		'getAvailableBaselines'       | [ComputeServerGroup]                          | "${ServiceResponse.name}<${List.name}<${FirmwareBaseline.name}>>"           | false
		'uploadFirmwareBundle'        | [ComputeServerGroup, InputStream, String]     | "${ServiceResponse.name}<${FirmwareBaseline.name}>"                        | false
		'checkFirmwareConformance'    | [ComputeServer, FirmwareBaseline]             | "${ServiceResponse.name}<${FirmwareConformanceResult.name}>"               | false
		'stageFirmware'               | [ComputeServer, UpdateDefinition]             | "${ServiceResponse.name}<${FirmwareStageResult.name}>"                     | false
		'collectFirmwareLogs'         | [ComputeServer]                               | "${ServiceResponse.name}<${String.name}>"                                  | false
	}

	private static class MinimalFirmwareProvider implements ServerFirmwareProvider {
		MorpheusContext getMorpheus() { null }
		Plugin getPlugin() { null }
		String getCode() { 'test' }
		String getName() { 'Test' }
		Collection<String> getSupportedClusterTypeCodes() { [] }
		ServiceResponse<UpdateOperation> validateFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts) { ServiceResponse.success() }
		ServiceResponse<UpdateOperation> executeFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts) { ServiceResponse.success() }
		ServiceResponse<UpdateOperation> postFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts) { ServiceResponse.success() }
		ServiceResponse<UpdateOperation> rollbackFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts) { ServiceResponse.success() }
		ServiceResponse<UpdateOperation> refreshFirmwareUpdate(UpdateOperation operation, ComputeServer... hosts) { ServiceResponse.success() }
		ServiceResponse<FirmwareInventory> collectFirmwareInventory(ComputeServer host) { ServiceResponse.success() }
		ServiceResponse<List<FirmwareBaseline>> getAvailableBaselines(ComputeServerGroup cluster) { ServiceResponse.success() }
		ServiceResponse<FirmwareBaseline> uploadFirmwareBundle(ComputeServerGroup cluster, InputStream stream, String filename) { ServiceResponse.success() }
		ServiceResponse<FirmwareConformanceResult> checkFirmwareConformance(ComputeServer host, FirmwareBaseline baseline) { ServiceResponse.success() }
		ServiceResponse<FirmwareStageResult> stageFirmware(ComputeServer host, UpdateDefinition update) { ServiceResponse.success() }
		ServiceResponse<String> collectFirmwareLogs(ComputeServer host) { ServiceResponse.success() }
	}
}
