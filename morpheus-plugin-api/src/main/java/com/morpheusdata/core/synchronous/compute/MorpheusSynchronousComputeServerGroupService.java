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

package com.morpheusdata.core.synchronous.compute;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.ComputeTypeLayout;
import com.morpheusdata.request.AddServerGroupServersRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.List;

public interface MorpheusSynchronousComputeServerGroupService extends MorpheusSynchronousDataService<ComputeServerGroup, ComputeServerGroup>, MorpheusSynchronousIdentityService<ComputeServerGroup> {
	/**
	 * Returns the Compute Server Group Type Service
	 *
	 * @return An instance of the Compute Server Group Type Service
	 */
	MorpheusSynchronousComputeServerGroupTypeService getType();

	/**
	 * Add one or more servers to an existing cluster. Follows the same flow as the
	 * {@code POST /api/clusters/:id/servers} REST API endpoint, including license checks,
	 * policy enforcement, and async provisioning.
	 *
	 * @param cluster the target cluster (server group)
	 * @param layout  the ComputeTypeLayout to use for provisioning
	 * @param request the request object describing the server(s) to add
	 * @return a {@link ServiceResponse} containing the list of provisioned {@link ComputeServer} objects on success
	 * @since 1.4.0
	 */
	ServiceResponse<List<ComputeServer>> addServerGroupServers(ComputeServerGroup cluster, ComputeTypeLayout layout, AddServerGroupServersRequest request);
}
