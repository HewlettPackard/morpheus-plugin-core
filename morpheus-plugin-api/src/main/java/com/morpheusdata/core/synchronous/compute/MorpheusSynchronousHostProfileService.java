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
import com.morpheusdata.model.ResourceReservationApplyResult;
import com.morpheusdata.model.ResourceReservationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.Map;
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

	/**
	 * Applies every currently recorded reservation on this host: evacuates the host
	 * (maintenance mode), invokes hvmcli to write/reboot into the reserved state, verifies
	 * it landed, then restores the host to service.
	 * <p>
	 * If this host cannot be evacuated because it is running a VM that cannot be migrated
	 * off it (notably the Morpheus appliance/System VM itself, typically deployed on the
	 * host's local storage — core has no migration mechanism for a VM with no shared
	 * datastore to move to), this still applies every no-reboot-safe change and returns
	 * with the host's entry {@code state=BLOCKED} rather than failing the whole call.
	 *
	 * @param server the host to apply reservations on
	 * @param opts optional narrowing, e.g. {@code reservedBy}
	 * @return a process-tracked result with one entry in {@code hosts} for this host
	 */
	ServiceResponse<ResourceReservationApplyResult> applyReservation(ComputeServer server, Map opts);

	/**
	 * Same operation fanned out across every host in the cluster.
	 *
	 * @param cluster the cluster to apply reservations across
	 * @param opts optional narrowing, e.g. {@code reservedBy}
	 * @return a process-tracked result with one entry in {@code hosts} per cluster host
	 */
	ServiceResponse<ResourceReservationApplyResult> applyReservations(ComputeServerGroup cluster, Map opts);

	/**
	 * Removes one component's reservation from this host, evacuating and rebooting it only when
	 * the release requires it. Idempotent: a host where {@code reservedBy} holds nothing is
	 * returned as {@code SKIPPED}.
	 * <p>
	 * <b>Ordering contract:</b> a successful host release does <b>not</b> remove the recorded
	 * reservation from the Host Profile. Callers must release on the host first, using this
	 * method, and only then remove the recorded reservation with {@link #releaseResources}.
	 *
	 * @param server the host to release the reservation on
	 * @param reservedBy the component whose reservation is released
	 * @param opts optional settings, e.g. timeouts and polling intervals
	 * @return a process-tracked result with one entry in {@code hosts} for this host
	 * @since 1.7.0
	 */
	default ServiceResponse<ResourceReservationApplyResult> releaseReservation(ComputeServer server, String reservedBy, Map opts) {
		return ServiceResponse.error("releaseReservation is not supported by this implementation");
	}

	/**
	 * Same operation fanned out across every host in the cluster.
	 * <p>
	 * <b>Ordering contract:</b> a successful host release does <b>not</b> remove the recorded
	 * reservation from the Host Profile. Callers must release on the hosts first, using this
	 * method, and only then remove the recorded reservation with {@link #releaseResources}.
	 *
	 * @param cluster the cluster to release the reservation across
	 * @param reservedBy the component whose reservation is released
	 * @param opts optional settings, e.g. timeouts and polling intervals
	 * @return a process-tracked result with one entry in {@code hosts} per cluster host
	 * @since 1.7.0
	 */
	default ServiceResponse<ResourceReservationApplyResult> releaseReservations(ComputeServerGroup cluster, String reservedBy, Map opts) {
		return ServiceResponse.error("releaseReservations is not supported by this implementation");
	}
}
