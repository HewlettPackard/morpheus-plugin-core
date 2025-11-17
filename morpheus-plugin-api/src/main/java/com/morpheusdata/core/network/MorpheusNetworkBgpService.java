package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.NetworkRouterBgpNeighbor;
import com.morpheusdata.model.projection.NetworkRouterBgpNeighborIdentityProjection;

/**
 * Provides Morpheus services related to querying, saving, and removing {@link NetworkRouterBgpNeighbor}.
 * @since 1.13.0
 */
public interface MorpheusNetworkBgpService extends MorpheusDataService<NetworkRouterBgpNeighbor, NetworkRouterBgpNeighborIdentityProjection>, MorpheusIdentityService<NetworkRouterBgpNeighborIdentityProjection> {
}
