/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.model.projection.StorageServerNodeIdentityProjection;

import java.util.List;

/**
 * Blocking counterpart to {@link com.morpheusdata.core.MorpheusStorageServerNodeService} for use in
 * synchronous plugin code. Provides {@code bulkCreate}/{@code bulkSave}/{@code bulkRemove} and other
 * standard CRUD/query methods via {@link MorpheusSynchronousDataService}.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusSynchronousStorageServerNodeService extends
		MorpheusSynchronousDataService<StorageServerNode, StorageServerNodeIdentityProjection>,
		MorpheusSynchronousIdentityService<StorageServerNodeIdentityProjection> {

	/**
	 * List all nodes belonging to a storage server.
	 * @param storageServer the storage server
	 * @return a List of nodes
	 */
	List<StorageServerNode> listByStorageServer(StorageServer storageServer);

	/**
	 * Find a node by its SDS-assigned {@code nodeId} within a storage server.
	 * @param storageServerId the storage server ID
	 * @param nodeId the SDS-assigned node identifier
	 * @return the node if found, otherwise null
	 */
	StorageServerNode findByNodeId(Long storageServerId, String nodeId);
}
