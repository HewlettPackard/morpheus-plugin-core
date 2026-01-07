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

/**
 * Representation of a Morpheus StorageAggregateType database object within the Morpheus platform.
 * This defines the type of storage aggregation mechanism (e.g., RAID, ZFS, LVM, etc.)
 *
 * @author Mike Carlin
 * @since 1.3.0
 */
public class StorageAggregateType extends MorpheusModel {

	/**
	 * The name of the storage aggregate type (e.g., "RAID 5", "ZFS Mirror", "LVM Volume Group")
	 */
	protected String name;

	/**
	 * A unique code identifying this type (e.g., "raid5", "zfs-mirror", "lvm-vg")
	 */
	protected String code;

	/**
	 * Description of the storage aggregate type
	 */
	protected String description;

	/**
	 * Gets the name of the storage aggregate type
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the storage aggregate type
	 * @param name the type name
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Gets the unique code for this type
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code for this type
	 * @param code the type code
	 */
	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	/**
	 * Gets the description of the storage aggregate type
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the storage aggregate type
	 * @param description the type description
	 */
	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}
}

