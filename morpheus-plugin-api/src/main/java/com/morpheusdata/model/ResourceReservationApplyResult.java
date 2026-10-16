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

import java.util.List;

/**
 * Progress/result of an {@code applyReservation}/{@code applyReservations} call on
 * {@link com.morpheusdata.core.MorpheusHostProfileService}. The cluster overload returns
 * immediately with one {@link #processId} for the whole fan-out, with per-host sub-steps
 * recorded as {@code ProcessEvent}s under that same {@code Process}. The single-host
 * overload returns the same shape with a one-member {@link #hosts} list, so callers don't
 * need two result types.
 *
 * @since 1.6.0
 */
public class ResourceReservationApplyResult {

	/** UUID of the trackable Process entity a caller can poll/subscribe to for progress. */
	public String processId;

	/**
	 * Overall rollup across every host in {@link #hosts}. {@code PARTIAL} only applies to
	 * the cluster variant, when a sequential abort leaves remaining hosts {@code SKIPPED}.
	 */
	public String state; // PENDING | RUNNING | COMPLETE | FAILED | PARTIAL

	/**
	 * Per-host breakdown. One entry for the single-host overload; one per cluster host for
	 * the fan-out overload.
	 */
	public List<ResourceReservationHostApplyResult> hosts;
}
