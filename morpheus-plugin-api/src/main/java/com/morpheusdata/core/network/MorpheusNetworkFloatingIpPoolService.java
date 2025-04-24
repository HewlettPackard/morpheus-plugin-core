package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.NetworkFloatingIpPool;
import com.morpheusdata.model.projection.NetworkFloatingIpPoolIdentityProjection;

public interface MorpheusNetworkFloatingIpPoolService extends MorpheusDataService<NetworkFloatingIpPool, NetworkFloatingIpPoolIdentityProjection>, MorpheusIdentityService<NetworkFloatingIpPoolIdentityProjection> {
}
