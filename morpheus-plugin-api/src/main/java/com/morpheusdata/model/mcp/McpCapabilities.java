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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Capability metadata for an MCP provider integration.
 */
public class McpCapabilities {
	protected Boolean toolsSupported = true;
	protected Boolean resourcesSupported = false;
	protected Boolean promptsSupported = false;
	protected List<String> supportedTools = new ArrayList<>();
	protected List<McpTool> tools = new ArrayList<>();
	protected Map<String, Object> metadata = new HashMap<>();

	public Boolean getToolsSupported() {
		return toolsSupported;
	}

	public void setToolsSupported(Boolean toolsSupported) {
		this.toolsSupported = toolsSupported;
	}

	public Boolean getResourcesSupported() {
		return resourcesSupported;
	}

	public void setResourcesSupported(Boolean resourcesSupported) {
		this.resourcesSupported = resourcesSupported;
	}

	public Boolean getPromptsSupported() {
		return promptsSupported;
	}

	public void setPromptsSupported(Boolean promptsSupported) {
		this.promptsSupported = promptsSupported;
	}

	public List<String> getSupportedTools() {
		return supportedTools;
	}

	public void setSupportedTools(List<String> supportedTools) {
		this.supportedTools = supportedTools;
	}

	public List<McpTool> getTools() {
		return tools;
	}

	public void setTools(List<McpTool> tools) {
		this.tools = tools;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
