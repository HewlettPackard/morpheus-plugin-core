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

package com.morpheusdata.model.llm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shared metadata helpers for provider-specific model endpoint and capability data.
 * Metadata is persisted on {@link LlmModel#getMetadata()} and reused for request routing.
 */
public final class LlmModelMetadata {

	public static final String KEY_CAPABILITIES = "capabilities";
	public static final String KEY_SUPPORTED_ENDPOINTS = "supportedEndpoints";
	public static final String KEY_INTERACTION_ENDPOINT = "interactionEndpoint";
	public static final String KEY_STREAMING_INTERACTION_ENDPOINT = "streamingInteractionEndpoint";
	public static final String KEY_SUPPORTS = "supports";
	public static final String KEY_SUPPORTS_STREAMING = "streaming";

	private LlmModelMetadata() {
	}

	public static Map<String, Object> buildMetadata(Map<String, Object> capabilities, List<String> supportedEndpoints, String interactionEndpoint, String streamingInteractionEndpoint) {
		Map<String, Object> metadata = new HashMap<>();
		if(capabilities != null && !capabilities.isEmpty()) {
			metadata.put(KEY_CAPABILITIES, capabilities);
		}
		if(supportedEndpoints != null && !supportedEndpoints.isEmpty()) {
			metadata.put(KEY_SUPPORTED_ENDPOINTS, new ArrayList<>(supportedEndpoints));
		}
		if(interactionEndpoint != null && !interactionEndpoint.isEmpty()) {
			metadata.put(KEY_INTERACTION_ENDPOINT, interactionEndpoint);
		}
		if(streamingInteractionEndpoint != null && !streamingInteractionEndpoint.isEmpty()) {
			metadata.put(KEY_STREAMING_INTERACTION_ENDPOINT, streamingInteractionEndpoint);
		}
		return metadata;
	}

	public static String getInteractionEndpoint(LlmModel model) {
		if(model == null || model.getMetadata() == null) {
			return null;
		}
		Object value = model.getMetadata().get(KEY_INTERACTION_ENDPOINT);
		return value != null ? value.toString() : null;
	}

	public static String getStreamingInteractionEndpoint(LlmModel model) {
		if(model == null || model.getMetadata() == null) {
			return null;
		}
		Object value = model.getMetadata().get(KEY_STREAMING_INTERACTION_ENDPOINT);
		return value != null ? value.toString() : null;
	}

	public static List<String> getSupportedEndpoints(LlmModel model) {
		if(model == null || model.getMetadata() == null) {
			return Collections.emptyList();
		}
		Object value = model.getMetadata().get(KEY_SUPPORTED_ENDPOINTS);
		if(!(value instanceof List)) {
			return Collections.emptyList();
		}
		List<String> endpoints = new ArrayList<>();
		for(Object endpoint : (List) value) {
			if(endpoint != null) {
				endpoints.add(endpoint.toString());
			}
		}
		return endpoints;
	}

	public static Map<String, Object> getCapabilities(LlmModel model) {
		if(model == null || model.getMetadata() == null) {
			return Collections.emptyMap();
		}
		Object value = model.getMetadata().get(KEY_CAPABILITIES);
		if(value instanceof Map) {
			return (Map<String, Object>) value;
		}
		return Collections.emptyMap();
	}

	public static Boolean getStreamingSupported(LlmModel model) {
		Map<String, Object> capabilities = getCapabilities(model);
		Object supports = capabilities.get(KEY_SUPPORTS);
		if(supports instanceof Map) {
			Object streaming = ((Map) supports).get(KEY_SUPPORTS_STREAMING);
			if(streaming instanceof Boolean) {
				return (Boolean) streaming;
			}
		}
		return null;
	}
}
