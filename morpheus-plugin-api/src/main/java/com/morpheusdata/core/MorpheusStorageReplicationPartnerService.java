/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core;

import com.morpheusdata.model.StorageReplicationPartner;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.projection.StorageReplicationPartnerIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with {@link StorageReplicationPartner} in Morpheus.
 * A replication partner represents a remote storage system that a local array
 * replicates data to.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageReplicationPartnerService extends
		MorpheusDataService<StorageReplicationPartner, StorageReplicationPartnerIdentityProjection>,
		MorpheusIdentityService<StorageReplicationPartnerIdentityProjection> {

	/**
	 * List identity projections for all replication partners belonging to a storage server.
	 *
	 * @param storageServerId ID of the local {@link com.morpheusdata.model.StorageServer}
	 * @return Observable stream of identity projections
	 */
	Observable<StorageReplicationPartnerIdentityProjection> listIdentityProjections(Long storageServerId);

	// ============================================================================
	// CRUD Operations (with Provider Delegation)
	// ============================================================================

	/**
	 * Create a new replication partner on the storage server.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplicationPartnerFacet#createReplicationPartner}
	 * to create the pairing on the actual storage array.
	 *
	 * @param storageServer the storage server to create the partner on
	 * @param partner the replication partner to create
	 * @param opts additional options
	 * @return ServiceResponse with the created replication partner
	 */
	Single<ServiceResponse<StorageReplicationPartner>> createReplicationPartner(StorageServer storageServer, StorageReplicationPartner partner, Map<String, Object> opts);

	/**
	 * Update an existing replication partner.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplicationPartnerFacet#updateReplicationPartner}
	 * before persisting the change.
	 *
	 * @param partner the replication partner to update
	 * @param opts additional options
	 * @return ServiceResponse with the updated replication partner
	 */
	Single<ServiceResponse<StorageReplicationPartner>> updateReplicationPartner(StorageReplicationPartner partner, Map<String, Object> opts);

	/**
	 * Delete a replication partner.
	 * <p>
	 * This operation delegates to the storage provider plugin via
	 * {@link com.morpheusdata.core.providers.StorageProviderReplicationPartnerFacet#deleteReplicationPartner}
	 * before removing the domain record.
	 *
	 * @param partner the replication partner to delete
	 * @param opts additional options
	 * @return ServiceResponse indicating success/failure
	 */
	Single<ServiceResponse> deleteReplicationPartner(StorageReplicationPartner partner, Map<String, Object> opts);
}
