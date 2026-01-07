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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelCollectionAsIdsOnlySerializer;

import java.util.List;

/**
 * Representation of a Morpheus StorageAggregate database object within the Morpheus platform.
 * A StorageAggregate models the aggregation of a set of member StorageVolumes that are pooled
 * together and expose a set of volumes. This can model relationships like RAID, ZFS zpools,
 * LVM volume groups, etc.
 *
 * @author Mike Carlin
 * @since 1.3.0
 */
public class StorageAggregate extends MorpheusModel {

	/**
	 * Unique identifier (UUID) for this aggregate
	 */
	protected String uuid;

	/**
	 * Internal identifier used by the storage system
	 */
	protected String internalId;

	/**
	 * External identifier used by the storage system
	 */
	protected String externalId;

	/**
	 * The type of the reference object (e.g. ComputeServer)
	 */
	protected String refType;

	/**
	 * The ID of the reference object
	 */
	protected Long refId;

	/**
	 * The name of the storage aggregate
	 */
	protected String name;

	/**
	 * Description of the storage aggregate
	 */
	protected String description;

	/**
	 * The type of storage aggregate (e.g., RAID, ZFS, LVM)
	 */
	protected StorageAggregateType type;

	/**
	 * Volumes that are exposed/provided by this aggregate (one-to-many)
	 */
	@JsonSerialize(using = ModelCollectionAsIdsOnlySerializer.class)
	protected List<StorageVolume> volumes;

	/**
	 * Volumes that make up the storage of this aggregate (one-to-many)
	 */
	@JsonSerialize(using = ModelCollectionAsIdsOnlySerializer.class)
	protected List<StorageVolume> members;

	/**
	 * Gets the UUID for this storage aggregate
	 * @return uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the UUID for this storage aggregate
	 * @param uuid the unique identifier
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
		markDirty("uuid", uuid);
	}

	/**
	 * Gets the internal ID used by the storage system
	 * @return internalId
	 */
	public String getInternalId() {
		return internalId;
	}

	/**
	 * Sets the internal ID used by the storage system
	 * @param internalId the internal identifier
	 */
	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", internalId);
	}

	/**
	 * Gets the external ID used by the storage system
	 * @return externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * Sets the external ID used by the storage system
	 * @param externalId the external identifier
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	/**
	 * Gets the reference type
	 * @return refType
	 */
	public String getRefType() {
		return refType;
	}

	/**
	 * Sets the reference type
	 * @param refType the reference type
	 */
	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	/**
	 * Gets the reference ID
	 * @return refId
	 */
	public Long getRefId() {
		return refId;
	}

	/**
	 * Sets the reference ID
	 * @param refId the reference ID
	 */
	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}

	/**
	 * Gets the name of the storage aggregate
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the storage aggregate
	 * @param name the aggregate name
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Gets the description of the storage aggregate
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the storage aggregate
	 * @param description the aggregate description
	 */
	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	/**
	 * Gets the type of storage aggregate
	 * @return type (e.g., RAID, ZFS, LVM)
	 */
	public StorageAggregateType getType() {
		return type;
	}

	/**
	 * Sets the type of storage aggregate
	 * @param type the aggregate type
	 */
	public void setType(StorageAggregateType type) {
		this.type = type;
		markDirty("type", type);
	}

	/**
	 * Gets the list of volumes exposed/provided by this aggregate.
	 * <p>
	 * These are the volumes available for use that are backed by this aggregate's storage pool.
	 * @return list of exposed volumes
	 */
	public List<StorageVolume> getVolumes() {
		return volumes;
	}

	/**
	 * Sets the list of volumes exposed/provided by this aggregate
	 * @param volumes list of exposed volumes
	 */
	public void setVolumes(List<StorageVolume> volumes) {
		this.volumes = volumes;
		markDirty("volumes", volumes);
	}

	/**
	 * Gets the list of volumes that make up the storage of this aggregate.
	 * <p>
	 * These are the physical volumes/disks that are pooled together to form this aggregate's storage capacity.
	 * For example, in a RAID array, these would be the individual disks; in a ZFS zpool, these would be the vdevs.
	 * @return list of member volumes
	 */
	public List<StorageVolume> getMembers() {
		return members;
	}

	/**
	 * Sets the list of volumes that make up the storage of this aggregate
	 * @param members list of member volumes
	 */
	public void setMembers(List<StorageVolume> members) {
		this.members = members;
		markDirty("members", members);
	}
}
