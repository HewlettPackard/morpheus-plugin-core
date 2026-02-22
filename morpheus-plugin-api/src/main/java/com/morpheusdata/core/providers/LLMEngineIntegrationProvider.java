/*
 *  Copyright 2026 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.core.providers;

import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.Icon;
import com.morpheusdata.model.OptionType;
import com.morpheusdata.model.llmEngine.LLMEngineCapabilities;
import com.morpheusdata.model.llmEngine.LLMEngineChatRequest;
import com.morpheusdata.model.llmEngine.LLMEngineChatResponse;
import com.morpheusdata.model.llmEngine.LLMEngineEmbeddingRequest;
import com.morpheusdata.model.llmEngine.LLMEngineEmbeddingResponse;
import com.morpheusdata.model.llmEngine.LLMEngineIntegrationType;
import com.morpheusdata.model.llmEngine.LLMEngineModel;
import com.morpheusdata.response.LLMEngineStreamingResponseHandler;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Integration provider contract for LLM/LLMEngine service integrations.
 * Plugins implement this interface to register an LLMEngine integration type
 * under Admin &gt; Integrations. Similar to {@link IPAMProvider}, this creates
 * its own {@link com.morpheusdata.model.AccountIntegrationType} and related
 * {@link LLMEngineIntegrationType} during plugin load.
 */
public interface LLMEngineIntegrationProvider extends PluginProvider {

	/**
	 * The integration category used for routing and grouping.
	 * @return the category string
	 */
	default String getCategory() {
		return "llmEngine";
	}

	/**
	 * Returns the integration logo for display.
	 * @return Icon representation of assets stored in the plugin's src/assets
	 */
	Icon getIcon();

	/**
	 * Provide custom configuration options when creating a new integration.
	 * @return a List of OptionType
	 */
	default List<OptionType> getIntegrationOptionTypes() {
		return defaultOptionTypes(getCode());
	}

	/**
	 * Flags if the user can create this integration type.
	 * @return true if creatable; false otherwise
	 */
	default Boolean getCreatable() {
		return true;
	}

	/**
	 * Validation method used to validate inputs applied to the integration upon save.
	 * @param accountIntegration the integration being validated
	 * @param opts custom payload options
	 * @return a response indicating validity
	 */
	default ServiceResponse verifyAccountIntegration(AccountIntegration accountIntegration, Map opts) {
		return ServiceResponse.success(accountIntegration);
	}

	/**
	 * Called periodically to refresh and sync data from the provider.
	 * @param accountIntegration the integration to refresh
	 */
	void refresh(AccountIntegration accountIntegration);

	/**
	 * Returns the LLMEngine integration type representation for this provider.
	 *
	 * @return a typed provider descriptor
	 */
	default LLMEngineIntegrationType getLLMEngineIntegrationType() {
		LLMEngineIntegrationType type = new LLMEngineIntegrationType();
		type.setCode(getCode());
		type.setName(getName());
		type.setProviderService(getCode());
		type.setChatSupported(true);
		type.setStreamingChatSupported(false);
		type.setEmbeddingSupported(false);
		type.setEnabled(true);
		return type;
	}

