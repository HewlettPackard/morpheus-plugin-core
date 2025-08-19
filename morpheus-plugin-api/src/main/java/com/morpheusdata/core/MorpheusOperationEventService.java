package com.morpheusdata.core;

import com.morpheusdata.model.OperationEvent;
import com.morpheusdata.model.OperationEventIdentityProjection;
import io.reactivex.rxjava3.core.Observable;

public interface MorpheusOperationEventService extends MorpheusDataService<OperationEvent, OperationEventIdentityProjection>, MorpheusIdentityService<OperationEventIdentityProjection> {

}
