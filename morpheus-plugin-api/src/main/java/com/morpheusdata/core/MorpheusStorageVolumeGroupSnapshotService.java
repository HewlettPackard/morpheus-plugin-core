/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core;

import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageVolumeGroup;
import com.morpheusdata.model.StorageVolumeGroupSnapshot;
import com.morpheusdata.model.projection.StorageVolumeGroupSnapshotIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

/**
 * Context methods for dealing with {@link StorageVolumeGroupSnapshot} in Morpheus.
 * A group snapshot is a consistent point-in-time capture of every volume in a
 * {@link StorageVolumeGroup}, taken as one operation.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusStorageVolumeGroupSnapshotService extends
		MorpheusDataService<StorageVolumeGroupSnapshot, StorageVolumeGroupSnapshotIdentityProjection>,
		MorpheusIdentityService<StorageVolumeGroupSnapshotIdentityProjection> {

	/**
	 * List identity projections for all snapshots belonging to a volume group.
	 *
	 * @param volumeGroupId ID of the {@link StorageVolumeGroup}
	 * @return Observable stream of identity projections
	 */
	Observable<StorageVolumeGroupSnapshotIdentityProjection> listIdentityProjections(Long volumeGroupId);

	/**
	 * List all snapshots belonging to a volume group.
	 *
	 * @param volumeGroupId ID of the {@link StorageVolumeGroup}
	 * @return Observable stream of snapshots
	 */
	Observable<StorageVolumeGroupSnapshot> listByVolumeGroup(Long volumeGroupId);

	// ============================================================================
	// Create Operation (with Provider Delegation)
	// ============================================================================

	/**
	 * Create a consistent snapshot of a volume group.
	 * <p>
	 * When the storage server's provider implements
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet}, this delegates to
	 * {@link com.morpheusdata.core.providers.StorageProviderVolumeGroupsFacet#createSnapshot} so the
	 * snapshot is taken on the array; the resulting record is then persisted. When no such provider is
	 * present the record is persisted directly.
	 *
	 * @param storageServer the storage server hosting the volume group
	 * @param volumeGroup the volume group to snapshot
	 * @param snapshot the snapshot to create (name, origin, applicationConsistent)
	 * @param opts additional options
	 * @return ServiceResponse with the created snapshot
	 */
	Single<ServiceResponse<StorageVolumeGroupSnapshot>> createSnapshot(StorageServer storageServer, StorageVolumeGroup volumeGroup, StorageVolumeGroupSnapshot snapshot, Map<String, Object> opts);
}
