package com.morpheusdata.model;


import com.morpheusdata.model.projection.UpdateIdentityProjection;

public class UpdateDefinition extends UpdateIdentityProjection {
	protected String code; //unique code if needed
	protected String version; //version of the update
	protected String name; //name of the update
	protected String catalogVersion; //version of the catalog this update came from

	protected String refType; //associated type of object this update is for
	protected String refId; //associated id of the object this update is for

	protected String updateFile; //file location of the update
	protected String updateFileHash; //hash of the update file for integrity check
	protected String updateFileHashType; //hash type of the update file for integrity check
	protected Long updateFileSize; //size of the update file for integrity check
	protected String updateReleaseDate; // release date of the update
	protected String UpdateReleaseNotesUrl; //url to the release notes of the update

	protected Boolean zeroDowntime = false; //can this be applied with zero downtime
	protected Boolean requiresReboot = false; //does this require a reboot

	protected String severity; //normal, important, critical TODO: ENUM?
	protected String type; //security, feature, bugfix, enhancement TODO: ENUM?
	protected String description; //description of the update (CHANGELOG?)
	protected String descriptionRendered; //markdown rendered description

	protected Boolean requiresMaintenanceMode = false; //does this require maintenance mode to be enabled
	protected Boolean requiresRestart = false; //does this require a service restart

	protected Boolean supportsRollback = false; //can this update be rolled back

	protected Boolean isPlugin=false; //is this update code located in a plugin

	protected Object hasMany;

}
