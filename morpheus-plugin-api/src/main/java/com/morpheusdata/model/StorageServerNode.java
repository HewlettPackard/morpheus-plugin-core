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

/**
 * Represents a node participating in a storage system. For SDS this is an HVM
 * host running controller and/or storage containers. For a physical array
 * this could model a controller node.
 * <p>
 * This is a minimal projection intended to satisfy the {@link StorageServerNodeDisk}
 * parent relationship. The full node lifecycle (role assignment, join/evacuate,
 * reservation handling) is tracked separately and is expected to extend this model.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public class StorageServerNode extends MorpheusModel {

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected StorageServer storageServer;

	/** The HVM host this node runs on, when host-backed. */
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected ComputeServer computeServer;

	/** SDS-assigned node identifier. */
	protected String nodeId;

	/** symmetric | controller | storage */
	protected String role;

	/** online | degraded | offline | joining | evacuating */
	protected String status;
	protected String statusMessage;
	protected String config;

	public StorageServer getStorageServer() { return storageServer; }
	public void setStorageServer(StorageServer storageServer) {
		this.storageServer = storageServer;
		markDirty("storageServer", storageServer);
	}

	public ComputeServer getComputeServer() { return computeServer; }
	public void setComputeServer(ComputeServer computeServer) {
		this.computeServer = computeServer;
		markDirty("computeServer", computeServer);
	}

	public String getNodeId() { return nodeId; }
	public void setNodeId(String nodeId) { this.nodeId = nodeId; markDirty("nodeId", nodeId); }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; markDirty("role", role); }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; markDirty("status", status); }

	public String getStatusMessage() { return statusMessage; }
	public void setStatusMessage(String statusMessage) { this.statusMessage = statusMessage; markDirty("statusMessage", statusMessage); }

	public String getConfig() { return config; }
	public void setConfig(String config) { this.config = config; markDirty("config", config); }
}
