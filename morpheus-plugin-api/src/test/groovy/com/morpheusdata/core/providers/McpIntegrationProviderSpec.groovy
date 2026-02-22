package com.morpheusdata.core.providers

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.AccountIntegration
import com.morpheusdata.model.Icon
import com.morpheusdata.model.mcp.McpCapabilities
import com.morpheusdata.model.mcp.McpIntegrationType
import com.morpheusdata.model.mcp.McpToolCallRequest
import com.morpheusdata.model.mcp.McpToolCallResponse
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class McpIntegrationProviderSpec extends Specification {

	void "default category and option types are MCP specific"() {
		given:
		def provider = new BaseMcpProvider()

		expect:
		provider.category == 'mcp'
		provider.integrationOptionTypes*.fieldName.containsAll(['serviceUrl', 'serviceToken'])
		provider.integrationOptionTypes*.fieldContext.contains('credential')
	}

	void "default capabilities inherit from integration type flags"() {
		given:
		def provider = new TypedMcpProvider()

		when:
		def response = provider.getCapabilities(new AccountIntegration(), [:])

		then:
		response.success
		response.data.toolsSupported == true
		response.data.resourcesSupported == true
		response.data.promptsSupported == true
	}

	void "listTools maps supported tool names into tool objects"() {
		given:
		def provider = new SupportedToolsMcpProvider()

		when:
		def response = provider.listTools(new AccountIntegration(), [:])

		then:
		response.success
		response.data*.code == ['list_instances', 'get_instance']
		response.data*.name == ['list_instances', 'get_instance']
	}

	private static class BaseMcpProvider implements McpIntegrationProvider {
		@Override
		MorpheusContext getMorpheus() {
			return null
		}

		@Override
		Plugin getPlugin() {
			return null
		}

		@Override
		String getCode() {
			return 'mcp.test'
		}

		@Override
		String getName() {
			return 'MCP Test'
		}

		@Override
		void refresh(AccountIntegration accountIntegration) {
		}

		@Override
		Icon getIcon() {
			return null
		}

		@Override
		ServiceResponse<McpToolCallResponse> callTool(AccountIntegration accountIntegration, McpToolCallRequest request, Map opts) {
			return ServiceResponse.success(new McpToolCallResponse())
		}
	}

	private static class TypedMcpProvider extends BaseMcpProvider {
		@Override
		McpIntegrationType getMcpIntegrationType() {
			def type = new McpIntegrationType()
			type.toolsSupported = true
			type.resourcesSupported = true
			type.promptsSupported = true
			return type
		}
	}

	private static class SupportedToolsMcpProvider extends BaseMcpProvider {
		@Override
		ServiceResponse<McpCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
			def capabilities = new McpCapabilities()
			capabilities.supportedTools = ['list_instances', 'get_instance']
			return ServiceResponse.success(capabilities)
		}
	}
}
