package com.morpheusdata.model.projection;

public class SecurityGroupRuleDestinationIdentityProjection extends MorpheusIdentityModel {
	public SecurityGroupRuleDestinationIdentityProjection(){}

	protected String name;
	protected String externalId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
