package com.morpheusdata.model.projection;

public class StorageHostGroupIdentityProjection extends MorpheusIdentityModel {
	protected String externalId;
	protected String name;

	public StorageHostGroupIdentityProjection(Long id, String externalId, String name, String config) {
		this.externalId = externalId;
		this.name = name;
		this.id = id;
		this.config = config;
	}

	public StorageHostGroupIdentityProjection() {

	}
}
