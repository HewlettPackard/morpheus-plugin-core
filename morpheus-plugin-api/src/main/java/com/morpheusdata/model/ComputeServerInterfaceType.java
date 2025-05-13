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

import java.util.List;

public class ComputeServerInterfaceType extends MorpheusModel {

	protected String name;
	protected String externalId;
	protected String code;
	protected Integer displayOrder;
	protected Boolean defaultType;
	protected Boolean enabled;
	protected Boolean bonded;
	protected Boolean vlan;
	protected Boolean deleteOnWorkloadRemoval;
	protected Boolean hasChildInterfaces;
	protected List<ComputeServerInterfaceType> childTypes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
		markDirty("displayOrder", displayOrder);
	}

	public Boolean getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Boolean defaultType) {
		this.defaultType = defaultType;
		markDirty("defaultType", defaultType);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Boolean getBonded() {
		return bonded;
	}

	public void setBonded(Boolean bonded) {
		this.bonded = bonded;
		markDirty("bonded", bonded);
	}

	public Boolean getVlan() {
		return vlan;
	}

	public void setVlan(Boolean vlan) {
		this.vlan = vlan;
		markDirty("vlan", vlan);
	}

	/**
	 * @return true to delete a ComputeServerInterface of this type after workload removal
	 */
	public Boolean getDeleteOnWorkloadRemoval() {
		return deleteOnWorkloadRemoval;
	}

	/**
	 * Flags whether a ComputeServerInterface of this type should be deleted after workload removal
	 * <p>
	 * Core will carry out the deletion after a successful and/or forced removal of the workload is complete.
	 * @param deleteOnWorkloadRemoval true to delete a ComputeServerInterface of this type after workload removal
	 */
	public void setDeleteOnWorkloadRemoval(Boolean deleteOnWorkloadRemoval) {
		this.deleteOnWorkloadRemoval = deleteOnWorkloadRemoval;
		markDirty("deleteOnWorkloadRemoval", deleteOnWorkloadRemoval);
	}

	public Boolean getHasChildInterfaces() {
		return hasChildInterfaces;
	}

	public void setHasChildInterfaces(Boolean hasChildInterfaces) {
		this.hasChildInterfaces = hasChildInterfaces;
		markDirty("hasChildInterfaces", hasChildInterfaces);
	}

	public List<ComputeServerInterfaceType> getChildTypes() {
		return childTypes;
	}

	public void setChildTypes(List<ComputeServerInterfaceType> childTypes) {
		this.childTypes = childTypes;
		markDirty("childTypes", childTypes);
	}
}
