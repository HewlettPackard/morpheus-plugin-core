package com.morpheusdata.model;

import com.morpheusdata.model.projection.UpdateIdentityProjection;

public class UpdateOperation extends UpdateIdentityProjection {
	protected String code; //unique code if needed
	protected String version; //version of the update
	protected String name; // update name
	protected String catalogVersion; //version of the catalog this update came from

	protected String refType; //associated type of object this update is for
	protected String refId; //associated id of the object this update is for

	protected String state; // status of the update operation (e.g., pending, in-progress, failed, completed)
	protected String statesMessage; //status message of the operation
	protected Integer percentComplete = 0; // completion percentage
	protected String precheckValidUntil = null; // timestamp until which precheck is valid

	protected String lastUpdatedOn; // last updated timestamp
	protected String startedOn; // start timestamp
	protected String completedOn; // completion timestamp
}
