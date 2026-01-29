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

import java.util.Date;

/**
 * Represents the state of an orchestration workflow. This model tracks
 * progress,
 * configuration, and execution state across multiple orchestration steps. It
 * can
 * be attached to any parent object that needs orchestration state tracking.
 * 
 * The state data is stored as JSON to allow flexible schema evolution and
 * accommodate different orchestration provider requirements.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class OrchestrationState extends MorpheusModel {

	protected String providerCode; // The OrchestrationProvider code
	protected String status; // pending, in-progress, completed, failed, cancelled
	protected String statusMessage;
	protected Date statusDate;

	// Orchestration state stored as JSON - contains all step data
	protected String stateData;

	// Progress tracking
	protected Integer totalSteps;
	protected Integer completedSteps;
	protected String currentStepCode;

	// Audit fields
	protected Date dateCreated;
	protected Date lastUpdated;
	protected Date completedDate;
	protected Date startedDate;

	// Configuration data collected during orchestration (JSON)
	protected String configData;

	// Result data from orchestration execution (JSON)
	protected String resultData;

	// Error tracking
	protected String errorMessage;
	protected String errorData;

	// Reference to parent object that owns this state
	protected String refType;
	protected Long refId;

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
		markDirty("providerCode", providerCode);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		markDirty("status", status);
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		markDirty("statusMessage", statusMessage);
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
		markDirty("statusDate", statusDate);
	}

	public String getStateData() {
		return stateData;
	}

	public void setStateData(String stateData) {
		this.stateData = stateData;
		markDirty("stateData", stateData);
	}

	public Integer getTotalSteps() {
		return totalSteps;
	}

	public void setTotalSteps(Integer totalSteps) {
		this.totalSteps = totalSteps;
		markDirty("totalSteps", totalSteps);
	}

	public Integer getCompletedSteps() {
		return completedSteps;
	}

	public void setCompletedSteps(Integer completedSteps) {
		this.completedSteps = completedSteps;
		markDirty("completedSteps", completedSteps);
	}

	public String getCurrentStepCode() {
		return currentStepCode;
	}

	public void setCurrentStepCode(String currentStepCode) {
		this.currentStepCode = currentStepCode;
		markDirty("currentStepCode", currentStepCode);
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

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
		markDirty("completedDate", completedDate);
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
		markDirty("startedDate", startedDate);
	}

	public String getConfigData() {
		return configData;
	}

	public void setConfigData(String configData) {
		this.configData = configData;
		markDirty("configData", configData);
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
		markDirty("resultData", resultData);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		markDirty("errorMessage", errorMessage);
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
		markDirty("errorData", errorData);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}
}
