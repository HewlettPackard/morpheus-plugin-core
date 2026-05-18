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

import com.morpheusdata.model.ComputeServerInterface;
import com.morpheusdata.model.ComputeServerType;
import com.morpheusdata.model.ServicePlan;
import com.morpheusdata.model.StorageVolume;

import java.util.List;
import java.util.Map;

/**
 * Request object for adding server(s) to an existing cluster via
 * {@link com.morpheusdata.core.MorpheusComputeServerGroupService#addServerGroupServers}.
 *
 * @since 1.4.0
 */
public class AddServerGroupServersRequest {

	/**
	 * The type of server to add to the cluster.
	 */
	public ComputeServerType serverType;

	/**
	 * Optional display name for the server(s). If not provided, a name will be auto-generated
	 * based on the cluster name and existing node count.
	 */
	public String serverName;

	/**
	 * Optional hostname for the server(s). Defaults to {@link #serverName} if not provided.
	 */
	public String hostname;

	/**
	 * The service plan (sizing) to use for the server(s).
	 */
	public ServicePlan plan;

	/**
	 * Additional configuration options passed to the provisioner.
	 * May include provider-specific settings such as {@code resourcePoolId}, {@code customOptions}, etc.
	 * May also include a {@code server} map to set additional params directly on the server object in the config
	 */
	public Map<String, Object> config;

	/**
	 * ID of the cloud to provision into. Defaults to the cluster's cloud if not specified.
	 */
	public Long zoneId;

	/**
	 * ID of the group/site to provision into. Defaults to the cluster's site if not specified.
	 */
	public Long siteId;

	/**
	 * Number of nodes to add. Defaults to 1.
	 */
	public Integer nodeCount = 1;

	/**
	 * Storage volumes to attach to the server(s).
	 */
	public List<StorageVolume> volumes;

	/**
	 * Network interfaces to configure on the server(s).
	 */
	public List<ComputeServerInterface> networkInterfaces;

	/**
	 * Security group IDs to apply to the server(s).
	 */
	public List<Long> securityGroupIds;

	/**
	 * Whether to perform license and connectivity checks before provisioning.
	 * Set to {@code false} to skip these checks. Defaults to {@code true}.
	 */
	public boolean licenseCheck = true;
}
