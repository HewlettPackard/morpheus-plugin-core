package com.morpheusdata.model.projection;

public class OperationEventIdentityProjection extends MorpheusIdentityModel {
	protected String externalId;
	protected String name;

	/**
	 * The default constructor for creating a projection object.
	 * @param id the database id of the object
	 * @param externalId the API id of the object
	 * @param name the Name of the object as a secondary comparison
	 * @param config the configuration of the object
	 */
	public OperationEventIdentityProjection(Long id, String externalId, String name, String config) {
		this.id = id;
		this.externalId = externalId;
		this.name = name;
		this.config = config;
	}

	public OperationEventIdentityProjection() {

	}
}
