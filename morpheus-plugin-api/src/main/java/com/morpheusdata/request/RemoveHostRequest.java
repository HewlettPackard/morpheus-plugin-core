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

package com.morpheusdata.request;

/**
 * Request object for removing a host via
 * {@link com.morpheusdata.core.MorpheusComputeServerService#removeHost}.
 *
 * @since 1.4.2
 */
public class RemoveHostRequest {

	/**
	 * Indicates if the remove should be forced and bypass other checks.
	 */
	public boolean force = false;

	/**
	 * Indicates if associated resources should be removed.
	 */
	public boolean removeResources = false;

	/**
	 * Indicates if associated instances should be removed.
	 */
	public boolean removeInstances = false;

	/**
	 * ID of the user requesting the removal.
	 */
	public Long userId;

	/**
	 * Indicates if policy checks should be skipped.
	 */
	public boolean skipPolicyCheck = false;
}
