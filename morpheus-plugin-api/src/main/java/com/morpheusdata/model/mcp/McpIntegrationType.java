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

import com.morpheusdata.model.MorpheusModel;

/**
 * Represents the plugin supplied MCP provider type.
 */
public class McpIntegrationType extends MorpheusModel {
	protected String code;
	protected String name;
	protected String description;
	protected String providerService;
	protected Boolean toolsSupported = true;
	protected Boolean resourcesSupported = false;
	protected Boolean promptsSupported = false;
	protected Boolean enabled = true;

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

	public String getProviderService() {
		return providerService;
	}

	public void setProviderService(String providerService) {
		this.providerService = providerService;
		markDirty("providerService", providerService);
	}

	public Boolean getToolsSupported() {
		return toolsSupported;
	}

	public void setToolsSupported(Boolean toolsSupported) {
		this.toolsSupported = toolsSupported;
		markDirty("toolsSupported", toolsSupported);
	}

	public Boolean getResourcesSupported() {
		return resourcesSupported;
	}

	public void setResourcesSupported(Boolean resourcesSupported) {
		this.resourcesSupported = resourcesSupported;
		markDirty("resourcesSupported", resourcesSupported);
	}

	public Boolean getPromptsSupported() {
		return promptsSupported;
	}

	public void setPromptsSupported(Boolean promptsSupported) {
		this.promptsSupported = promptsSupported;
		markDirty("promptsSupported", promptsSupported);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}
}
