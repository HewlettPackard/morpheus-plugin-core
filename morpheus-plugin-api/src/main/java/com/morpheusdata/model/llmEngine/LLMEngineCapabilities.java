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

package com.morpheusdata.model.llmEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Capability metadata for an LLM provider integration.
 */
public class LLMEngineCapabilities {
	protected Boolean chatSupported = true;
	protected Boolean streamingChatSupported = false;
	protected Boolean embeddingSupported = false;
	protected List<String> supportedModels = new ArrayList<>();
	protected List<LLMEngineModel> models = new ArrayList<>();
	protected Map<String, Object> metadata = new HashMap<>();

	public Boolean getChatSupported() {
		return chatSupported;
	}

	public void setChatSupported(Boolean chatSupported) {
		this.chatSupported = chatSupported;
	}

	public Boolean getStreamingChatSupported() {
		return streamingChatSupported;
	}

	public void setStreamingChatSupported(Boolean streamingChatSupported) {
		this.streamingChatSupported = streamingChatSupported;
	}

	public Boolean getEmbeddingSupported() {
		return embeddingSupported;
	}

	public void setEmbeddingSupported(Boolean embeddingSupported) {
		this.embeddingSupported = embeddingSupported;
	}

	public List<String> getSupportedModels() {
		return supportedModels;
	}

	public void setSupportedModels(List<String> supportedModels) {
		this.supportedModels = supportedModels;
	}

	public List<LLMEngineModel> getModels() {
		return models;
	}

	public void setModels(List<LLMEngineModel> models) {
		this.models = models;
		this.supportedModels = new ArrayList<>();
		if(models != null) {
			for(LLMEngineModel model : models) {
				if(model != null && model.getCode() != null) {
					this.supportedModels.add(model.getCode());
				}
			}
		}
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
