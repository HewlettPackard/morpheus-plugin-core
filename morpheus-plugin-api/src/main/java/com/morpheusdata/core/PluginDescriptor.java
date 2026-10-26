/*
 *  Copyright 2024 Morpheus Data, LLC.
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

package com.morpheusdata.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Plugin metadata that can be inspected without loading or initializing the plugin class.
 */
public final class PluginDescriptor {

	public static final int CURRENT_SCHEMA_VERSION = 1;
	public static final String DESCRIPTOR_PATH = "META-INF/morpheus-plugin.yml";

	private final int schemaVersion;
	private final String pluginClass;
	private final String code;
	private final String version;
	private final List<RequiredPlugin> requiredPlugins;

	public PluginDescriptor(int schemaVersion, String pluginClass, String code, String version, List<RequiredPlugin> requiredPlugins) {
		this.schemaVersion = schemaVersion;
		this.pluginClass = pluginClass;
		this.code = code;
		this.version = version;
		this.requiredPlugins = Collections.unmodifiableList(new ArrayList<>(requiredPlugins == null ? Collections.emptyList() : requiredPlugins));
	}

	public int getSchemaVersion() {
		return schemaVersion;
	}

	public String getPluginClass() {
		return pluginClass;
	}

	public String getCode() {
		return code;
	}

	public String getVersion() {
		return version;
	}

	public List<RequiredPlugin> getRequiredPlugins() {
		return requiredPlugins;
	}
}
