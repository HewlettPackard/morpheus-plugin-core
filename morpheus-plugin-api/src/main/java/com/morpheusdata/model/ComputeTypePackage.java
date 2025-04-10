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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelCollectionAsIdsOnlySerializer;
import com.morpheusdata.model.serializers.ModelCollectionIdCodeNameSerializer;
import com.morpheusdata.model.serializers.ModelCollectionIdUuidCodeNameSerializer;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ComputeTypePackage extends MorpheusModel {

	protected String code;
	protected String name;
	protected String packageVersion;
	protected Integer sortOrder;
	protected Boolean enabled; //flag for tuning this on or off
	protected Date dateCreated;
	protected Date lastUpdated;
	protected Boolean isPlugin;
	protected String description;
	protected String category;
	protected String type;
	protected String packageType;
	protected ProviderType providerType;
	protected Long memoryRequirement;
	protected String userData;
	protected String containerRuntime;
	protected String vmRuntime;
	protected String storageRuntime;
	protected String networkRuntime;
	//compatibility
	protected String computeVersion;
	protected String clusterVersion;
	protected Boolean globalPackage;

	@JsonSerialize(using = ModelCollectionIdCodeNameSerializer.class)
	protected List<OptionType> optionTypes;
	@JsonSerialize(using = ModelCollectionIdUuidCodeNameSerializer.class)
	protected List<ResourceSpecTemplate> specTemplates;
	@JsonSerialize(using= ModelCollectionAsIdsOnlySerializer.class)
	protected List<WorkloadTemplate> templates;
	@JsonSerialize(using = ModelCollectionIdCodeNameSerializer.class)
	protected List<WorkloadScript> scripts;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
		markDirty("packageVersion", packageVersion);
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
		markDirty("sortOrder", sortOrder);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		markDirty("lastUpdated", lastUpdated);
	}

	public Boolean getPlugin() {
		return isPlugin;
	}

	public void setPlugin(Boolean plugin) {
		isPlugin = plugin;
		markDirty("isPlugin", isPlugin);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", category);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		markDirty("type", type);
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
		markDirty("packageType", packageType);
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
		markDirty("providerType", providerType);
	}

	public Long getMemoryRequirement() {
		return memoryRequirement;
	}

	public void setMemoryRequirement(Long memoryRequirement) {
		this.memoryRequirement = memoryRequirement;
		markDirty("memoryRequirement", memoryRequirement);
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
		markDirty("userData", userData);
	}

	public String getContainerRuntime() {
		return containerRuntime;
	}

	public void setContainerRuntime(String containerRuntime) {
		this.containerRuntime = containerRuntime;
		markDirty("containerRuntime", containerRuntime);
	}

	public String getVmRuntime() {
		return vmRuntime;
	}

	public void setVmRuntime(String vmRuntime) {
		this.vmRuntime = vmRuntime;
		markDirty("vmRuntime", vmRuntime);
	}

	public String getStorageRuntime() {
		return storageRuntime;
	}

	public void setStorageRuntime(String storageRuntime) {
		this.storageRuntime = storageRuntime;
		markDirty("storageRuntime", storageRuntime);
	}

	public String getNetworkRuntime() {
		return networkRuntime;
	}

	public void setNetworkRuntime(String networkRuntime) {
		this.networkRuntime = networkRuntime;
		markDirty("networkRuntime", networkRuntime);
	}

	public String getComputeVersion() {
		return computeVersion;
	}

	public void setComputeVersion(String computeVersion) {
		this.computeVersion = computeVersion;
		markDirty("computeVersion", computeVersion);
	}

	public String getClusterVersion() {
		return clusterVersion;
	}

	public void setClusterVersion(String clusterVersion) {
		this.clusterVersion = clusterVersion;
		markDirty("clusterVersion", clusterVersion);
	}

	public Boolean getGlobalPackage() {
		return globalPackage;
	}

	public void setGlobalPackage(Boolean globalPackage) {
		this.globalPackage = globalPackage;
		markDirty("globalPackage", globalPackage);
	}

	public List<OptionType> getOptionTypes() {
		return optionTypes;
	}

	public void setOptionTypes(List<OptionType> optionTypes) {
		this.optionTypes = optionTypes;
		markDirty("optionTypes", optionTypes);
	}

	public List<ResourceSpecTemplate> getSpecTemplates() {
		return specTemplates;
	}

	public void setSpecTemplates(List<ResourceSpecTemplate> specTemplates) {
		this.specTemplates = specTemplates;
		markDirty("specTemplates", specTemplates);
	}

	public List<WorkloadTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<WorkloadTemplate> templates) {
		this.templates = templates;
		markDirty("templates", templates);
	}

	public List<WorkloadScript> getScripts() {
		return scripts;
	}

	public void setScripts(List<WorkloadScript> scripts) {
		this.scripts = scripts;
		markDirty("scripts", scripts);
	}

	public enum ProviderType {
		MVM("mvm"),
		KUBERNETES("kubernetes");

		private final String value;

		ProviderType(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}

	}


}
