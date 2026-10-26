/*
 *  Copyright 2026 HPE Development Company, L.P.
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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;
import java.util.Date;

/**
 * Schedule and retention rules that produce automatic snapshots for a {@link StorageVolumeGroup}.
 * A group has at most one policy (1:1). Only snapshots with an origin of
 * {@link StorageVolumeGroupSnapshot.Origin#policy} are subject to retention; manual and system
 * snapshots never auto-expire.
 *
 * <p>Execution is hybrid: by default the Morpheus core scheduler triggers snapshots at
 * {@link #nextRunDate}, but a provider MAY run the schedule on a native array scheduler by honoring
 * {@link #useNativeScheduler}, in which case Morpheus syncs state rather than triggering.
 *
 * @since 1.6.0
 * @author HPE Storage Plugin Team
 */
public class StorageSnapshotPolicy extends MorpheusModel {

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected StorageVolumeGroup volumeGroup;

	/** Provider identifier of the array-native schedule when {@link #useNativeScheduler} is true; null when
	 * Morpheus drives the schedule. */
	protected String externalId;
	/** Whether the policy is active. A disabled policy keeps its settings but produces no snapshots. */
	protected Boolean enabled = true;
	/** Snapshot frequency in seconds. Required; minimum 60. */
	protected Long intervalSeconds;
	/** Maximum number of {@code policy}-origin snapshots to keep; the oldest expires beyond this.
	 * Required and at least 1 when {@link #enabled} is true. */
	protected Integer retainCount;
	/** Whether scheduled snapshots quiesce members (application-consistent) rather than crash-consistent. */
	protected Boolean applicationConsistent = false;
	/** When true the provider's array-native scheduler runs the schedule and Morpheus syncs state
	 * instead of triggering at {@link #nextRunDate}. */
	protected Boolean useNativeScheduler = false;
	/** Next scheduled execution; computed by the core scheduler (or reported by the array in native mode). */
	protected Date nextRunDate;
	/** Last execution time. */
	protected Date lastRunDate;
	protected Date dateCreated;
	protected Date lastUpdated;

	public StorageVolumeGroup getVolumeGroup() {
		return volumeGroup;
	}

	public void setVolumeGroup(StorageVolumeGroup volumeGroup) {
		this.volumeGroup = volumeGroup;
		markDirty("volumeGroup", volumeGroup);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Long getIntervalSeconds() {
		return intervalSeconds;
	}

	public void setIntervalSeconds(Long intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
		markDirty("intervalSeconds", intervalSeconds);
	}

	public Integer getRetainCount() {
		return retainCount;
	}

	public void setRetainCount(Integer retainCount) {
		this.retainCount = retainCount;
		markDirty("retainCount", retainCount);
	}

	public Boolean getApplicationConsistent() {
		return applicationConsistent;
	}

	public void setApplicationConsistent(Boolean applicationConsistent) {
		this.applicationConsistent = applicationConsistent;
		markDirty("applicationConsistent", applicationConsistent);
	}

	public Boolean getUseNativeScheduler() {
		return useNativeScheduler;
	}

	public void setUseNativeScheduler(Boolean useNativeScheduler) {
		this.useNativeScheduler = useNativeScheduler;
		markDirty("useNativeScheduler", useNativeScheduler);
	}

	public Date getNextRunDate() {
		return nextRunDate;
	}

	public void setNextRunDate(Date nextRunDate) {
		this.nextRunDate = nextRunDate;
		markDirty("nextRunDate", nextRunDate);
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
		markDirty("lastRunDate", lastRunDate);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		markDirty("lastUpdated", lastUpdated);
	}
}
