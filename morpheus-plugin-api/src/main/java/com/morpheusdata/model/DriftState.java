package com.morpheusdata.model;

import org.apache.groovy.util.Maps;

import java.util.List;
import java.util.Objects;

public class DriftState {
	protected String refType; //associated type of object this drift check is for
	protected String refId; //associated id of the object this drift check is for

	DriftStateStatus state; // status of the drift check (e.g., no-drift, drift-detected, error)
	protected String lastCheckedOn; // last checked timestamp
	protected String details; // details about the drift state, if any
	protected String resolvedOn; // timestamp when the drift was resolved, if applicable
	protected List<String> detectedChanges; // list of detected changes, if any
	enum DriftStateStatus {
		NO_DRIFT,
		DRIFT_DETECTED,
		ERROR
	}
	protected Maps configuration; // configuration details like iSCSI MTU subnet settings etc.

}