	/**
	 * Returns provider capabilities and available models.
	 *
	 * @param accountIntegration integration configuration
	 * @param opts optional call options
	 * @return response containing capabilities
	 */
	default ServiceResponse<LLMEngineCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
		LLMEngineIntegrationType type = getLLMEngineIntegrationType();
		LLMEngineCapabilities capabilities = new LLMEngineCapabilities();
		capabilities.setChatSupported(type.getChatSupported() != null ? type.getChatSupported() : true);
		capabilities.setStreamingChatSupported(type.getStreamingChatSupported() != null ? type.getStreamingChatSupported() : false);
		capabilities.setEmbeddingSupported(type.getEmbeddingSupported() != null ? type.getEmbeddingSupported() : false);
		return ServiceResponse.success(capabilities);
	}

	/**
	 * Lists models supported by this provider for the integration.
	 *
	 * @param accountIntegration integration configuration
	 * @param opts optional call options
	 * @return response containing available models
	 */
	default ServiceResponse<List<LLMEngineModel>> listModels(AccountIntegration accountIntegration, Map opts) {
		ServiceResponse<LLMEngineCapabilities> capabilitiesResponse = getCapabilities(accountIntegration, opts);
		if(capabilitiesResponse.getSuccess() != true) {
			return ServiceResponse.error(capabilitiesResponse.getMsg(), capabilitiesResponse.getErrors());
		}

		LLMEngineCapabilities capabilities = capabilitiesResponse.getData();
		if(capabilities == null) {
			return ServiceResponse.success(new ArrayList<>());
		}

		if(capabilities.getModels() != null && !capabilities.getModels().isEmpty()) {
			return ServiceResponse.success(capabilities.getModels());
		}

		List<LLMEngineModel> models = new ArrayList<>();
		if(capabilities.getSupportedModels() != null) {
			for(String supportedModel : capabilities.getSupportedModels()) {
				LLMEngineModel model = new LLMEngineModel();
				model.setCode(supportedModel);
				model.setName(supportedModel);
				models.add(model);
			}
		}
		return ServiceResponse.success(models);
	}

	/**
	 * Performs a non-streaming chat completion request.
	 *
	 * @param accountIntegration integration configuration
	 * @param request chat request payload
	 * @param opts optional call options
	 * @return response containing the generated chat completion
	 */
	ServiceResponse<LLMEngineChatResponse> chatCompletion(AccountIntegration accountIntegration, LLMEngineChatRequest request, Map opts);

	/**
	 * Performs a streaming chat completion request.
	 *
	 * @param accountIntegration integration configuration
	 * @param request chat request payload
	 * @param handler streaming callback handler
	 * @param opts optional call options
	 */
	default void streamChatCompletion(AccountIntegration accountIntegration, LLMEngineChatRequest request, LLMEngineStreamingResponseHandler handler, Map opts) {
		if(handler != null) {
			handler.onError(new UnsupportedOperationException("Streaming chat is not supported by this provider"));
		}
	}

	/**
	 * Generates embedding vectors for one or more input texts.
	 *
	 * @param accountIntegration integration configuration
	 * @param request embedding request payload
	 * @param opts optional call options
	 * @return response containing embedding vectors
	 */
	default ServiceResponse<LLMEngineEmbeddingResponse> generateEmbeddings(AccountIntegration accountIntegration, LLMEngineEmbeddingRequest request, Map opts) {
		return ServiceResponse.error("Embeddings are not supported by this provider");
	}

	/**
	 * Default integration fields common to most LLM providers.
	 *
	 * @param providerCode provider code used as option code prefix
	 * @return default option type list
	 */
	static List<OptionType> defaultOptionTypes(String providerCode) {
		List<OptionType> optionTypes = new ArrayList<>();

		OptionType endpoint = new OptionType();
		endpoint.setCode(providerCode + ".serviceUrl");
		endpoint.setName("Service Endpoint");
		endpoint.setInputType(OptionType.InputType.TEXT);
		endpoint.setFieldName("serviceUrl");
		endpoint.setFieldLabel("Service Endpoint");
		endpoint.setFieldContext("domain");
		endpoint.setRequired(true);
		endpoint.setDisplayOrder(0);
		optionTypes.add(endpoint);

		OptionType credentials = new OptionType();
		credentials.setCode(providerCode + ".credential");
		credentials.setName("Credentials");
		credentials.setInputType(OptionType.InputType.CREDENTIAL);
		credentials.setFieldName("type");
		credentials.setFieldLabel("Credentials");
		credentials.setFieldContext("credential");
		credentials.setRequired(true);
		credentials.setDisplayOrder(1);
		credentials.setDefaultValue("local");
		credentials.setOptionSource("credentials");
		credentials.setConfig("{\"credentialTypes\":[\"username-password\",\"api-key\",\"oauth2\",\"client-id-secret\"]}");
		optionTypes.add(credentials);

		OptionType username = new OptionType();
		username.setCode(providerCode + ".serviceUsername");
		username.setName("Username");
		username.setInputType(OptionType.InputType.TEXT);
		username.setFieldName("serviceUsername");
		username.setFieldLabel("Username");
		username.setFieldContext("domain");
		username.setDisplayOrder(2);
		username.setLocalCredential(true);
		optionTypes.add(username);

		OptionType password = new OptionType();
		password.setCode(providerCode + ".servicePassword");
		password.setName("Password");
		password.setInputType(OptionType.InputType.PASSWORD);
		password.setFieldName("servicePassword");
		password.setFieldLabel("Password");
		password.setFieldContext("domain");
		password.setDisplayOrder(3);
		password.setLocalCredential(true);
		optionTypes.add(password);

		OptionType token = new OptionType();
		token.setCode(providerCode + ".serviceToken");
		token.setName("API Key");
		token.setInputType(OptionType.InputType.PASSWORD);
		token.setFieldName("serviceToken");
		token.setFieldLabel("API Key");
		token.setFieldContext("domain");
		token.setDisplayOrder(4);
		token.setLocalCredential(true);
		optionTypes.add(token);

		return optionTypes;
	}
}
