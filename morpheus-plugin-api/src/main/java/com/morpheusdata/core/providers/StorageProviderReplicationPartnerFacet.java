/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.providers;

import com.morpheusdata.model.StorageReplicationPartner;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.response.ServiceResponse;

import java.util.Map;

/**
 * Optional facet for {@link StorageProvider} implementations whose arrays support pairing
 * with a remote array as a replication partner. Paired with {@link StorageProvider} the same
 * way as {@link StorageProviderVolumeGroupsFacet}.
 *
 * <p>A provider that does not implement this facet contributes nothing to replication partner
 * management; partner records may still be populated read-only via
 * {@link StorageProvider#refreshStorageServer}.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 * @see StorageProvider
 * @see StorageProviderReplicationGroupFacet
 */
public interface StorageProviderReplicationPartnerFacet {

	/**
	 * Create a replication partner pairing with a remote storage system.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param partner       the partner to create
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the persisted partner on success
	 */
	ServiceResponse<StorageReplicationPartner> createReplicationPartner(
			StorageServer storageServer, StorageReplicationPartner partner, Map opts);

	/**
	 * Update the connection details of an existing replication partner.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param partner       the partner with updated fields
	 * @param opts          provider-specific options
	 * @return ServiceResponse containing the updated partner on success
	 */
	ServiceResponse<StorageReplicationPartner> updateReplicationPartner(
			StorageServer storageServer, StorageReplicationPartner partner, Map opts);

	/**
	 * Delete a replication partner pairing. Fails if any replication group still
	 * references this partner.
	 *
	 * @param storageServer the local {@link StorageServer}
	 * @param partner       the partner to delete
	 * @param opts          provider-specific options
	 * @return ServiceResponse indicating success or failure
	 */
	ServiceResponse<StorageReplicationPartner> deleteReplicationPartner(
			StorageServer storageServer, StorageReplicationPartner partner, Map opts);
}
