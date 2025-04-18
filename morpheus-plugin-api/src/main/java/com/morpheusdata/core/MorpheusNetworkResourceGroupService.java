package com.morpheusdata.core;

import com.morpheusdata.model.NetworkResourceGroup;
import com.morpheusdata.model.projection.NetworkResourceGroupIdentityProjection;

/**
 * Context methods for managing network resource groups
 * @author aclement
 * @since 1.2.5
 */
public interface MorpheusNetworkResourceGroupService extends MorpheusDataService<NetworkResourceGroup, NetworkResourceGroupIdentityProjection> {

	MorpheusNetworkResourceGroupMemberService getMember();

}
