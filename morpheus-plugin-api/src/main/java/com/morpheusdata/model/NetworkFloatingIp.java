package com.morpheusdata.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.projection.NetworkFloatingIpIdentityProjection;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

import java.util.Date;

public class NetworkFloatingIp extends NetworkFloatingIpIdentityProjection {
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkFloatingIpPool floatingPool;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkFloatingIpPoolRange floatingIpPoolRange;

	protected String ipType = IPType.ASSIGNED.toString();
	protected String ipStatus = IpStatus.FREE.toString();
	protected String gatewayAddress;
	protected String subnetMask;
	protected String dnsServer;
	protected String interfaceName;
	protected String description;
	protected Boolean active = true;
	protected Boolean staticIp = true;
	protected String fqdn;
	protected String domainName;
	protected String hostName;
	protected String internalId;
	protected String ptrId;
	protected Date startDate;
	protected Date endDate;
	protected String refType;
	protected Long refId;
	protected Long subRefId;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected NetworkDomain domain;
	@JsonSerialize(using= ModelAsIdOnlySerializer.class)
	protected User createdBy;
	protected Date dateCreated;
	protected Date lastUpdated;
	protected String category;

	public NetworkFloatingIpPool getFloatingPool() {
		return floatingPool;
	}

	public void setFloatingPool(NetworkFloatingIpPool floatingPool) {
		this.floatingPool = floatingPool;
		markDirty("floatingPool", floatingPool);
	}

	public NetworkFloatingIpPoolRange getFloatingIpPoolRange() {
		return floatingIpPoolRange;
	}

	public void setFloatingIpPoolRange(NetworkFloatingIpPoolRange floatingIpPoolRange) {
		this.floatingIpPoolRange = floatingIpPoolRange;
		markDirty("floatingIpPoolRange", floatingIpPoolRange);
	}

	public String getIpType() {
		return ipType;
	}

	public void setIpType(String ipType) {
		this.ipType = ipType;
		markDirty("ipType", ipType);
	}

	public String getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(String ipStatus) {
		this.ipStatus = ipStatus;
		markDirty("ipStatus", ipStatus);
	}

	public String getGatewayAddress() {
		return gatewayAddress;
	}

	public void setGatewayAddress(String gatewayAddress) {
		this.gatewayAddress = gatewayAddress;
		markDirty("gatewayAddress", gatewayAddress);
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
		markDirty("subnetMask", subnetMask);
	}

	public String getDnsServer() {
		return dnsServer;
	}

	public void setDnsServer(String dnsServer) {
		this.dnsServer = dnsServer;
		markDirty("dnsServer", dnsServer);
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
		markDirty("interfaceName", interfaceName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
		markDirty("active", active);
	}

	public Boolean getStaticIp() {
		return staticIp;
	}

	public void setStaticIp(Boolean staticIp) {
		this.staticIp = staticIp;
		markDirty("staticIp", staticIp);
	}

	public String getFqdn() {
		return fqdn;
	}

	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
		markDirty("fqdn", fqdn);
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
		markDirty("domainName", domainName);
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
		markDirty("hostName", hostName);
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
		markDirty("internalId", internalId);
	}

	public String getPtrId() {
		return ptrId;
	}

	public void setPtrId(String ptrId) {
		this.ptrId = ptrId;
		markDirty("ptrId", ptrId);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		markDirty("startDate", startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		markDirty("endDate", endDate);
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
		markDirty("refType", refType);
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
		markDirty("refId", refId);
	}

	public Long getSubRefId() {
		return subRefId;
	}

	public void setSubRefId(Long subRefId) {
		this.subRefId = subRefId;
		markDirty("subRefId", subRefId);
	}

	public NetworkDomain getDomain() {
		return domain;
	}

	public void setDomain(NetworkDomain domain) {
		this.domain = domain;
		markDirty("domain", domain);
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
		markDirty("createdBy",createdBy);
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		markDirty("category", category);
	}

	public enum IPType {
		ASSIGNED("assigned"),
		RESERVED("reserved");

		private final String value;

		IPType(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

	public enum IpStatus {
		FREE("free"),
		ASSIGNED("assigned"),
		PENDING("pending");

		private final String value;

		IpStatus(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}
}
