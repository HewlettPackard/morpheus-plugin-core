package com.morpheusdata.core.providers

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.AccountIntegration
import com.morpheusdata.model.Icon
import com.morpheusdata.model.languageModel.LanguageModelCapabilities
import com.morpheusdata.model.languageModel.LanguageModelChatRequest
import com.morpheusdata.model.languageModel.LanguageModelChatResponse
import com.morpheusdata.model.languageModel.LanguageModelIntegrationType
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class LanguageModelIntegrationProviderSpec extends Specification {

	void "default capabilities inherit from integration type flags"() {
		given:
		def provider = new TypedLanguageModelProvider()

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
		def provider = new SupportedModelsLanguageModelProvider()

		when:
		def response = provider.listModels(new AccountIntegration(), [:])

		then:
		response.success
		response.data*.code == ['gpt-4o', 'claude-sonnet']
		response.data*.name == ['gpt-4o', 'claude-sonnet']
	}

	private static class BaseLanguageModelProvider implements LanguageModelIntegrationProvider {
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
			return 'languageModel.test'
		}

		@Override
		String getName() {
			return 'LanguageModel Test'
		}

		@Override
		void refresh(AccountIntegration accountIntegration) {
		}

		@Override
		Icon getIcon() {
			return null
		}

		@Override
		ServiceResponse<LanguageModelChatResponse> chatCompletion(AccountIntegration accountIntegration, LanguageModelChatRequest request, Map opts) {
			return ServiceResponse.success(new LanguageModelChatResponse())
		}
	}

	private static class TypedLanguageModelProvider extends BaseLanguageModelProvider {
		@Override
		LanguageModelIntegrationType getLanguageModelIntegrationType() {
			def type = new LanguageModelIntegrationType()
			type.chatSupported = true
			type.streamingChatSupported = true
			type.embeddingSupported = true
			return type
		}
	}

	private static class SupportedModelsLanguageModelProvider extends BaseLanguageModelProvider {
		@Override
		ServiceResponse<LanguageModelCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
			def capabilities = new LanguageModelCapabilities()
			capabilities.supportedModels = ['gpt-4o', 'claude-sonnet']
			return ServiceResponse.success(capabilities)
		}
	}
}
