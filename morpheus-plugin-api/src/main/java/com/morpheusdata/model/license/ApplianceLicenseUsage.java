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

package com.morpheusdata.model.license;

/**
 * Current appliance usage counts used for license limit checking.
 */
public class ApplianceLicenseUsage {
	// standard limitType metrics
	public Long managedServers;
	public Long discoveredServers;
	public Long hosts;
	/** @deprecated use mvmSockets */
	@Deprecated
	public Long mvm;
	public Long mvmSockets;
	public Long sockets;
	public Long iac;
	public Long xaas;
	public Long executions;
	public Long distributedWorkers;
	public Long discoveredObjects;

	// workload / capacity limitType metrics
	public Long workloads;
	/** Memory in bytes */
	public Long memory;
	/** Storage in bytes */
	public Long storage;
}
