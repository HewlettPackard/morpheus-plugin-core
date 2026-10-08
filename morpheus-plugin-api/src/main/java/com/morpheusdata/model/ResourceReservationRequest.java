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
 * Request to establish, revise, or release a host-level resource reservation via
 * {@link com.morpheusdata.core.MorpheusHostProfileService}.
 * <p>
 * A reservation withholds CPU cores, memory, and hugepages on a host at the operating
 * system/containerd layer so customer workloads cannot encroach on what the requesting
 * component needs to run reliably. Calling {@code reserveResources} again with a different
 * amount for the same {@link #reservedBy} revises that reservation in place; reservations
 * belonging to any other component are untouched.
 *
 * @since 1.6.0
 */
public class ResourceReservationRequest {

	/** Identifies the requesting component (e.g. "sds"). A component can only change its own reservation. */
	public String reservedBy;

	/** Display name shown beside the reservation in the UI (e.g. "Alletra MP Storage"). */
	public String reservedByName;

	/** Whole cores withheld from customer workloads. */
	public Integer reservedCores;

	/** Total memory withheld, in megabytes. */
	public Long reservedMemoryMb;

	/** Amount of {@link #reservedMemoryMb} set aside as hugepages; drawn from the total, not added to it. */
	public Long hugePagesMb;

	/** Hugepage size, e.g. {@code "2M"} or {@code "1G"}, as applied to {@code system.memory.hugePages.size}. */
	public String hugePageSize;

	/** CPU isolation mode: {@code none}, {@code dedicated}, or {@code tuned}. */
	public String cpuIsolation;

	/** When {@code true}, reports the impact of applying this request without writing anything. */
	public Boolean dryRun;
}
