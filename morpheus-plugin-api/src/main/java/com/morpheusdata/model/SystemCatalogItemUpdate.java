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

package com.morpheusdata.model;

/**
 * Represents a catalog item update received from Central Services.
 * Passed to {@link com.morpheusdata.core.providers.SystemProvider.CatalogItemFacet} implementations
 * when a change is detected in the CS catalog (via hash comparison).
 *
 * @author Morpheus Development Team
 * @since 1.4.x
 */
public class SystemCatalogItemUpdate {

	/**
	 * Human-readable name of the catalog item.
	 */
	private String name;

	/**
	 * Unique code identifying the catalog item.
	 */
	private String code;

	/**
	 * Hash of the catalog item payload, used for change detection.
	 */
	private String hash;

	/**
	 * The full catalog item payload as received from Central Services.
	 */
	private String payload;

	/**
	 * Optional version string for the catalog item.
	 */
	private String version;

	public SystemCatalogItemUpdate() {}

	public SystemCatalogItemUpdate(String name, String code, String hash, String payload, String version) {
		this.name = name;
		this.code = code;
		this.hash = hash;
		this.payload = payload;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
