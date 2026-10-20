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

package com.morpheusdata.request;

import com.morpheusdata.model.Process;

import java.util.List;
import java.util.Map;

/**
 * Request passed to every storage day-two operation facet, such as
 * {@link com.morpheusdata.core.providers.StorageProviderNodeRole} and
 * {@link com.morpheusdata.core.providers.StorageProviderDriveAddition}.
 * <p>
 * One method serves both preview and execution. With {@link OpType#DRY_RUN} the provider judges the
 * change and touches nothing, returning the steps it would take. With {@link OpType#UPDATE} it re-runs
 * the same checks and performs the change, so a dry run is a preview and never a required first step.
 * A refusal rides in the {@link com.morpheusdata.response.ServiceResponse}; when the returned
 * {@link com.morpheusdata.model.StorageOperation#getOverridable() operation is overridable} the operator
 * may retry with {@link #getOverridden() overridden} set.
 *
 * @since 1.6.0
 * @see com.morpheusdata.model.StorageOperation
 */
public class StorageOperationRequest {

	protected OpType opType;
	protected Process process;
	protected Boolean overridden;
	protected String overriddenBy;

	protected Map<String, Object> config;
	protected Map<String, Object> previousConfig;
	protected List<String> changedKeys;

	/**
	 * Whether the request judges the change or performs it.
	 */
	public enum OpType {
		/** Performs the change, re-running the same checks a dry run makes. */
		UPDATE,
		/** Judges the change and returns the steps it would take, changing nothing. */
		DRY_RUN
	}

	/**
	 * @return {@link OpType#DRY_RUN} to judge the change, {@link OpType#UPDATE} to perform it
	 */
	public OpType getOpType() {
		return opType;
	}

	/**
	 * @param opType {@link OpType#DRY_RUN} to judge the change, {@link OpType#UPDATE} to perform it
	 */
	public void setOpType(OpType opType) {
		this.opType = opType;
	}

	/**
	 * @return the Morpheus process the change is tracked under
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @param process the Morpheus process the change is tracked under
	 */
	public void setProcess(Process process) {
		this.process = process;
	}

	/**
	 * @return true when an operator proceeded past an overridable refusal
	 */
	public Boolean getOverridden() {
		return overridden;
	}

	/**
	 * @param overridden true when an operator proceeded past an overridable refusal
	 */
	public void setOverridden(Boolean overridden) {
		this.overridden = overridden;
	}

	/**
	 * @return who overrode the refusal, recorded against the operation with the refusal
	 */
	public String getOverriddenBy() {
		return overriddenBy;
	}

	/**
	 * @param overriddenBy who overrode the refusal, recorded against the operation with the refusal
	 */
	public void setOverriddenBy(String overriddenBy) {
		this.overriddenBy = overriddenBy;
	}

	/**
	 * @return detail specific to the operation
	 */
	public Map<String, Object> getConfig() {
		return config;
	}

	/**
	 * @param config detail specific to the operation
	 */
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}

	/**
	 * @return what is in force now, where the operation edits a stored configuration
	 */
	public Map<String, Object> getPreviousConfig() {
		return previousConfig;
	}

	/**
	 * @param previousConfig what is in force now, where the operation edits a stored configuration
	 */
	public void setPreviousConfig(Map<String, Object> previousConfig) {
		this.previousConfig = previousConfig;
	}

	/**
	 * @return the fields the operator actually changed
	 */
	public List<String> getChangedKeys() {
		return changedKeys;
	}

	/**
	 * @param changedKeys the fields the operator actually changed
	 */
	public void setChangedKeys(List<String> changedKeys) {
		this.changedKeys = changedKeys;
	}
}
