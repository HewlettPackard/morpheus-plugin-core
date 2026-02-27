package com.morpheusdata.core.providers

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Icon
import com.morpheusdata.model.llm.LlmChatRequest
import com.morpheusdata.model.llm.LlmChatResponse
import com.morpheusdata.model.llm.LlmIntegration
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class LlmProviderSpec extends Specification {

	void "default provider fields expose expected capability flags"() {
		given:
		def provider = new BaseLlmProvider()

		expect:
		provider.chatSupported == true
		provider.streamingChatSupported == false
		provider.embeddingSupported == false
	}

	void "listModels default returns empty list"() {
		given:
		def provider = new BaseLlmProvider()

		when:
		def response = provider.listModels(new LlmIntegration(), [:])

		then:
		response.success
		response.data == []
	}

	private static class BaseLlmProvider implements LlmProvider {
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
			return 'LlmModel Test'
		}

		@Override
		void refresh(LlmIntegration languageModelIntegration) {
		}

		@Override
		Icon getIcon() {
			return null
		}

		@Override
		ServiceResponse<LlmChatResponse> generateResponse(LlmIntegration languageModelIntegration, LlmChatRequest request, Map opts) {
			return ServiceResponse.success(new LlmChatResponse())
		}

		@Override
		ServiceResponse<List<com.morpheusdata.model.llm.LlmModel>> listModels(LlmIntegration languageModelIntegration, Map opts) {
			return ServiceResponse.success([])
		}
	}
}
