/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core;

import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.model.StorageServerNodeDisk;
import com.morpheusdata.model.projection.StorageServerNodeDiskIdentityProjection;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Context methods for dealing with {@link StorageServerNodeDisk} in Morpheus.
 * A node disk is a physical drive already claimed by a {@link StorageServerNode}.
 * <p>
 * These records are written by plugin refresh (e.g. reporting a failed drive or
 * a resynchronization state change), not created directly by a tenant through
 * a REST API — there is no user-facing CRUD surface for this model.
 *
 * @since 1.5.1
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageServerNodeDiskService extends
		MorpheusDataService<StorageServerNodeDisk, StorageServerNodeDiskIdentityProjection>,
		MorpheusIdentityService<StorageServerNodeDiskIdentityProjection> {

	/**
	 * List identity projections for all disks belonging to a node.
	 *
	 * @param nodeId ID of the {@link StorageServerNode}
	 * @return Observable stream of identity projections
	 */
	Observable<StorageServerNodeDiskIdentityProjection> listIdentityProjections(Long nodeId);

	/**
	 * List all disks claimed by a node.
	 * @param node the storage server node
	 * @return Observable stream of disks
	 */
	Observable<StorageServerNodeDisk> listByNode(StorageServerNode node);

	/**
	 * Find a disk by its durable {@code uniqueId} within a node.
	 * @param nodeId the storage server node ID
	 * @param uniqueId the durable, per-transport derived disk identity
	 * @return Single containing the disk if found
	 */
	Single<StorageServerNodeDisk> findByUniqueId(Long nodeId, String uniqueId);
}
