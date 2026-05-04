/*
 *  Copyright 2024 Morpheus Data, LLC.
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
import com.morpheusdata.model.projection.NetworkLocationIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Network Location, which defines geographic or logical locations associated with networks and cloud resources.
 * Network Locations can be used to organize networks by region, datacenter, or other logical groupings.
 *
 * @author Jordon Saardchit
 */
public class NetworkLocation extends NetworkLocationIdentityProjection {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account account;
	protected String refType;
	protected Long refId;
	protected String refUUID;
	protected String internalId;
	protected String uniqueId;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Network network;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected CloudPool cloudPool;
	protected List<CloudPool> assignedZonePools = new ArrayList<>();

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		markDirty("account", account);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}

	public String getRefUUID() {
		return refUUID;
	}

	public void setRefUUID(String refUUID) {
		this.refUUID = refUUID;
		markDirty("refUUID", refUUID);
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
		markDirty("network", network);
	}

	/**
	 * Gets the internal id of the NetworkLocation
	 * @return the internalId
	 */
	public String getInternalId() {
		return internalId;
	}

	/**
	 * Sets the internal id of the NetworkLocation
	 * @param internalId the internal id
	 */
	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", internalId);
	}

	/**
	 * Gets the unique id of the NetworkLocation
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the unique id of the NetworkLocation
	 * @param uniqueId the unique id
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		markDirty("uniqueId", uniqueId);
	}

	/**
	 * Gets the primary {@link CloudPool} (zone pool) associated with this NetworkLocation
	 * @return the cloudPool
	 */
	public CloudPool getCloudPool() {
		return cloudPool;
	}

	/**
	 * Sets the primary {@link CloudPool} (zone pool) associated with this NetworkLocation
	 * @param cloudPool the zone pool
	 */
	public void setCloudPool(CloudPool cloudPool) {
		this.cloudPool = cloudPool;
		markDirty("cloudPool", cloudPool);
	}

	/**
	 * Gets the list of {@link CloudPool} zone pools assigned to this NetworkLocation
	 * @return the assignedZonePools
	 */
	public List<CloudPool> getAssignedZonePools() {
		return assignedZonePools;
	}

	/**
	 * Sets the list of {@link CloudPool} zone pools assigned to this NetworkLocation
	 * @param assignedZonePools the list of assigned zone pools
	 */
	public void setAssignedZonePools(List<CloudPool> assignedZonePools) {
		this.assignedZonePools = assignedZonePools;
		markDirty("assignedZonePools", assignedZonePools);
	}
}

