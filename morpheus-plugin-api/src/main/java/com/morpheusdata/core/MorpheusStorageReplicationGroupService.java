/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core;

import com.morpheusdata.model.StorageReplicationGroup;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.projection.StorageReplicationGroupIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with {@link StorageReplicationGroup} in Morpheus.
 * A replication group tracks the RPO, lag and failover state for a set of volumes
 * being replicated to a {@link com.morpheusdata.model.StorageReplicationPartner}.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageReplicationGroupService extends
		MorpheusDataService<StorageReplicationGroup, StorageReplicationGroupIdentityProjection>,
		MorpheusIdentityService<StorageReplicationGroupIdentityProjection> {

	/**
	 * List identity projections for all replication groups belonging to a storage server.
	 *
	 * @param storageServerId ID of the {@link com.morpheusdata.model.StorageServer}
	 * @return Observable stream of identity projections
	 */
	Observable<StorageReplicationGroupIdentityProjection> listIdentityProjections(Long storageServerId);

	/**
	 * List replication groups whose {@code lagSeconds} exceeds {@code targetRpoSeconds}.
	 * Used by the RPO breach detection scheduler.
	 *
	 * @param storageServerId ID of the {@link com.morpheusdata.model.StorageServer}
	 * @return Observable stream of breaching groups
	 */
	Observable<StorageReplicationGroup> listRpoBreaches(Long storageServerId);

	// ============================================================================
	// CRUD Operations (with Provider Delegation)
	// ============================================================================

	/**
	 * Create a new replication group on the storage server.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplication#createReplicationGroup}
	 * to create the relationship on the actual storage array.
	 *
	 * @param storageServer the storage server to create the group on
	 * @param group the replication group to create
	 * @param opts additional options
	 * @return ServiceResponse with the created replication group
	 */
	Single<ServiceResponse<StorageReplicationGroup>> createReplicationGroup(StorageServer storageServer, StorageReplicationGroup group, Map<String, Object> opts);

	/**
	 * Update an existing replication group.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplication#updateReplicationGroup}
	 * before persisting the change.
	 *
	 * @param group the replication group to update
	 * @param opts additional options
	 * @return ServiceResponse with the updated replication group
	 */
	Single<ServiceResponse<StorageReplicationGroup>> updateReplicationGroup(StorageReplicationGroup group, Map<String, Object> opts);

	/**
	 * Delete a replication group.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplication#deleteReplicationGroup}
	 * before removing the domain record.
	 *
	 * @param group the replication group to delete
	 * @param opts additional options (e.g. "deleteRemoteCopy")
	 * @return ServiceResponse indicating success/failure
	 */
	Single<ServiceResponse> deleteReplicationGroup(StorageReplicationGroup group, Map<String, Object> opts);
}
