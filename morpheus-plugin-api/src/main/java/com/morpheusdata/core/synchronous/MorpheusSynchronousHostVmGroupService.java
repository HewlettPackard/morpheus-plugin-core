package com.morpheusdata.core.synchronous;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.model.HostVmGroup;

/**
 * A synchronous data service for HostVmGroups (named Host Groups and VM Groups used by
 * combined VM-to-Host affinity rules)
 * @since 1.6.0
 */
public interface MorpheusSynchronousHostVmGroupService extends MorpheusSynchronousDataService<HostVmGroup, HostVmGroup> {

}
