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

package com.morpheusdata.model.mcp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an MCP integration instance backed by an AccountIntegration.
 */
public class McpIntegration extends MorpheusModel {
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected AccountIntegration accountIntegration;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected McpIntegrationType type;
	protected String code;
	protected String name;
	protected Boolean enabled = true;
	protected List<McpTool> tools = new ArrayList<>();
	protected Map<String, Object> metadata = new HashMap<>();

	public AccountIntegration getAccountIntegration() {
		return accountIntegration;
	}

	public void setAccountIntegration(AccountIntegration accountIntegration) {
		this.accountIntegration = accountIntegration;
		markDirty("accountIntegration", accountIntegration);
	}

	public McpIntegrationType getType() {
		return type;
	}

	public void setType(McpIntegrationType type) {
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

	public List<McpTool> getTools() {
		return tools;
	}

	public void setTools(List<McpTool> tools) {
		this.tools = tools;
		markDirty("tools", tools);
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
		markDirty("metadata", metadata);
	}
}
