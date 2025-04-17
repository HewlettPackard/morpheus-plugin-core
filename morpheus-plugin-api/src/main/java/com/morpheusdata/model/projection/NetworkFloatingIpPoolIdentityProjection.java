package com.morpheusdata.model.projection;

public class NetworkFloatingIpPoolIdentityProjection extends MorpheusIdentityModel {
	protected String externalId;

	public NetworkFloatingIpPoolIdentityProjection() {
		//
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
		markDirty("externalId",externalId);
	}
}
