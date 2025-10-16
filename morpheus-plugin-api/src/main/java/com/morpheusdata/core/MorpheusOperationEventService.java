package com.morpheusdata.core;

import com.morpheusdata.model.OperationEvent;
import com.morpheusdata.model.projection.OperationEventIdentityProjection;

public interface MorpheusOperationEventService extends MorpheusDataService<OperationEvent, OperationEventIdentityProjection>, MorpheusIdentityService<OperationEventIdentityProjection> {

}
