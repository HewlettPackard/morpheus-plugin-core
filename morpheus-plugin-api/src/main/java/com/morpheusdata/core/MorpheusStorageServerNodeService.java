/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core;

import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.model.projection.StorageServerNodeIdentityProjection;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Context methods for dealing with {@link StorageServerNode} in Morpheus.
 * A node represents a member of a scale-out storage system — for SDS this is
 * an HVM host running controller and/or storage containers.
 * <p>
 * This is a minimal query/sync surface intended to unblock
 * {@link MorpheusStorageServerNodeDiskService}. Node lifecycle operations
 * (role assignment, join/evacuate, reservation handling) are tracked separately.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageServerNodeService extends
		MorpheusDataService<StorageServerNode, StorageServerNodeIdentityProjection>,
		MorpheusIdentityService<StorageServerNodeIdentityProjection> {

	/**
	 * List identity projections for all nodes belonging to a storage server.
	 *
	 * @param storageServerId ID of the {@link StorageServer}
	 * @return Observable stream of identity projections
	 */
	Observable<StorageServerNodeIdentityProjection> listIdentityProjections(Long storageServerId);

	/**
	 * List all nodes for a storage server.
	 * @param storageServer the storage server
	 * @return Observable stream of nodes
	 */
	Observable<StorageServerNode> listByStorageServer(StorageServer storageServer);

	/**
	 * Find a node by its SDS-assigned node identifier within a storage server.
	 * @param storageServerId the storage server ID
	 * @param nodeId the SDS-assigned node identifier
	 * @return Single containing the node if found
	 */
	Single<StorageServerNode> findByNodeId(Long storageServerId, String nodeId);
}
