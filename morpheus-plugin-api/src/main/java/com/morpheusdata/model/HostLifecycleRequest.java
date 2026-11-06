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

import java.util.Map;

/**
 * Request passed to every method of {@link com.morpheusdata.core.providers.StorageProviderHostLifecycle}.
 * <p>
 * Each {@code validate*} method may refuse by returning {@code ServiceResponse.error} with a message
 * the operator is shown; whether the operator may proceed anyway rides in the returned
 * {@link HostLifecycleResult#getOverridable()}. On retry the operator's choice to proceed is carried
 * back in {@link #getOverridden()}/{@link #getOverriddenBy()}; the provider decides whether to honor it.
 *
 * @since 1.6.0
 * @see com.morpheusdata.core.providers.StorageProviderHostLifecycle
 * @see HostLifecycleResult
 */
public class HostLifecycleRequest {

	protected ComputeServerGroup cluster;
	protected String trigger;
	protected Boolean evacuated;
	protected Boolean overridden;
	protected String overriddenBy;
	protected Map<String, Object> config;

	/**
	 * @return the cluster the host belongs to
	 */
	public ComputeServerGroup getCluster() {
		return cluster;
	}

	/**
	 * @param cluster the cluster the host belongs to
	 */
	public void setCluster(ComputeServerGroup cluster) {
		this.cluster = cluster;
	}

	/**
	 * @return one of {@code maintenance}, {@code reboot}, {@code shutdown}, {@code removal}
	 */
	public String getTrigger() {
		return trigger;
	}

	/**
	 * @param trigger one of {@code maintenance}, {@code reboot}, {@code shutdown}, {@code removal}
	 */
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	/**
	 * @return true when the host's virtual machines are already off the host
	 */
	public Boolean getEvacuated() {
		return evacuated;
	}

	/**
	 * @param evacuated true when the host's virtual machines are already off the host
	 */
	public void setEvacuated(Boolean evacuated) {
		this.evacuated = evacuated;
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
	 * @return who overrode the refusal, recorded against the host with the refusal
	 */
	public String getOverriddenBy() {
		return overriddenBy;
	}

	/**
	 * @param overriddenBy who overrode the refusal, recorded against the host with the refusal
	 */
	public void setOverriddenBy(String overriddenBy) {
		this.overriddenBy = overriddenBy;
	}

	/**
	 * @return detail specific to the trigger
	 */
	public Map<String, Object> getConfig() {
		return config;
	}

	/**
	 * @param config detail specific to the trigger
	 */
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
}
