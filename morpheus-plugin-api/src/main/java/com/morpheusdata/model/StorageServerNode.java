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
import com.morpheusdata.model.projection.StorageServerNodeIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;

/**
 * Represents a host participating in a scale-out storage system.
 * <p>
 * A {@link StorageServer} models a storage system as a single endpoint. Scale-out and
 * software-defined storage systems are built from member hosts, each carrying one or more service
 * roles. A StorageServerNode records that membership: which host it is, which roles it carries
 * (its {@link StorageServerNodePersona persona}), its health, and the compute and memory the
 * storage system has reserved on it.
 * <p>
 * Key constraints:
 * <ul>
 *   <li>{@code nodeId} is required and unique within a storage server</li>
 *   <li>{@code computeServer} is optional; it is set when the node is a Morpheus-managed host</li>
 *   <li>Records are written by the storage provider plugin (at install, expansion, and refresh),
 *       never inferred by Morpheus</li>
 * </ul>
 *
 * @author Chinmay Keskar
 * @since 1.5.1
 * @see StorageServer
 * @see ComputeServer
 * @see StorageServerNodePersona
 * @see StorageServerNodeStatus
 */
public class StorageServerNode extends StorageServerNodeIdentityProjection {

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected StorageServer storageServer;

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected ComputeServer computeServer;

	/**
	 * The role this node plays in the storage system.
	 */
	protected StorageServerNodePersona persona;

	/**
	 * The operational state of this node as reported by the storage system.
	 */
	protected StorageServerNodeStatus status;

	protected String statusMessage;
	protected Date statusDate;

	/**
	 * Number of controller role instances running on this node.
	 */
	protected Integer controllerCount = 0;

	/**
	 * Number of storage role instances running on this node.
	 */
	protected Integer storageCount = 0;

	/**
	 * CPU cores reserved on the host for the storage system.
	 */
	protected Integer reservedCpuCores;

	/**
	 * Memory in megabytes reserved on the host for the storage system.
	 */
	protected Long reservedMemoryMb;

	protected String rawData;
	protected Boolean enabled = true;
	protected Date dateCreated;
	protected Date lastUpdated;

	// Constructors

	public StorageServerNode() {
		// default constructor
	}

	// Getters and Setters

	public StorageServer getStorageServer() {
		return storageServer;
	}

	public void setStorageServer(StorageServer storageServer) {
		this.storageServer = storageServer;
		markDirty("storageServer", storageServer);
	}

	public ComputeServer getComputeServer() {
		return computeServer;
	}

	public void setComputeServer(ComputeServer computeServer) {
		this.computeServer = computeServer;
		markDirty("computeServer", computeServer);
	}

	public StorageServerNodePersona getPersona() {
		return persona;
	}

	public void setPersona(StorageServerNodePersona persona) {
		this.persona = persona;
		markDirty("persona", persona);
	}

	public StorageServerNodeStatus getStatus() {
		return status;
	}

	public void setStatus(StorageServerNodeStatus status) {
		this.status = status;
		markDirty("status", status);
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		markDirty("statusMessage", statusMessage);
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
		markDirty("statusDate", statusDate);
	}

	public Integer getControllerCount() {
		return controllerCount;
	}

	public void setControllerCount(Integer controllerCount) {
		this.controllerCount = controllerCount;
		markDirty("controllerCount", controllerCount);
	}

	public Integer getStorageCount() {
		return storageCount;
	}

	public void setStorageCount(Integer storageCount) {
		this.storageCount = storageCount;
		markDirty("storageCount", storageCount);
	}

	public Integer getReservedCpuCores() {
		return reservedCpuCores;
	}

	public void setReservedCpuCores(Integer reservedCpuCores) {
		this.reservedCpuCores = reservedCpuCores;
		markDirty("reservedCpuCores", reservedCpuCores);
	}

	public Long getReservedMemoryMb() {
		return reservedMemoryMb;
	}

	public void setReservedMemoryMb(Long reservedMemoryMb) {
		this.reservedMemoryMb = reservedMemoryMb;
		markDirty("reservedMemoryMb", reservedMemoryMb);
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
		markDirty("rawData", rawData);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
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
