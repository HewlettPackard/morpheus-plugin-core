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
 * Result returned by every method of {@link com.morpheusdata.core.providers.StorageProviderHostLifecycle}.
 *
 * @since 1.6.0
 * @see com.morpheusdata.core.providers.StorageProviderHostLifecycle
 * @see HostLifecycleRequest
 */
public class HostLifecycleResult {

	protected Boolean overridable;
	protected String health;
	protected Integer resyncPercent;
	protected Long retryAfterSeconds;

	/**
	 * @return whether a refusal may be overridden by the operator
	 */
	public Boolean getOverridable() {
		return overridable;
	}

	/**
	 * @param overridable whether a refusal may be overridden by the operator
	 */
	public void setOverridable(Boolean overridable) {
		this.overridable = overridable;
	}

	/**
	 * @return one of {@code healthy}, {@code resyncing}, {@code degraded}
	 */
	public String getHealth() {
		return health;
	}

	/**
	 * @param health one of {@code healthy}, {@code resyncing}, {@code degraded}
	 */
	public void setHealth(String health) {
		this.health = health;
	}

	/**
	 * @return progress, 0-100, while {@link #getHealth()} is {@code resyncing}
	 */
	public Integer getResyncPercent() {
		return resyncPercent;
	}

	/**
	 * @param resyncPercent progress, 0-100, while {@link #getHealth()} is {@code resyncing}
	 */
	public void setResyncPercent(Integer resyncPercent) {
		this.resyncPercent = resyncPercent;
	}

	/**
	 * @return seconds until the plugin expects to allow the operation, where it currently refuses
	 */
	public Long getRetryAfterSeconds() {
		return retryAfterSeconds;
	}

	/**
	 * @param retryAfterSeconds seconds until the plugin expects to allow the operation, where it currently refuses
	 */
	public void setRetryAfterSeconds(Long retryAfterSeconds) {
		this.retryAfterSeconds = retryAfterSeconds;
	}
}
