package com.morpheusdata.core.providers

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.AccountIntegration
import com.morpheusdata.model.Icon
import com.morpheusdata.model.llmEngine.LLMEngineCapabilities
import com.morpheusdata.model.llmEngine.LLMEngineChatRequest
import com.morpheusdata.model.llmEngine.LLMEngineChatResponse
import com.morpheusdata.model.llmEngine.LLMEngineIntegrationType
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class LLMEngineIntegrationProviderSpec extends Specification {

	void "default capabilities inherit from integration type flags"() {
		given:
		def provider = new TypedLLMEngineProvider()

		when:
		def response = provider.getCapabilities(new AccountIntegration(), [:])

		then:
		response.success
		response.data.chatSupported == true
		response.data.streamingChatSupported == true
		response.data.embeddingSupported == true
	}

	void "listModels maps supported model ids into model objects"() {
		given:
		def provider = new SupportedModelsLLMEngineProvider()

		when:
		def response = provider.listModels(new AccountIntegration(), [:])

		then:
		response.success
		response.data*.code == ['gpt-4o', 'claude-sonnet']
		response.data*.name == ['gpt-4o', 'claude-sonnet']
	}

	private static class BaseLLMEngineProvider implements LLMEngineIntegrationProvider {
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
			return 'llmEngine.test'
		}

		@Override
		String getName() {
			return 'LLMEngine Test'
		}

		@Override
		void refresh(AccountIntegration accountIntegration) {
		}

		@Override
		Icon getIcon() {
			return null
		}

		@Override
		ServiceResponse<LLMEngineChatResponse> chatCompletion(AccountIntegration accountIntegration, LLMEngineChatRequest request, Map opts) {
			return ServiceResponse.success(new LLMEngineChatResponse())
		}
	}

	private static class TypedLLMEngineProvider extends BaseLLMEngineProvider {
		@Override
		LLMEngineIntegrationType getLLMEngineIntegrationType() {
			def type = new LLMEngineIntegrationType()
			type.chatSupported = true
			type.streamingChatSupported = true
			type.embeddingSupported = true
			return type
		}
	}

	private static class SupportedModelsLLMEngineProvider extends BaseLLMEngineProvider {
		@Override
		ServiceResponse<LLMEngineCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
			def capabilities = new LLMEngineCapabilities()
			capabilities.supportedModels = ['gpt-4o', 'claude-sonnet']
			return ServiceResponse.success(capabilities)
		}
	}
}
