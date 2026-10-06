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

package com.morpheusdata.core.synchronous.compute;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.ResourceReservation;
import com.morpheusdata.model.ResourceReservationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.List;

/**
 * Synchronous counterpart to {@link com.morpheusdata.core.MorpheusHostProfileService}.
 *
 * @since 1.6.0
 */
public interface MorpheusSynchronousHostProfileService {

	/**
	 * Records or replaces one component's reservation on every host of a cluster.
	 *
	 * @param cluster the cluster whose host profile is written
	 * @param request the amounts reserved, and the component reserving them
	 * @return one row per host, carrying the amount now held and the impact of reaching it
	 */
	ServiceResponse<List<ResourceReservation>> reserveResources(ComputeServerGroup cluster, ResourceReservationRequest request);

	/**
	 * The same operation against a single host.
	 *
	 * @param server the host whose profile is written
	 * @param request the amounts reserved, and the component reserving them
	 * @return the reservation now held on this host
	 */
	ServiceResponse<List<ResourceReservation>> reserveResources(ComputeServer server, ResourceReservationRequest request);

	/**
	 * Withdraws a reservation and returns the capacity to the hosts.
	 *
	 * @param cluster the cluster whose reservation is withdrawn
	 * @param reservedBy the component whose reservation is withdrawn
	 * @return one row per host, carrying the remaining reservations (if any) after release
	 */
	ServiceResponse<List<ResourceReservation>> releaseResources(ComputeServerGroup cluster, String reservedBy);

	/**
	 * Reports every reservation held on a cluster, whichever component holds it.
	 *
	 * @param cluster the cluster to report on
	 * @return every reservation held on the cluster's hosts, across all components
	 */
	ServiceResponse<List<ResourceReservation>> listReservations(ComputeServerGroup cluster);
}
