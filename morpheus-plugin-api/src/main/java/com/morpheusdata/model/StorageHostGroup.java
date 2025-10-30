package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.StorageHostGroupIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Collections;
import java.util.List;

public class StorageHostGroup extends StorageHostGroupIdentityProjection {

	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	private Account owner;
	private StorageServer storageServer;
	private String name;
	private String code;
	private String category;
	private String visibility = "public"; //["public", "private"]
	private String description;
	private String internalId;
	private String externalId;
	private String config;
	private String status;
	private String statusMessage;
	private List<StorageHost> hosts = Collections.EMPTY_LIST;
	private List<StorageVolume> volumes = Collections.EMPTY_LIST;
	private List<Account> accounts = Collections.EMPTY_LIST;

	public StorageServer getStorageServer() {return storageServer;}

	public void setStorageServer(StorageServer storageServer) {	this.storageServer = storageServer;	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Override
	public String getConfig() {
		return config;
	}

	@Override
	public void setConfig(String config) {
		this.config = config;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<StorageHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<StorageHost> hosts) {
		this.hosts = hosts;
	}

	public List<StorageVolume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<StorageVolume> volumes) {
		this.volumes = volumes;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
