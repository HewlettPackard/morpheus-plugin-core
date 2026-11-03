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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The loadable plugins in required-plugin order and any plugins that cannot be loaded.
 */
public final class PluginLoadPlan {

	private final List<PluginDescriptor> loadOrder;
	private final Map<PluginDescriptor, String> failures;

	PluginLoadPlan(List<PluginDescriptor> loadOrder, Map<PluginDescriptor, String> failures) {
		this.loadOrder = Collections.unmodifiableList(new ArrayList<>(loadOrder));
		this.failures = Collections.unmodifiableMap(new LinkedHashMap<>(failures));
	}

	public List<PluginDescriptor> getLoadOrder() {
		return loadOrder;
	}

	public Map<PluginDescriptor, String> getFailures() {
		return failures;
	}

	public String getFailure(PluginDescriptor descriptor) {
		return failures.get(descriptor);
	}

	public boolean hasFailures() {
		return !failures.isEmpty();
	}
}
