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
import com.morpheusdata.model.MorpheusModel;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a provider supplied MCP tool.
 */
public class McpTool extends MorpheusModel {
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected McpIntegration mcpIntegration;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected McpTool parentTool;
	protected String code;
	protected String name;
	protected String description;
	protected String category;
	protected Integer displayOrder;
	protected Boolean enabled = true;
	protected Map<String, Object> inputSchema = new HashMap<>();
	protected Map<String, Object> metadata = new HashMap<>();
	protected List<McpTool> subTools = new ArrayList<>();

	public McpIntegration getMcpIntegration() {
		return mcpIntegration;
	}

	public void setMcpIntegration(McpIntegration mcpIntegration) {
		this.mcpIntegration = mcpIntegration;
		markDirty("mcpIntegration", mcpIntegration);
	}

	public McpTool getParentTool() {
		return parentTool;
	}

	public void setParentTool(McpTool parentTool) {
		this.parentTool = parentTool;
		markDirty("parentTool", parentTool);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", category);
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
		markDirty("displayOrder", displayOrder);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Map<String, Object> getInputSchema() {
		return inputSchema;
	}

	public void setInputSchema(Map<String, Object> inputSchema) {
		this.inputSchema = inputSchema;
		markDirty("inputSchema", inputSchema);
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
		markDirty("metadata", metadata);
	}

	public List<McpTool> getSubTools() {
		return subTools;
	}

	public void setSubTools(List<McpTool> subTools) {
		this.subTools = subTools;
		markDirty("subTools", subTools);
	}
}
