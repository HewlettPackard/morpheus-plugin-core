/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.StorageVolumeGroupSnapshot;
import com.morpheusdata.model.projection.StorageVolumeGroupSnapshotIdentityProjection;


/**
 * Synchronous context methods for {@link StorageVolumeGroupSnapshot} in Morpheus.
 * A group snapshot is a consistent point-in-time capture of every volume in a
 * {@link com.morpheusdata.model.StorageVolumeGroup}, taken as one operation. Provides the standard
 * CRUD/query methods via {@link MorpheusSynchronousDataService}.
 *
 * @since 1.5.0
 * @author HPE Storage Plugin Team
 */
public interface MorpheusSynchronousStorageVolumeGroupSnapshotService extends
		MorpheusSynchronousDataService<StorageVolumeGroupSnapshot, StorageVolumeGroupSnapshotIdentityProjection>,
		MorpheusSynchronousIdentityService<StorageVolumeGroupSnapshotIdentityProjection> {
}
