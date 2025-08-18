package com.morpheusdata.core;

import com.morpheusdata.model.OperationEvent;
import com.morpheusdata.model.OperationEventIdentityProjection;
import io.reactivex.rxjava3.core.Observable;

public interface MorpheusOperationEventService extends MorpheusDataService<OperationEvent, OperationEventIdentityProjection>, MorpheusIdentityService<OperationEventIdentityProjection> {

	/**
	 * Get a list of OperationEvent projections based on a category name
	 * @param category category string unique filter category
	 * @return Observable stream of sync projection
	 */
	Observable<OperationEventIdentityProjection> listIdentityProjections(String category);

	/**
	 * Get a list of OperationEvent projections based on a category name
	 * @param category category
	 * @return Observable stream of sync projection
	 * @deprecated replaced by {{@link #listIdentityProjections(String)}}
	 */
	@Deprecated
	Observable<OperationEventIdentityProjection> listSyncProjections(String category);
}
