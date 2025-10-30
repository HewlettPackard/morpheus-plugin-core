package com.morpheusdata.core;

import com.morpheusdata.model.StorageHost;
import com.morpheusdata.model.StorageHostGroup;
import com.morpheusdata.model.projection.StorageHostGroupIdentityProjection;
import com.morpheusdata.model.projection.StorageHostIdentityProjection;

public interface MorpheusStorageHostGroupService extends MorpheusDataService<StorageHostGroup, StorageHostGroupIdentityProjection>, MorpheusIdentityService<StorageHostGroupIdentityProjection>{
}
