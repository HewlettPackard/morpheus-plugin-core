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
 * One host's entry within a {@link ResourceReservationApplyResult}.
 *
 * @since 1.6.0
 */
public class ResourceReservationHostApplyResult {

	/** The {@link ComputeServer} this entry reports on. */
	public Long hostId;

	/**
	 * This host's own progress. {@code SKIPPED} is used for hosts not yet started after a
	 * sequential cluster abort; {@code BLOCKED} is used when this host could not be
	 * evacuated because of a non-migratable VM (notably the Morpheus appliance/System VM,
	 * typically deployed on the host's local storage, which core has no migration
	 * mechanism to move off a host with no shared datastore to move to) — every
	 * no-reboot-safe change is still applied, and the reboot-requiring remainder is left
	 * pending for the caller to resolve (e.g. by completing its own install, building a
	 * shared datastore, and migrating the blocking VM itself) and retry.
	 */
	public String state; // PENDING | RUNNING | COMPLETE | FAILED | SKIPPED | BLOCKED

	/**
	 * Human-readable progress/error detail, surfaced in the Process UI. For
	 * {@code state=BLOCKED}, names the blocking VM and the reason it could not migrate
	 * (e.g. "on local-only storage, no migration path off this host").
	 */
	public String detail;

	/** Whether this host's apply actually issued a reboot to take effect. */
	public Boolean rebooted;

	/**
	 * Mirrors {@link ResourceReservation#active} once settled — true once the reservation
	 * is confirmed in force on this host post-reboot.
	 */
	public Boolean active;
}
