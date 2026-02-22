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

package com.morpheusdata.core.providers;

import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.Icon;
import com.morpheusdata.model.OptionType;
import com.morpheusdata.model.mcp.McpCapabilities;
import com.morpheusdata.model.mcp.McpIntegrationType;
import com.morpheusdata.model.mcp.McpTool;
import com.morpheusdata.model.mcp.McpToolCallRequest;
import com.morpheusdata.model.mcp.McpToolCallResponse;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Integration provider contract for MCP service integrations.
 * Plugins implement this interface to register an MCP integration type
 * under Admin &gt; Integrations. Similar to {@link IPAMProvider}, this creates
 * its own {@link com.morpheusdata.model.AccountIntegrationType} and related
 * {@link McpIntegrationType} during plugin load.
 */
public interface McpIntegrationProvider extends PluginProvider {

	/**
	 * The integration category used for routing and grouping.
	 * @return the category string
	 */
	default String getCategory() {
		return "mcp";
	}

	/**
	 * Returns the integration logo for display.
	 * @return Icon representation of assets stored in the plugin's src/assets
	 */
	Icon getIcon();

	/**
	 * Provide custom configuration options when creating a new integration.
	 * @return a List of OptionType
	 */
	default List<OptionType> getIntegrationOptionTypes() {
		return defaultOptionTypes(getCode());
	}

	/**
	 * Flags if the user can create this integration type.
	 * @return true if creatable; false otherwise
	 */
	default Boolean getCreatable() {
		return true;
	}

	/**
	 * Validation method used to validate inputs applied to the integration upon save.
	 * @param accountIntegration the integration being validated
	 * @param opts custom payload options
	 * @return a response indicating validity
	 */
	default ServiceResponse verifyAccountIntegration(AccountIntegration accountIntegration, Map opts) {
		return ServiceResponse.success(accountIntegration);
	}

	/**
	 * Called periodically to refresh and sync data from the provider.
	 * @param accountIntegration the integration to refresh
	 */
	void refresh(AccountIntegration accountIntegration);

	/**
	 * Returns the MCP integration type representation for this provider.
	 *
	 * @return a typed provider descriptor
	 */
	default McpIntegrationType getMcpIntegrationType() {
		McpIntegrationType type = new McpIntegrationType();
		type.setCode(getCode());
		type.setName(getName());
		type.setProviderService(getCode());
		type.setToolsSupported(true);
		type.setResourcesSupported(false);
		type.setPromptsSupported(false);
		type.setEnabled(true);
		return type;
	}

	/**
	 * Returns provider capabilities and available tools.
	 *
	 * @param accountIntegration integration configuration
	 * @param opts optional call options
	 * @return response containing capabilities
	 */
	default ServiceResponse<McpCapabilities> getCapabilities(AccountIntegration accountIntegration, Map opts) {
		McpIntegrationType type = getMcpIntegrationType();
		McpCapabilities capabilities = new McpCapabilities();
		capabilities.setToolsSupported(type.getToolsSupported() != null ? type.getToolsSupported() : true);
		capabilities.setResourcesSupported(type.getResourcesSupported() != null ? type.getResourcesSupported() : false);
		capabilities.setPromptsSupported(type.getPromptsSupported() != null ? type.getPromptsSupported() : false);
		return ServiceResponse.success(capabilities);
	}

	/**
	 * Lists tools supported by this provider for the integration.
	 *
	 * @param accountIntegration integration configuration
	 * @param opts optional call options
	 * @return response containing available tools
	 */
	default ServiceResponse<List<McpTool>> listTools(AccountIntegration accountIntegration, Map opts) {
		ServiceResponse<McpCapabilities> capabilitiesResponse = getCapabilities(accountIntegration, opts);
		if(capabilitiesResponse.getSuccess() != true) {
			return ServiceResponse.error(capabilitiesResponse.getMsg(), capabilitiesResponse.getErrors());
		}

		McpCapabilities capabilities = capabilitiesResponse.getData();
		if(capabilities == null) {
			return ServiceResponse.success(new ArrayList<>());
		}

		if(capabilities.getTools() != null && !capabilities.getTools().isEmpty()) {
			return ServiceResponse.success(capabilities.getTools());
		}

		List<McpTool> tools = new ArrayList<>();
		if(capabilities.getSupportedTools() != null) {
			for(String supportedTool : capabilities.getSupportedTools()) {
				McpTool tool = new McpTool();
				tool.setCode(supportedTool);
				tool.setName(supportedTool);
				tools.add(tool);
			}
		}
		return ServiceResponse.success(tools);
	}

	/**
	 * Performs an MCP tool invocation request.
	 *
	 * @param accountIntegration integration configuration
	 * @param request tool call request payload
	 * @param opts optional call options
	 * @return response containing tool invocation result
	 */
	ServiceResponse<McpToolCallResponse> callTool(AccountIntegration accountIntegration, McpToolCallRequest request, Map opts);

	/**
	 * Default integration fields common to most MCP providers.
	 *
	 * @param providerCode provider code used as option code prefix
	 * @return default option type list
	 */
	static List<OptionType> defaultOptionTypes(String providerCode) {
		List<OptionType> optionTypes = new ArrayList<>();

		OptionType endpoint = new OptionType();
		endpoint.setCode(providerCode + ".serviceUrl");
		endpoint.setName("MCP Endpoint");
		endpoint.setInputType(OptionType.InputType.TEXT);
		endpoint.setFieldName("serviceUrl");
		endpoint.setFieldLabel("MCP Endpoint");
		endpoint.setFieldContext("domain");
		endpoint.setRequired(true);
		endpoint.setDisplayOrder(0);
		optionTypes.add(endpoint);

		OptionType credentials = new OptionType();
		credentials.setCode(providerCode + ".credential");
		credentials.setName("Credentials");
		credentials.setInputType(OptionType.InputType.CREDENTIAL);
		credentials.setFieldName("type");
		credentials.setFieldLabel("Credentials");
		credentials.setFieldContext("credential");
		credentials.setRequired(true);
		credentials.setDisplayOrder(1);
		credentials.setDefaultValue("local");
		credentials.setOptionSource("credentials");
		credentials.setConfig("{\"credentialTypes\":[\"username-password\",\"api-key\",\"oauth2\",\"client-id-secret\"]}");
		optionTypes.add(credentials);

		OptionType username = new OptionType();
		username.setCode(providerCode + ".serviceUsername");
		username.setName("Username");
		username.setInputType(OptionType.InputType.TEXT);
		username.setFieldName("serviceUsername");
		username.setFieldLabel("Username");
		username.setFieldContext("domain");
		username.setDisplayOrder(2);
		username.setLocalCredential(true);
		optionTypes.add(username);

		OptionType password = new OptionType();
		password.setCode(providerCode + ".servicePassword");
		password.setName("Password");
		password.setInputType(OptionType.InputType.PASSWORD);
		password.setFieldName("servicePassword");
		password.setFieldLabel("Password");
		password.setFieldContext("domain");
		password.setDisplayOrder(3);
		password.setLocalCredential(true);
		optionTypes.add(password);

		OptionType token = new OptionType();
		token.setCode(providerCode + ".serviceToken");
		token.setName("API Key");
		token.setInputType(OptionType.InputType.PASSWORD);
		token.setFieldName("serviceToken");
		token.setFieldLabel("API Key");
		token.setFieldContext("domain");
		token.setDisplayOrder(4);
		token.setLocalCredential(true);
		optionTypes.add(token);

		return optionTypes;
	}
}
