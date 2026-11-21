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
import com.morpheusdata.model.ResourceReservationApplyResult;
import com.morpheusdata.model.ResourceReservationRequest;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;
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

	/**
	 * Applies every currently recorded reservation on this host: evacuates the host
	 * (maintenance mode), invokes hvmcli to write/reboot into the reserved state, verifies
	 * it landed, then restores the host to service. Long-running; the returned
	 * {@code ServiceResponse} carries a {@code processId} a caller can poll/subscribe to for
	 * progress, mirroring the existing Process UI used by host-profile remediation.
	 * <p>
	 * It does not need a {@code reservedBy} filter to know what to apply — by default it
	 * applies every {@code reservedBy} entry already recorded against this host, matching
	 * hvmcli's own behavior of merging all of a host's recorded components into a single
	 * boot/hugepage state. {@code opts[reservedBy]} is an optional narrowing filter, not a
	 * requirement for correctness.
	 * <p>
	 * If this host cannot be evacuated because it is running a VM that cannot be migrated
	 * off it (notably the Morpheus appliance/System VM itself, typically deployed on the
	 * host's local storage — core has no migration mechanism for a VM with no shared
	 * datastore to move to), this still applies every no-reboot-safe change and returns
	 * with the host's entry {@code state=BLOCKED} rather than failing the whole call; the
	 * reboot-requiring remainder is left pending for the caller to resolve (e.g. by
	 * completing its own install, building a shared datastore, and migrating the blocking
	 * VM itself) and retry.
	 *
	 * @param server the host to apply reservations on
	 * @param opts optional narrowing, e.g. {@code reservedBy} to validate/recompute a single
	 *             component's flattened keys
	 * @return a process-tracked result with one entry in {@code hosts} for this host
	 */
	Single<ServiceResponse<ResourceReservationApplyResult>> applyReservation(ComputeServer server, Map opts);

	/**
	 * Same operation fanned out across every host in the cluster.
	 *
	 * @param cluster the cluster to apply reservations across
	 * @param opts optional narrowing, e.g. {@code reservedBy}
	 * @return a process-tracked result with one entry in {@code hosts} per cluster host
	 */
	Single<ServiceResponse<ResourceReservationApplyResult>> applyReservations(ComputeServerGroup cluster, Map opts);

	/**
	 * Removes one component's reservation from this host: reads the host's current reservation
	 * state, evacuates the host (maintenance mode) when the release needs a reboot, invokes
	 * hvmcli to release the component's reserved capacity, verifies it is gone, then restores
	 * the host to service. Long-running; the returned {@code ServiceResponse} carries a
	 * {@code processId} a caller can poll/subscribe to for progress.
	 * <p>
	 * This is idempotent: if {@code reservedBy} holds nothing on the host, the host's entry is
	 * returned with {@code state=SKIPPED} and nothing is changed. A release that needs no reboot
	 * runs directly without evacuating the host. As with {@link #applyReservation}, a host that
	 * cannot be evacuated because of a VM that cannot be migrated off it is reported as
	 * {@code state=BLOCKED} (or {@code PENDING_REBOOT} when the reboot is deferred) rather than
	 * failing the whole call.
	 * <p>
	 * <b>Ordering contract:</b> a successful host release does <b>not</b> remove the recorded
	 * reservation from the Host Profile. Callers must release on the host first, using this
	 * method, and only then remove the recorded reservation with {@link #releaseResources}.
	 * Removing the record first leaves the host still holding the reserved capacity with nothing
	 * recording that it is held.
	 *
	 * @param server the host to release the reservation on
	 * @param reservedBy the component whose reservation is released
	 * @param opts optional settings, e.g. timeouts and polling intervals
	 * @return a process-tracked result with one entry in {@code hosts} for this host
	 * @since 1.7.0
	 */
	default Single<ServiceResponse<ResourceReservationApplyResult>> releaseReservation(ComputeServer server, String reservedBy, Map opts) {
		return Single.just(ServiceResponse.error("releaseReservation is not supported by this implementation"));
	}

	/**
	 * Same operation fanned out across every host in the cluster. A host that holds nothing for
	 * {@code reservedBy} is reported as {@code SKIPPED}; one host failing or being blocked does
	 * not prevent the remaining hosts from being released.
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
	default Single<ServiceResponse<ResourceReservationApplyResult>> releaseReservations(ComputeServerGroup cluster, String reservedBy, Map opts) {
		return Single.just(ServiceResponse.error("releaseReservations is not supported by this implementation"));
	}
}
