package com.morpheusdata.core;

import com.morpheusdata.model.NetworkResourceGroup;
import com.morpheusdata.model.NetworkResourceGroupMember;
import com.morpheusdata.model.projection.NetworkResourceGroupIdentityProjection;
import com.morpheusdata.model.projection.NetworkResourceGroupMemberIdentityProjection;

/**
 * Context methods for managing network resource groups
 * @author aclement
 * @since 1.2.5
 */
public interface MorpheusNetworkResourceGroupMemberService extends MorpheusDataService<NetworkResourceGroupMember, NetworkResourceGroupMemberIdentityProjection> {

}
