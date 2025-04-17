package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.NetworkResourceGroupMemberIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;
import java.util.List;

public class NetworkResourceGroupMember extends NetworkResourceGroupMemberIdentityProjection {

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account account;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account owner;

	protected String category;
	protected String type;
	protected String memberType;// VirtualMachine, IP Addresses, Condition, Expression, Operator, etc.
	protected String memberValue; // for members that don't require an association, e.g: IP Address, Conjunction Operator
	protected String memberExpression; // flexible config for custom inputs
	protected Integer displayOrder;

	protected String internalId;

	protected String rawData;
	protected Date dateCreated;
	protected Date lastUpdated;

	protected List<NetworkRouterInterface> routerInterfaces;
	protected List<ComputeServer> servers;
	protected List<Network> networks;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberValue() {
		return memberValue;
	}

	public void setMemberValue(String memberValue) {
		this.memberValue = memberValue;
	}

	public String getMemberExpression() {
		return memberExpression;
	}

	public void setMemberExpression(String memberExpression) {
		this.memberExpression = memberExpression;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public List<NetworkRouterInterface> getRouterInterfaces() {
		return routerInterfaces;
	}

	public void setRouterInterfaces(List<NetworkRouterInterface> routerInterfaces) {
		this.routerInterfaces = routerInterfaces;
	}

	public List<ComputeServer> getServers() {
		return servers;
	}

	public void setServers(List<ComputeServer> servers) {
		this.servers = servers;
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}
}
