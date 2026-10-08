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

package com.morpheusdata.core;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.ResourceReservation;
import com.morpheusdata.model.ResourceReservationRequest;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

/**
 * Context methods for reserving, revising, and releasing host-level CPU, memory, and hugepage
 * capacity via the Host Profile. Reservation is host-level desired state owned by the Host
 * Profile; this service lets a component (e.g. an SDS storage plugin) carry an amount, attribute
 * it to itself, and revise or release it later, without a second mechanism writing boot
 * configuration directly.
 * <p>
 * Reached from the Morpheus context beside the cluster and compute server services a plugin
 * already uses, at {@code morpheusContext.getAsync().getHostProfile()}.
 *
 * @since 1.6.0
 */
public interface MorpheusHostProfileService {

	/**
	 * Records or replaces one component's reservation on every host of a cluster. Calling it
	 * again with different amounts revises that reservation in place, which is how a reservation
	 * changes after an install. Reservations belonging to anything else are untouched.
	 * <p>
	 * This is an all-or-nothing operation: if any host in the cluster cannot satisfy the
	 * request, no host is written.
	 *
	 * @param cluster the cluster whose host profile is written
	 * @param request the amounts reserved, and the component reserving them
	 * @return one row per host, carrying the amount now held and the impact of reaching it. A
	 *         host that cannot satisfy the request is named in {@code ServiceResponse.errors}
	 *         against its id, with success {@code false}, and no host is written.
	 */
	Single<ServiceResponse<List<ResourceReservation>>> reserveResources(ComputeServerGroup cluster, ResourceReservationRequest request);

	/**
	 * The same operation against a single host, for a node joining a cluster or moving from one
	 * role to another.
	 *
	 * @param server the host whose profile is written
	 * @param request the amounts reserved, and the component reserving them
	 * @return the reservation now held on this host, carrying the impact of reaching it. If the
	 *         host cannot satisfy the request, success is {@code false} and nothing is written.
	 */
	Single<ServiceResponse<List<ResourceReservation>>> reserveResources(ComputeServer server, ResourceReservationRequest request);

	/**
	 * Withdraws a reservation and returns the capacity to the hosts. Called at uninstall, and
	 * when a node stops taking part.
	 *
	 * @param cluster the cluster whose reservation is withdrawn
	 * @param reservedBy the component whose reservation is withdrawn; every other component's
	 *                   reservation is left alone
	 * @return one row per host, carrying the remaining reservations (if any) after release
	 */
	Single<ServiceResponse<List<ResourceReservation>>> releaseResources(ComputeServerGroup cluster, String reservedBy);

	/**
	 * Reports every reservation held on a cluster, whichever component holds it. A plugin reads
	 * this to see what else is on the host before it asks for more.
	 *
	 * @param cluster the cluster to report on
	 * @return every reservation held on the cluster's hosts, across all components
	 */
	Single<ServiceResponse<List<ResourceReservation>>> listReservations(ComputeServerGroup cluster);
}
