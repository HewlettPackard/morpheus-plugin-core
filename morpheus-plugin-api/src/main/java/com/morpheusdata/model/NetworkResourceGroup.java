package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.NetworkResourceGroupIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;
import java.util.List;

public class NetworkResourceGroup extends NetworkResourceGroupIdentityProjection {

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account account;

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account owner;


	protected String description;
	protected String category;

	protected String rawData;
	protected String visibility = "private";

	protected Date dateCreated;
	protected Date lastUpdated;

	protected String externalType;

	protected List<MetadataTag> metadata;
	protected List<NetworkResourceGroupMember> members;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
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

	public String getExternalType() {
		return externalType;
	}

	public void setExternalType(String externalType) {
		this.externalType = externalType;
	}

	public List<MetadataTag> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<MetadataTag> metadata) {
		this.metadata = metadata;
	}

	public List<NetworkResourceGroupMember> getMembers() {
		return members;
	}

	public void setMembers(List<NetworkResourceGroupMember> members) {
		this.members = members;
	}
}
