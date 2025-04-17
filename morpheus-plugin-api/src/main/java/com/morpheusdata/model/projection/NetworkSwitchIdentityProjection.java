package com.morpheusdata.model.projection;

import com.morpheusdata.model.MorpheusModel;

public class NetworkSwitchIdentityProjection extends MorpheusModel {
	protected String externalId;

	public NetworkSwitchIdentityProjection() {}

	public NetworkSwitchIdentityProjection(String externalId) {
		this.externalId = externalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
