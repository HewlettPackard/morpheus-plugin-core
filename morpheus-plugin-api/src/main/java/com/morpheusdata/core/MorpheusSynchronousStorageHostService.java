package com.morpheusdata.core;

import com.morpheusdata.model.StorageHost;
import com.morpheusdata.model.projection.StorageHostIdentityProjection;

public interface MorpheusSynchronousStorageHostService extends MorpheusSynchronousDataService<StorageHost, StorageHostIdentityProjection>, MorpheusSynchronousIdentityService<StorageHostIdentityProjection> {

}
