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
 * A consistent point-in-time capture of every volume in a {@link StorageVolumeGroup}, taken as
 * one operation so the members stay mutually coherent. Distinct from {@link Snapshot}, which is
 * per-volume and cannot express consistency across a set.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 */
public class StorageVolumeGroupSnapshot extends MorpheusModel {

	/** How the snapshot was produced. {@code policy} snapshots are reaped by retention; {@code manual}
	 * and {@code system} snapshots never auto-expire and never count against a policy's retain count. */
	public enum Origin { policy, manual, system }

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected StorageVolumeGroup volumeGroup;

	protected String name;
	protected String externalId;
	protected Origin origin = Origin.manual;
	/** Whether the members were quiesced (application-consistent) rather than crash-consistent. */
	protected Boolean applicationConsistent = false;
	protected Date snapshotCreated;
	/** When the snapshot auto-expires; only meaningful for {@code policy} snapshots (null otherwise). */
	protected Date expiresDate;
	protected Long usedStorage;
	/** Number of volumes captured, retained for auditability. */
	protected Integer volumeCount;
	protected String status;
	/** Username of the operator who created a {@code manual} snapshot (null otherwise). */
	protected String createdByUsername;
	protected String uuid;

	public StorageVolumeGroup getVolumeGroup() { return volumeGroup; }
	public void setVolumeGroup(StorageVolumeGroup volumeGroup) {
		this.volumeGroup = volumeGroup; markDirty("volumeGroup", volumeGroup);
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; markDirty("name", name); }

	public String getExternalId() { return externalId; }
	public void setExternalId(String externalId) { this.externalId = externalId; markDirty("externalId", externalId); }

	public Origin getOrigin() { return origin; }
	public void setOrigin(Origin origin) { this.origin = origin; markDirty("origin", origin); }

	public Boolean getApplicationConsistent() { return applicationConsistent; }
	public void setApplicationConsistent(Boolean applicationConsistent) {
		this.applicationConsistent = applicationConsistent; markDirty("applicationConsistent", applicationConsistent);
	}

	public Date getSnapshotCreated() { return snapshotCreated; }
	public void setSnapshotCreated(Date snapshotCreated) { this.snapshotCreated = snapshotCreated; markDirty("snapshotCreated", snapshotCreated); }

	public Date getExpiresDate() { return expiresDate; }
	public void setExpiresDate(Date expiresDate) { this.expiresDate = expiresDate; markDirty("expiresDate", expiresDate); }

	public Long getUsedStorage() { return usedStorage; }
	public void setUsedStorage(Long usedStorage) { this.usedStorage = usedStorage; markDirty("usedStorage", usedStorage); }

	public Integer getVolumeCount() { return volumeCount; }
	public void setVolumeCount(Integer volumeCount) { this.volumeCount = volumeCount; markDirty("volumeCount", volumeCount); }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; markDirty("status", status); }

	public String getCreatedByUsername() { return createdByUsername; }
	public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; markDirty("createdByUsername", createdByUsername); }

	public String getUuid() { return uuid; }
	public void setUuid(String uuid) { this.uuid = uuid; markDirty("uuid", uuid); }
}
