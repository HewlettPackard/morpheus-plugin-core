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


package com.morpheusdata.core;

import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.model.projection.StorageServerNodeIdentityProjection;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

/**
 * Context methods for managing {@link StorageServerNode} entities in Morpheus.
 * <p>
 * A StorageServerNode records a host's membership in a scale-out storage system: its role
 * (persona), health, resource reservation, and the {@link com.morpheusdata.model.ComputeServer}
 * it maps to when Morpheus manages that host. This service provides:
 * <ul>
 *   <li>Standard CRUD via {@link MorpheusDataService}</li>
 *   <li>Scoped queries by storage server and compute server</li>
 *   <li>Sync operations for plugin-based data synchronization</li>
 * </ul>
 * <p>
 * Node records are owned by the storage provider plugin. A plugin creates them when the storage
 * system is installed or a host joins, and updates them on refresh. Morpheus stores and serves the
 * records; it does not infer persona or status itself.
 *
 * @author Chinmay Keskar
 * @since 1.5.1
 * @see StorageServerNode
 * @see StorageServer
 */
public interface MorpheusStorageServerNodeService extends MorpheusDataService<StorageServerNode, StorageServerNodeIdentityProjection>, MorpheusIdentityService<StorageServerNodeIdentityProjection> {

	// ============================================================================
	// Query Operations
	// ============================================================================

	/**
	 * Get a list of StorageServerNode projections based on StorageServer id.
	 * @param storageServerId the storage server to filter by
	 * @return Observable stream of identity projections
	 */
	Observable<StorageServerNodeIdentityProjection> listIdentityProjections(Long storageServerId);

	/**
	 * Get a list of StorageServerNode projections based on StorageServer.
	 * @param storageServer the storage server to filter by
	 * @return Observable stream of identity projections
	 */
	Observable<StorageServerNodeIdentityProjection> listIdentityProjections(StorageServer storageServer);

	/**
	 * List all nodes belonging to a storage server.
	 * @param storageServer the storage server to filter by
	 * @return Observable stream of nodes
	 */
	Observable<StorageServerNode> listByStorageServer(StorageServer storageServer);

	/**
	 * List all nodes belonging to a storage server by id.
	 * @param storageServerId the storage server id to filter by
	 * @return Observable stream of nodes
	 */
	Observable<StorageServerNode> listByStorageServerId(Long storageServerId);

	/**
	 * List all nodes linked to a compute server. A host normally participates in at most one
	 * storage system, but the model does not enforce that.
	 * @param computeServerId the compute server id to filter by
	 * @return Observable stream of nodes
	 */
	Observable<StorageServerNode> listByComputeServerId(Long computeServerId);

	/**
	 * Find a node by its storage-system-assigned identifier within a storage server.
	 * @param storageServerId the storage server id
	 * @param nodeId the node identifier assigned by the storage system
	 * @return Single of the matching node, or empty if not found
	 */
	Single<StorageServerNode> findByNodeId(Long storageServerId, String nodeId);

	/**
	 * Find a node by its external id within a storage server.
	 * @param storageServerId the storage server id
	 * @param externalId the external id to search for
	 * @return Single of the matching node, or empty if not found
	 */
	Single<StorageServerNode> findByExternalId(Long storageServerId, String externalId);

	// ============================================================================
	// Sync Operations (for Plugin Data Synchronization)
	// ============================================================================

	/**
	 * Create nodes in bulk during sync operations.
	 * @param nodes the nodes to create
	 * @return success indicator
	 */
	Single<Boolean> create(List<StorageServerNode> nodes);

	/**
	 * Save (update) nodes in bulk during sync operations.
	 * @param nodes the nodes to save
	 * @return success indicator
	 */
	Single<Boolean> save(List<StorageServerNode> nodes);

	/**
	 * Remove nodes in bulk during sync operations.
	 * @param nodes the nodes to remove
	 * @return success indicator
	 */
	Single<Boolean> remove(List<StorageServerNodeIdentityProjection> nodes);
}
