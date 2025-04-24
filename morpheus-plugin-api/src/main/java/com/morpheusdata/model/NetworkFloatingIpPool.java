package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.NetworkFloatingIpPoolIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class NetworkFloatingIpPool extends NetworkFloatingIpPoolIdentityProjection {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account account;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected Account owner;
	protected NetworkFloatingIpPoolType poolType;
	protected String category;
	protected String code;
	protected String name;
	protected String displayName;
	protected List<String> dnsServers;
	protected List<String> dnsSuffixList;
	protected String cidr;
	protected String gateway;
	protected String netmask;
	protected Integer ipCount = 0;
	protected Integer ipFreeCount = 0;
	protected String internalId;
	protected String refType;
	protected String refId;
	protected Boolean enabled;
	protected Date dateCreated;
	protected Date lastUpdated;

	protected List<NetworkFloatingIpPoolRange> ipRanges;

	public List<NetworkFloatingIpPoolRange> getIpRanges() {
		return ipRanges;
	}

	public void setIpRanges(List<NetworkFloatingIpPoolRange> ipRanges) {
		this.ipRanges = ipRanges;
		markDirty("ipRanges", ipRanges);
	}

	public void addToIpRanges(NetworkFloatingIpPoolRange ipRange) {
		if(this.ipRanges == null) {
			this.ipRanges = new ArrayList<>();
		}
		this.ipRanges.add(ipRange);
		markDirty("ipRanges", ipRanges);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
		markDirty("account", account);
	}

	public Account getOwner() {
		return owner;
	}

	public void setOwner(Account owner) {
		this.owner = owner;
		markDirty("owner", owner);
	}

	public NetworkFloatingIpPoolType getPoolType() {
		return poolType;
	}

	public void setPoolType(NetworkFloatingIpPoolType poolType) {
		this.poolType = poolType;
		markDirty("poolType", poolType);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", category);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		markDirty("displayName", displayName);
	}

	public List<String> getDnsServers() {
		return dnsServers;
	}

	public void setDnsServers(List<String> dnsServers) {
		this.dnsServers = dnsServers;
		markDirty("dnsServers", dnsServers);
	}

	public List<String> getDnsSuffixList() {
		return dnsSuffixList;
	}

	public void setDnsSuffixList(List<String> dnsSuffixList) {
		this.dnsSuffixList = dnsSuffixList;
		markDirty("dnsSuffixList", dnsSuffixList);
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
		markDirty("cidr", cidr);
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
		markDirty("gateway", gateway);
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
		markDirty("netmask", netmask);
	}

	public Integer getIpCount() {
		return ipCount;
	}

	public void setIpCount(Integer ipCount) {
		this.ipCount = ipCount;
		markDirty("ipCount", ipCount);
	}

	public Integer getIpFreeCount() {
		return ipFreeCount;
	}

	public void setIpFreeCount(Integer ipFreeCount) {
		this.ipFreeCount = ipFreeCount;
		markDirty("ipFreeCount", ipFreeCount);
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", internalId);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		markDirty("lastUpdated", lastUpdated);
	}
}
