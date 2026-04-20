/*
 *  Copyright 2025 Morpheus Data, LLC.
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

package com.morpheusdata.model.projection;

/**
 * Provides a subset of properties from the {@link com.morpheusdata.model.system.System}
 * for comparison with less bandwidth usage and memory footprint. This is a DTO Projection object.
 */
public class SystemIdentityProjection extends MorpheusIdentityModel {
	protected String name;
	protected String externalId;
	/**
	 * The {@link com.morpheusdata.model.system.SystemType#getCode()} mapped here for quick filter search.
	 */
	protected String typeCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name, this.name);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId, this.externalId);
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
		markDirty("typeCode", typeCode, this.typeCode);
	}
}
