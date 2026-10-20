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
package com.morpheusdata.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The configuration fields of a {@link StorageServer} that may not change once the cluster is installed,
 * as reported by {@link com.morpheusdata.core.providers.StorageProviderConfiguration#getConfigurationConstraints}.
 * <p>
 * Read while the configuration form renders, so a fixed field is shown read-only with its reason beside it
 * rather than refused after the operator commits.
 *
 * @since 1.6.0
 */
public class StorageConfigurationConstraints {

	protected Map<String, String> fixedFields = new LinkedHashMap<>();

	/**
	 * @return each field key that may not change, mapped to the reason it cannot; empty when every field may change
	 */
	public Map<String, String> getFixedFields() {
		return fixedFields;
	}

	/**
	 * @param fixedFields each field key that may not change, mapped to the reason it cannot
	 */
	public void setFixedFields(Map<String, String> fixedFields) {
		this.fixedFields = fixedFields;
	}
}
