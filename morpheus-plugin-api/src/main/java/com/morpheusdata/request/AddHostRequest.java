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

import com.morpheusdata.model.CloudPool;
import com.morpheusdata.model.ComputeServerInterface;
import com.morpheusdata.model.ComputeServerType;
import com.morpheusdata.model.ServicePlan;
import com.morpheusdata.model.StorageVolume;

import java.util.List;
import java.util.Map;

/**
 * Request object for adding a single host to a cloud via
 * {@link com.morpheusdata.core.MorpheusComputeServerService#addHost}.
 *
 * @since 1.4.2
 */
public class AddHostRequest {

	/**
	 * The type of server to add.
	 */
	public ComputeServerType serverType;

	/**
	 * Optional display name for the server. If not provided, a name will be auto-generated.
	 */
	public String serverName;

	/**
	 * Optional hostname for the server. Defaults to {@link #serverName} if not provided.
	 */
	public String hostname;

	/**
	 * The service plan (sizing) to use for the server.
	 */
	public ServicePlan plan;

	/**
	 * Additional configuration options passed to the provisioner.
	 * May include provider-specific settings such as {@code resourcePoolId}, {@code customOptions}, etc.
	 * May also include a {@code server} map to set additional params directly on the server object in the config.
	 */
	public Map<String, Object> config;

	/**
	 * ID of the group/site to provision into.
	 */
	public Long siteId;

	/**
	 * Storage volumes to attach to the server.
	 */
	public List<StorageVolume> volumes;

	/**
	 * Network interfaces to configure on the server.
	 */
	public List<ComputeServerInterface> networkInterfaces;

	/**
	 * Security group IDs to apply to the server.
	 */
	public List<Long> securityGroupIds;

	/**
	 * Whether to perform license and connectivity checks before provisioning.
	 * Set to {@code false} to skip these checks. Defaults to {@code true}.
	 */
	public boolean licenseCheck = true;

	/**
	 * Optional resource pool/cluster to provision into. {@code null} is valid for clouds that
	 * do not scope hosts to a resource pool/cluster.
	 */
	public CloudPool pool;
}
