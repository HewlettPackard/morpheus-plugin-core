/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.model.StorageServerNodeDisk;
import com.morpheusdata.model.projection.StorageServerNodeDiskIdentityProjection;

import java.util.List;

/**
 * Blocking counterpart to {@link com.morpheusdata.core.MorpheusStorageServerNodeDiskService} for use in
 * synchronous plugin code. Provides {@code bulkCreate}/{@code bulkSave}/{@code bulkRemove} and other
 * standard CRUD/query methods via {@link MorpheusSynchronousDataService}.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusSynchronousStorageServerNodeDiskService extends
		MorpheusSynchronousDataService<StorageServerNodeDisk, StorageServerNodeDiskIdentityProjection>,
		MorpheusSynchronousIdentityService<StorageServerNodeDiskIdentityProjection> {

	/**
	 * List identity projections for all disks belonging to a node.
	 *
	 * @param nodeId ID of the {@link StorageServerNode}
	 * @return a List of identity projections
	 */
	List<StorageServerNodeDiskIdentityProjection> listIdentityProjections(Long nodeId);

	/**
	 * List all disks claimed by a node.
	 * @param node the storage server node
	 * @return a List of disks
	 */
	List<StorageServerNodeDisk> listByNode(StorageServerNode node);

	/**
	 * Find a disk by its durable {@code uniqueId} within a node.
	 * @param nodeId the storage server node ID
	 * @param uniqueId the durable, per-transport derived disk identity
	 * @return the disk if found, otherwise null
	 */
	StorageServerNodeDisk findByUniqueId(Long nodeId, String uniqueId);
}
