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

package com.morpheusdata.model.health;

import java.util.Date;

/**
 * Represents the health status of the Morpheus appliance, including CPU, memory, storage,
 * threads, database, message queue, and search engine metrics.
 */
public class ApplianceHealth {

	protected Boolean success;
	protected String statusMessage;
	protected String applianceUrl;
	protected String buildVersion;
	protected String uuid;
	protected Boolean setupNeeded;
	protected Date date;

	protected ApplianceHealthCpuStats cpu;
	protected ApplianceHealthMemoryStats memory;
	protected ApplianceHealthStorageStats storage;
	protected ApplianceHealthThreadStats threads;
	protected ApplianceHealthDatabaseStats database;
	protected ApplianceHealthRabbitStats rabbit;
	protected ApplianceHealthElasticStats elastic;

	public Boolean getSuccess() { return success; }
	public void setSuccess(Boolean success) { this.success = success; }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; }

	public String getApplianceUrl() { return applianceUrl; }
	public void setApplianceUrl(String applianceUrl) { this.applianceUrl = applianceUrl; }

	public String getBuildVersion() { return buildVersion; }
	public void setBuildVersion(String buildVersion) { this.buildVersion = buildVersion; }

	public String getUuid() { return uuid; }
	public void setUuid(String uuid) { this.uuid = uuid; }

	public Boolean getSetupNeeded() { return setupNeeded; }
	public void setSetupNeeded(Boolean setupNeeded) { this.setupNeeded = setupNeeded; }

	public Date getDate() { return date; }
	public void setDate(Date date) { this.date = date; }

	public ApplianceHealthCpuStats getCpu() { return cpu; }
	public void setCpu(ApplianceHealthCpuStats cpu) { this.cpu = cpu; }

	public ApplianceHealthMemoryStats getMemory() { return memory; }
	public void setMemory(ApplianceHealthMemoryStats memory) { this.memory = memory; }

	public ApplianceHealthStorageStats getStorage() { return storage; }
	public void setStorage(ApplianceHealthStorageStats storage) { this.storage = storage; }

	public ApplianceHealthThreadStats getThreads() { return threads; }
	public void setThreads(ApplianceHealthThreadStats threads) { this.threads = threads; }

	public ApplianceHealthDatabaseStats getDatabase() { return database; }
	public void setDatabase(ApplianceHealthDatabaseStats database) { this.database = database; }

	public ApplianceHealthRabbitStats getRabbit() { return rabbit; }
	public void setRabbit(ApplianceHealthRabbitStats rabbit) { this.rabbit = rabbit; }

	public ApplianceHealthElasticStats getElastic() { return elastic; }
	public void setElastic(ApplianceHealthElasticStats elastic) { this.elastic = elastic; }
}
