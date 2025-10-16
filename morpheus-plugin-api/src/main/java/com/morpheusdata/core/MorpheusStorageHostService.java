package com.morpheusdata.core;

import com.morpheusdata.model.StorageHost;
import com.morpheusdata.model.projection.StorageHostIdentityProjection;

/**
 * Context methods for dealing with {@link StorageHost} in Morpheus
 */
public interface MorpheusStorageHostService extends MorpheusDataService<StorageHost, StorageHostIdentityProjection>, MorpheusIdentityService<StorageHostIdentityProjection>{
}
