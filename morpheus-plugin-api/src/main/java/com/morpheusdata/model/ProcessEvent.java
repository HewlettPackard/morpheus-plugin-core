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

import java.util.Map;

/**
 * Represents an event (or step) within a {@link Process}
 */
public class ProcessEvent {

	/**
	 * @deprecated Use {@link #stepType} instead.
	 */
	public ProcessType type;
	public ProcessStepType stepType;
	public String jobName;
	public Map jobConfig;

	/**
	 * @deprecated Use {@link #getStepType()} instead.
	 */
	@Deprecated(since = "1.2.8")
	public ProcessType getType() {
		return type;
	}

	/**
	 * @deprecated Use {@link #setStepType(ProcessStepType)} instead.
	 */
	@Deprecated(since = "1.2.8")
	public void setType(ProcessType type) {
		this.type = type;
	}

	/**
	 * The type of step this event represents.
	 * <p>
	 *  See {@link ProcessStepType} for some examples from core.
	 * @return step type
	 */
	public ProcessStepType getStepType() {
		return stepType;
	}

	/**
	 * Sets the type of step for this event
	 * <p>
	 * See {@link ProcessStepType} for some examples from core.
	 * @param stepType step type
	 */
	public void setStepType(ProcessStepType stepType) {
		this.stepType = stepType;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map getJobConfig() {
		return jobConfig;
	}

	public void setJobConfig(Map jobConfig) {
		this.jobConfig = jobConfig;
	}

	/**
	 * @deprecated Use {@link ProcessStepType} instead.
	 */
	@Deprecated(since = "1.2.8")
	public enum ProcessType {
		ansibleCommand,
		ansibleInstall,
		ansiblePlaybook,
		ansibleProvision,
		ansibleRepo,
		ansibleTowerInventory,
		ansibleTowerJobLaunch,
		ansibleTowerJobTemplate,
		ansibleTowerProvision,
		applyPackage,
		applyResourceSpec,
		appProvision,
		appTerraformCommand,
		azureArmProvision,
		azureOperation,
		chefBootstrap,
		chefInstall,
		chefProvision,
		chefRun,
		cloning,
		configureResources,
		containerScript,
		containerTask,
		containerTemplate,
		containerWorkflow,
		deletePackage,
		deletesnapshot,
		deploy,
		deployFiles,
		deployPackage,
		deployScanner,
		deployStartInstance,
		deployStopInstance,
		executeAction,
		executeCommand,
		executeScan,
		executeScript,
		executeTask,
		executeTemplate,
		executeWorkflow,
		extractResults,
		general,
		guestCustomizations,
		instanceAction,
		instanceTask,
		instanceTerraformCommand,
		instanceWorkflow,
		localTask,
		localWorkflow,
		planResources,
		postProvision,
		provision,
		provisionAgent,
		provisionAppDeploy,
		provisionCloudInit,
		provisionConfig,
		provisionDeploy,
		provisionFinalize,
		provisionImage,
		provisionInstances,
		provisionItem,
		provisionItems,
		provisionLaunch,
		provisionNetwork,
		provisionResize,
		provisionResolve,
		provisionResources,
		provisionState,
		provisionStateRefresh,
		provisionUpdates,
		provisionVolumes,
		reconfigure,
		resize,
		resizeMemory,
		resizeStart,
		resizeStopInstance,
		resizeVolumes,
		resourceConfig,
		revert,
		saltCommand,
		saltExecute,
		saltInstall,
		saltMinion,
		saltProvision,
		saltState,
		securityScan,
		serverGroupProvision,
		serverGroupWorkflow,
		serverProvision,
		serverScript,
		serverTask,
		serverWorkflow,
		shutdown,
		snapshot,
		startup,
		teardown,
		terraformCommand,
		updatePackage,
		upgradePackage,
	}
}
