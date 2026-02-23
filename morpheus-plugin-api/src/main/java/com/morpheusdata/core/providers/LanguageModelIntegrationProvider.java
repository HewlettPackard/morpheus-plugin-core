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
import com.morpheusdata.model.languageModel.LanguageModelCapabilities;
import com.morpheusdata.model.languageModel.LanguageModelChatRequest;
import com.morpheusdata.model.languageModel.LanguageModelChatResponse;
import com.morpheusdata.model.languageModel.LanguageModelEmbeddingRequest;
import com.morpheusdata.model.languageModel.LanguageModelEmbeddingResponse;
import com.morpheusdata.model.languageModel.LanguageModelIntegrationType;
import com.morpheusdata.model.languageModel.LanguageModel;
import com.morpheusdata.response.LanguageModelStreamingResponseHandler;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Integration provider contract for LLM/LanguageModel service integrations.
 * Plugins implement this interface to register an LanguageModel integration type
 * under Admin &gt; Integrations. Similar to {@link IPAMProvider}, this creates
 * its own {@link com.morpheusdata.model.AccountIntegrationType} and related
 * {@link LanguageModelIntegrationType} during plugin load.
 */
public interface LanguageModelIntegrationProvider extends PluginProvider {

	/**
	 * The integration category used for routing and grouping.
	 * @return the category string
	 */
	default String getCategory() {
		return "languageModel";
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
	 * Returns the LanguageModel integration type representation for this provider.
	 *
	 * @return a typed provider descriptor
	 */
	default LanguageModelIntegrationType getLanguageModelIntegrationType() {
		LanguageModelIntegrationType type = new LanguageModelIntegrationType();
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
	default ServiceResponse<LanguageModelCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
		LanguageModelIntegrationType type = getLanguageModelIntegrationType();
		LanguageModelCapabilities capabilities = new LanguageModelCapabilities();
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
	default ServiceResponse<List<LanguageModel>> listModels(AccountIntegration accountIntegration, Map opts) {
		ServiceResponse<LanguageModelCapabilities> capabilitiesResponse = getCapabilities(accountIntegration, opts);
		if(capabilitiesResponse.getSuccess() != true) {
			return ServiceResponse.error(capabilitiesResponse.getMsg(), capabilitiesResponse.getErrors());
		}

		LanguageModelCapabilities capabilities = capabilitiesResponse.getData();
		if(capabilities == null) {
			return ServiceResponse.success(new ArrayList<>());
		}

		if(capabilities.getModels() != null && !capabilities.getModels().isEmpty()) {
			return ServiceResponse.success(capabilities.getModels());
		}

		List<LanguageModel> models = new ArrayList<>();
		if(capabilities.getSupportedModels() != null) {
			for(String supportedModel : capabilities.getSupportedModels()) {
				LanguageModel model = new LanguageModel();
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
	ServiceResponse<LanguageModelChatResponse> chatCompletion(AccountIntegration accountIntegration, LanguageModelChatRequest request, Map opts);

	/**
	 * Performs a streaming chat completion request.
	 *
	 * @param accountIntegration integration configuration
	 * @param request chat request payload
	 * @param handler streaming callback handler
	 * @param opts optional call options
	 */
	default void streamChatCompletion(AccountIntegration accountIntegration, LanguageModelChatRequest request, LanguageModelStreamingResponseHandler handler, Map opts) {
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
	default ServiceResponse<LanguageModelEmbeddingResponse> generateEmbeddings(AccountIntegration accountIntegration, LanguageModelEmbeddingRequest request, Map opts) {
		return ServiceResponse.error("Embeddings are not supported by this provider");
	}

	/**
	 * Provides default option types for LLM Engine integrations including
	 * service URL, username, password, and token fields.
	 *
	 * @param providerCode the provider code prefix for field names
	 * @return a list of default OptionType definitions
	 */
	static List<OptionType> defaultOptionTypes(String providerCode) {
		List<OptionType> optionTypes = new ArrayList<>();

		OptionType serviceUrl = new OptionType();
		serviceUrl.setCode(providerCode + ".serviceUrl");
		serviceUrl.setName("Service URL");
		serviceUrl.setFieldName("serviceUrl");
		serviceUrl.setFieldLabel("API Endpoint");
		serviceUrl.setInputType(OptionType.InputType.TEXT);
		serviceUrl.setDisplayOrder(0);
		serviceUrl.setRequired(true);
		optionTypes.add(serviceUrl);

		OptionType serviceUsername = new OptionType();
		serviceUsername.setCode(providerCode + ".serviceUsername");
		serviceUsername.setName("Username");
		serviceUsername.setFieldName("serviceUsername");
		serviceUsername.setFieldLabel("Username");
		serviceUsername.setInputType(OptionType.InputType.TEXT);
		serviceUsername.setDisplayOrder(1);
		serviceUsername.setRequired(false);
		optionTypes.add(serviceUsername);

		OptionType servicePassword = new OptionType();
		servicePassword.setCode(providerCode + ".servicePassword");
		servicePassword.setName("Password");
		servicePassword.setFieldName("servicePassword");
		servicePassword.setFieldLabel("Password");
		servicePassword.setInputType(OptionType.InputType.PASSWORD);
		servicePassword.setDisplayOrder(2);
		servicePassword.setRequired(false);
		optionTypes.add(servicePassword);

		OptionType serviceToken = new OptionType();
		serviceToken.setCode(providerCode + ".serviceToken");
		serviceToken.setName("API Token");
		serviceToken.setFieldName("serviceToken");
		serviceToken.setFieldLabel("API Token");
		serviceToken.setInputType(OptionType.InputType.PASSWORD);
		serviceToken.setDisplayOrder(3);
		serviceToken.setRequired(false);
		optionTypes.add(serviceToken);

		return optionTypes;
	}
}
