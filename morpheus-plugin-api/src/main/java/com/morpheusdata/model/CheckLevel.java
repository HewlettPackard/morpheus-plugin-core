package com.morpheusdata.model;

public class CheckLevel {
	protected String refType; //associated type of object this drift check is for
	protected String refId; //associated id of the object this drift check is for
	CheckLevelEnum checkLevel; // level of the drift check (e.g., all, update)
	ConfigDriftState state; // status of the drift check (e.g., pending, in-progress, failed, completed)
	protected Integer percentComplete = 0; // completion percentage

	protected String lastUpdatedOn; // last updated timestamp
	protected String startedOn; // start timestamp
	protected String completedOn; // completion timestamp

	enum CheckLevelEnum {
		ALL,
		UPDATE
	}
	enum ConfigDriftState {
		PENDING,
		IN_PROGRESS,
		FAILED,
		COMPLETED
	}

}
