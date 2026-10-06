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


package com.morpheusdata.model.projection;

/**
 * Provides a subset of properties from the {@link com.morpheusdata.model.StorageServerNode} object
 * for doing a sync match comparison with less bandwidth usage and memory footprint. This is a DTO
 * Projection object.
 * <p>
 * A StorageServerNode represents a host participating in a scale-out storage system. The
 * {@code nodeId} is the identifier the storage system assigns to the node and is the primary match
 * key during sync; {@code externalId} and {@code name} are available as secondary keys.
 *
 * @author Chinmay Keskar
 * @since 1.5.1
 * @see com.morpheusdata.model.StorageServerNode
 */
public class StorageServerNodeIdentityProjection extends MorpheusIdentityModel {

	protected String nodeId;
	protected String externalId;
	protected String name;
	protected String uuid;

	public StorageServerNodeIdentityProjection() {
		// default constructor
	}

	public StorageServerNodeIdentityProjection(Long id, String nodeId, String externalId, String name) {
		this.id = id;
		this.nodeId = nodeId;
		this.externalId = externalId;
		this.name = name;
	}

	/**
	 * Returns the identifier assigned to this node by the storage system. Unique within a storage server.
	 * @return the storage-system-assigned node identifier
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * Sets the identifier assigned to this node by the storage system.
	 * @param nodeId the storage-system-assigned node identifier
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
		markDirty("nodeId", nodeId);
	}

	/**
	 * Returns the externalId, also known as the API id of the equivalent object on the storage system.
	 * @return the external id or API id of the current record
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * Sets the externalId of the storage server node.
	 * @param externalId the external id or API id of the current record
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId", externalId);
	}

	/**
	 * Returns the display name of the node, typically the host name. Available as a fallback match key.
	 * @return the name of the node
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name of the node.
	 * @param name the name of the node
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Returns the uuid of the storage server node.
	 * @return the uuid of the current record
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid of the storage server node.
	 * @param uuid the uuid of the current record
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
		markDirty("uuid", uuid);
	}
}
