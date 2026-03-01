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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an LlmModel integration instance backed by an AccountIntegration.
 */
public class LlmIntegration extends MorpheusModel {
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected AccountIntegration accountIntegration;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected LlmIntegrationType type;
	protected String code;
	protected String name;
	protected Boolean enabled = true;
	protected Long tokenUsageUsed;
	protected Long tokenUsageRemaining;
	protected Long tokenUsageLimit;
	protected String tokenUsagePeriod;
	protected String tokenUsageResetAt;
	protected Long requestUsageUsed;
	protected Long requestUsageRemaining;
	protected Long requestUsageLimit;
	protected String requestUsagePeriod;
	protected String requestUsageResetAt;
	protected List<LlmModel> models = new ArrayList<>();
	protected Map<String, Object> metadata = new HashMap<>();

	public AccountIntegration getAccountIntegration() {
		return accountIntegration;
	}

	public void setAccountIntegration(AccountIntegration accountIntegration) {
		this.accountIntegration = accountIntegration;
		markDirty("accountIntegration", accountIntegration);
	}

	public LlmIntegrationType getType() {
		return type;
	}

	public void setType(LlmIntegrationType type) {
		this.type = type;
		markDirty("type", type);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Long getTokenUsageUsed() {
		return tokenUsageUsed;
	}

	public void setTokenUsageUsed(Long tokenUsageUsed) {
		this.tokenUsageUsed = tokenUsageUsed;
		markDirty("tokenUsageUsed", tokenUsageUsed);
	}

	public Long getTokenUsageRemaining() {
		return tokenUsageRemaining;
	}

	public void setTokenUsageRemaining(Long tokenUsageRemaining) {
		this.tokenUsageRemaining = tokenUsageRemaining;
		markDirty("tokenUsageRemaining", tokenUsageRemaining);
	}

	public Long getTokenUsageLimit() {
		return tokenUsageLimit;
	}

	public void setTokenUsageLimit(Long tokenUsageLimit) {
		this.tokenUsageLimit = tokenUsageLimit;
		markDirty("tokenUsageLimit", tokenUsageLimit);
	}

	public String getTokenUsagePeriod() {
		return tokenUsagePeriod;
	}

	public void setTokenUsagePeriod(String tokenUsagePeriod) {
		this.tokenUsagePeriod = tokenUsagePeriod;
		markDirty("tokenUsagePeriod", tokenUsagePeriod);
	}

	public String getTokenUsageResetAt() {
		return tokenUsageResetAt;
	}

	public void setTokenUsageResetAt(String tokenUsageResetAt) {
		this.tokenUsageResetAt = tokenUsageResetAt;
		markDirty("tokenUsageResetAt", tokenUsageResetAt);
	}

	public Long getRequestUsageUsed() {
		return requestUsageUsed;
	}

	public void setRequestUsageUsed(Long requestUsageUsed) {
		this.requestUsageUsed = requestUsageUsed;
		markDirty("requestUsageUsed", requestUsageUsed);
	}

	public Long getRequestUsageRemaining() {
		return requestUsageRemaining;
	}

	public void setRequestUsageRemaining(Long requestUsageRemaining) {
		this.requestUsageRemaining = requestUsageRemaining;
		markDirty("requestUsageRemaining", requestUsageRemaining);
	}

	public Long getRequestUsageLimit() {
		return requestUsageLimit;
	}

	public void setRequestUsageLimit(Long requestUsageLimit) {
		this.requestUsageLimit = requestUsageLimit;
		markDirty("requestUsageLimit", requestUsageLimit);
	}

	public String getRequestUsagePeriod() {
		return requestUsagePeriod;
	}

	public void setRequestUsagePeriod(String requestUsagePeriod) {
		this.requestUsagePeriod = requestUsagePeriod;
		markDirty("requestUsagePeriod", requestUsagePeriod);
	}

	public String getRequestUsageResetAt() {
		return requestUsageResetAt;
	}

	public void setRequestUsageResetAt(String requestUsageResetAt) {
		this.requestUsageResetAt = requestUsageResetAt;
		markDirty("requestUsageResetAt", requestUsageResetAt);
	}

	public List<LlmModel> getModels() {
		return models;
	}

	public void setModels(List<LlmModel> models) {
		this.models = models;
		markDirty("models", models);
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
		markDirty("metadata", metadata);
	}
}
