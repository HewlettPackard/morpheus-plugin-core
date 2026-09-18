/*
 *  Copyright 2026 HPE Development Company, L.P.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.StorageServerNodeDisk;
import com.morpheusdata.model.projection.StorageServerNodeDiskIdentityProjection;

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
}
