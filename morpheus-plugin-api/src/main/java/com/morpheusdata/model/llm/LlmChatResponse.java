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

import java.util.HashMap;
import java.util.Map;

/**
 * Normalized chat completion response.
 */
public class LlmChatResponse {
	protected String id;
	protected String model;
	protected String finishReason;
	protected LlmChatMessage message;
	protected LlmTokenUsage tokenUsage;
	protected Map<String, Object> metadata = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	public LlmChatMessage getMessage() {
		return message;
	}

	public void setMessage(LlmChatMessage message) {
		this.message = message;
	}

	public LlmTokenUsage getTokenUsage() {
		return tokenUsage;
	}

	public void setTokenUsage(LlmTokenUsage tokenUsage) {
		this.tokenUsage = tokenUsage;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
