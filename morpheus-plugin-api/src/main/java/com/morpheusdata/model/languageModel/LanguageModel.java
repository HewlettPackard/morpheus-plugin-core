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

package com.morpheusdata.model.languageModel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a provider supplied LLM model and optional performance metadata.
 */
public class LanguageModel extends MorpheusModel {
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected LanguageModelIntegration languageModelIntegration;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected LanguageModel parentModel;
	protected String code;
	protected String externalId;
	protected String name;
	protected String providerCode;
	protected String modelType;
	protected Long contextWindow;
	protected Long maxOutputTokens;
	protected Integer displayOrder;
	protected Integer speedScore;
	protected Integer qualityScore;
	protected Integer costScore;
	protected Integer inputTokensPerSecond;
	protected Integer outputTokensPerSecond;
	protected Boolean defaultLevel;
	protected Boolean enabled = true;
	protected List<LanguageModel> subModels = new ArrayList<>();
	protected Map<String, Object> metadata = new HashMap<>();

	public LanguageModelIntegration getLanguageModelIntegration() {
		return languageModelIntegration;
	}

	public void setLanguageModelIntegration(LanguageModelIntegration languageModelIntegration) {
		this.languageModelIntegration = languageModelIntegration;
		markDirty("languageModelIntegration", languageModelIntegration);
	}

	public LanguageModel getParentModel() {
		return parentModel;
	}

	public void setParentModel(LanguageModel parentModel) {
		this.parentModel = parentModel;
		markDirty("parentModel", parentModel);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
		markDirty("providerCode", providerCode);
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
		markDirty("modelType", modelType);
	}

	public Long getContextWindow() {
		return contextWindow;
	}

	public void setContextWindow(Long contextWindow) {
		this.contextWindow = contextWindow;
		markDirty("contextWindow", contextWindow);
	}

	public Long getMaxOutputTokens() {
		return maxOutputTokens;
	}

	public void setMaxOutputTokens(Long maxOutputTokens) {
		this.maxOutputTokens = maxOutputTokens;
		markDirty("maxOutputTokens", maxOutputTokens);
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
		markDirty("displayOrder", displayOrder);
	}

	public Integer getSpeedScore() {
		return speedScore;
	}

	public void setSpeedScore(Integer speedScore) {
		this.speedScore = speedScore;
		markDirty("speedScore", speedScore);
	}

	public Integer getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
		markDirty("qualityScore", qualityScore);
	}

	public Integer getCostScore() {
		return costScore;
	}

	public void setCostScore(Integer costScore) {
		this.costScore = costScore;
		markDirty("costScore", costScore);
	}

	public Integer getInputTokensPerSecond() {
		return inputTokensPerSecond;
	}

	public void setInputTokensPerSecond(Integer inputTokensPerSecond) {
		this.inputTokensPerSecond = inputTokensPerSecond;
		markDirty("inputTokensPerSecond", inputTokensPerSecond);
	}

	public Integer getOutputTokensPerSecond() {
		return outputTokensPerSecond;
	}

	public void setOutputTokensPerSecond(Integer outputTokensPerSecond) {
		this.outputTokensPerSecond = outputTokensPerSecond;
		markDirty("outputTokensPerSecond", outputTokensPerSecond);
	}

	public Boolean getDefaultLevel() {
		return defaultLevel;
	}

	public void setDefaultLevel(Boolean defaultLevel) {
		this.defaultLevel = defaultLevel;
		markDirty("defaultLevel", defaultLevel);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public List<LanguageModel> getSubModels() {
		return subModels;
	}

	public void setSubModels(List<LanguageModel> subModels) {
		this.subModels = subModels;
		markDirty("subModels", subModels);
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
		markDirty("metadata", metadata);
	}
}
