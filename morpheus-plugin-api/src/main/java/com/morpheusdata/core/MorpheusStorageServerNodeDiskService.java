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
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with {@link StorageServerNodeDisk} in Morpheus.
 * A node disk is a physical drive already claimed by a {@link StorageServerNode}.
 * <p>
 * Most of these records are written by plugin refresh (e.g. reporting a failed drive
 * or a resynchronization state change), but a tenant with sufficient permission may
 * also manage them directly through the REST API's CRUD operations below.
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

	// ============================================================================
	// CRUD Operations
	// ============================================================================

	/**
	 * Create a new disk record claimed by the given node.
	 *
	 * @param node the {@link StorageServerNode} the disk belongs to
	 * @param disk the disk to create
	 * @param opts additional options
	 * @return ServiceResponse with the created disk
	 */
	Single<ServiceResponse<StorageServerNodeDisk>> createNodeDisk(StorageServerNode node, StorageServerNodeDisk disk, Map<String, Object> opts);

	/**
	 * Update an existing node disk.
	 * <p>
	 * This is a partial update: only fields the caller actually intends to change should be
	 * bound onto {@code disk} before calling. Since a plain model object can't distinguish
	 * "not sent" from "explicitly null," pass {@code opts.excludeFields} (a
	 * {@code List<String>} of property names) for any updatable field the caller did not
	 * submit, so the implementation can skip binding them and avoid clobbering existing values.
	 *
	 * @param disk the disk to update
	 * @param opts additional options; supports {@code excludeFields} (List&lt;String&gt;)
	 * @return ServiceResponse with the updated disk
	 */
	Single<ServiceResponse<StorageServerNodeDisk>> updateNodeDisk(StorageServerNodeDisk disk, Map<String, Object> opts);

	/**
	 * Delete a node disk.
	 *
	 * @param disk the disk to delete
	 * @param opts additional options
	 * @return ServiceResponse indicating success/failure
	 */
	Single<ServiceResponse> deleteNodeDisk(StorageServerNodeDisk disk, Map<String, Object> opts);
}
