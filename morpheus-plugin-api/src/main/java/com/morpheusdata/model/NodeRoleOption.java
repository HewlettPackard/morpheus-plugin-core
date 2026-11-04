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
 * A role a {@link StorageServerNode} may take, as reported by
 * {@link com.morpheusdata.core.providers.StorageProviderNodeRole#getNodeRoleConstraints}.
 * <p>
 * A role the cluster cannot take now is returned with {@link #getAvailable() available} false and the
 * {@link #getReason() reason}, so the picker shows it before the operator commits.
 *
 * @since 1.6.0
 */
public class NodeRoleOption {

	protected String role;
	protected String name;
	protected Boolean available;
	protected String reason;
	protected Integer minimumDrives;

	/**
	 * @return the role code; one of the {@link StorageServerNodePersona} names
	 *         ({@code symmetric}, {@code controller}, {@code storage})
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role code; one of the {@link StorageServerNodePersona} names
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the name shown in the picker
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name shown in the picker
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return whether the node may take this role now
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available whether the node may take this role now
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/**
	 * @return why the role is not available, when {@link #getAvailable()} is false
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason why the role is not available, when {@link #getAvailable()} is false
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the number of drives the role requires; 0 when it claims none
	 */
	public Integer getMinimumDrives() {
		return minimumDrives;
	}

	/**
	 * @param minimumDrives the number of drives the role requires; 0 when it claims none
	 */
	public void setMinimumDrives(Integer minimumDrives) {
		this.minimumDrives = minimumDrives;
	}
}
