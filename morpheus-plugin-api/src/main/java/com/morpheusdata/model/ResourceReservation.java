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
 * Reports the resource reservation currently held (or that would be held, for a dry run) by one
 * component on one host, as returned by {@link com.morpheusdata.core.MorpheusHostProfileService}.
 *
 * @since 1.6.0
 */
public class ResourceReservation {

	/** The {@link ComputeServer} this reservation applies to. */
	public Long serverId;

	/** The component holding this reservation (e.g. "sds"). */
	public String reservedBy;

	/** Display name shown beside the reservation in the UI. */
	public String reservedByName;

	/** Whole cores withheld from customer workloads on this host. */
	public Integer reservedCores;

	/** Total memory withheld on this host, in megabytes. */
	public Long reservedMemoryMb;

	/** Amount of {@link #reservedMemoryMb} set aside as hugepages. */
	public Long hugePagesMb;

	/** Hugepage size, e.g. {@code "2M"} or {@code "1G"}. */
	public String hugePageSize;

	/** CPU isolation mode: {@code none}, {@code dedicated}, or {@code tuned}. */
	public String cpuIsolation;

	/** Impact of reaching this state: {@code none}, {@code service-restart}, or {@code reboot-required}. */
	public String impact;

	/** {@code false} until a required reboot has happened and the reservation has taken effect. */
	public Boolean active;

	/** The {@code HostProfileVersion} written as a result of this reservation; {@code null} for a dry run. */
	public Integer profileVersion;
}
