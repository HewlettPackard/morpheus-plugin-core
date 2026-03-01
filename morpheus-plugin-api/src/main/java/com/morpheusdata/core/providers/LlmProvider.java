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

import com.morpheusdata.model.Icon;
import com.morpheusdata.model.OptionType;
import com.morpheusdata.model.llm.LlmChatRequest;
import com.morpheusdata.model.llm.LlmChatResponse;
import com.morpheusdata.model.llm.LlmIntegration;
import com.morpheusdata.response.LlmStreamingResponseHandler;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Integration provider contract for LLM/LlmModel service integrations.
 * Plugins implement this interface to register an LlmModel integration type
 * under Admin &gt; Integrations. Similar to {@link IPAMProvider}, this creates
 * its own {@link com.morpheusdata.model.AccountIntegrationType} and related
 * {@link com.morpheusdata.model.llm.LlmIntegrationType} during plugin load.
 */
public interface LlmProvider extends PluginProvider {

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
	default List<OptionType> getOptionTypes() {
		return new ArrayList<>();
	}

	/**
	 * Flags if the user can create this integration type.
	 * @return true if creatable; false otherwise
	 */
	default Boolean getCreatable() {
		return true;
	}

	/**
	 * Description for the provider-backed integration type.
	 * @return integration description text
	 */
	default String getDescription() {
		return null;
	}

	/**
	 * Flags if this integration type is enabled.
	 * @return true if enabled
	 */
	default Boolean getEnabled() {
		return true;
	}

	/**
	 * Indicates whether the provider supports chat completions.
	 *
	 * @return true if chat completion is supported
	 */
	default Boolean getChatSupported() {
		return true;
	}

	/**
	 * Indicates whether the provider supports streaming chat completions.
	 *
	 * @return true if streaming is supported
	 */
	default Boolean getStreamingChatSupported() {
		return false;
	}

	/**
	 * Indicates whether the provider supports embedding generation.
	 *
	 * @return true if embeddings are supported
	 */
	default Boolean getEmbeddingSupported() {
		return false;
	}

	/**
	 * Validation method used to validate inputs applied to the integration upon save.
	 * @param languageModelIntegration the integration being validated
	 * @param opts custom payload options
	 * @return a response indicating validity
	 */
	default ServiceResponse validate(LlmIntegration languageModelIntegration, Map opts) {
		return ServiceResponse.success(languageModelIntegration);
	}

	/**
	 * Called periodically to refresh and sync data from the provider.
	 * @param languageModelIntegration the integration to refresh
	 */
	void refresh(LlmIntegration languageModelIntegration);

	/**
	 * Performs a non-streaming response generation request.
	 *
	 * @param languageModelIntegration integration configuration
	 * @param request response generation request payload
	 * @param opts optional call options
	 * @return response containing the generated output
	 */
	ServiceResponse<LlmChatResponse> generateResponse(LlmIntegration languageModelIntegration, LlmChatRequest request, Map opts);

	/**
	 * Performs a streaming response generation request.
	 *
	 * @param languageModelIntegration integration configuration
	 * @param request response generation request payload
	 * @param handler streaming callback handler
	 * @param opts optional call options
	 */
	default void streamResponse(LlmIntegration languageModelIntegration, LlmChatRequest request, LlmStreamingResponseHandler handler, Map opts) {
		if(handler != null) {
			handler.onError(new UnsupportedOperationException("Streaming response generation is not supported by this provider"));
		}
	}

}
