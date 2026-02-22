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
 * Normalized embedding response payload.
 */
public class LLMEngineEmbeddingResponse {
	protected String model;
	protected List<List<Double>> embeddings = new ArrayList<>();
	protected LLMEngineTokenUsage tokenUsage;
	protected Map<String, Object> metadata = new HashMap<>();

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<List<Double>> getEmbeddings() {
		return embeddings;
	}

	public void setEmbeddings(List<List<Double>> embeddings) {
		this.embeddings = embeddings;
	}

	public LLMEngineTokenUsage getTokenUsage() {
		return tokenUsage;
	}

	public void setTokenUsage(LLMEngineTokenUsage tokenUsage) {
		this.tokenUsage = tokenUsage;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
