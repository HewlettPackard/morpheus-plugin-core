package com.morpheusdata.model;

import java.math.BigDecimal;
import java.util.Date;
import com.morpheusdata.model.projection.AccountPriceHistoryIdentityProjection;

public class AccountPriceHistory extends AccountPriceHistoryIdentityProjection {
	protected String incurCharges;
	protected Date dateCreated;
	protected Long volumeTypeId;
	protected BigDecimal price;
	protected Date lastUpdated;
	protected Boolean active;
	protected String updatedBy;
	protected String platform;
	protected String markupType;
	protected BigDecimal customPrice;
	protected String resourceType;
	protected Date endDate;
	protected String softwareOrService;
	protected Date startDate;
	protected Long datastoreId;
	protected Double markupPercent;
	protected BigDecimal markup;
	protected String priceType;
	protected Double matchValue;
	protected String createdBy;
	protected BigDecimal cost;
	protected Long priceTier;

	public String getIncurCharges() {
		return incurCharges;
	}

	public void setIncurCharges(String incurCharges) {
		this.incurCharges = incurCharges;
		markDirty("incurCharges", incurCharges);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated);
	}

	public Long getVolumeTypeId() {
		return volumeTypeId;
	}

	public void setVolumeTypeId(Long volumeTypeId) {
		this.volumeTypeId = volumeTypeId;
		markDirty("volumeTypeId", volumeTypeId);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		markDirty("price", price);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		markDirty("lastUpdated", lastUpdated);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
		markDirty("active", active);
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		markDirty("updatedBy", updatedBy);
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
		markDirty("platform", platform);
	}

	public String getMarkupType() {
		return markupType;
	}

	public void setMarkupType(String markupType) {
		this.markupType = markupType;
		markDirty("markupType", markupType);
	}

	public BigDecimal getCustomPrice() {
		return customPrice;
	}

	public void setCustomPrice(BigDecimal customPrice) {
		this.customPrice = customPrice;
		markDirty("customPrice", customPrice);
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
		markDirty("resourceType", resourceType);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		markDirty("endDate", endDate);
	}

	public String getSoftwareOrService() {
		return softwareOrService;
	}

	public void setSoftwareOrService(String softwareOrService) {
		this.softwareOrService = softwareOrService;
		markDirty("softwareOrService", softwareOrService);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		markDirty("startDate", startDate);
	}

	public Long getDatastoreId() {
		return datastoreId;
	}

	public void setDatastoreId(Long datastoreId) {
		this.datastoreId = datastoreId;
		markDirty("datastoreId", datastoreId);
	}

	public Double getMarkupPercent() {
		return markupPercent;
	}

	public void setMarkupPercent(Double markupPercent) {
		this.markupPercent = markupPercent;
		markDirty("markupPercent", markupPercent);
	}

	public BigDecimal getMarkup() {
		return markup;
	}

	public void setMarkup(BigDecimal markup) {
		this.markup = markup;
		markDirty("markup", markup);
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
		markDirty("priceType", priceType);
	}

	public Double getMatchValue() {
		return matchValue;
	}

	public void setMatchValue(Double matchValue) {
		this.matchValue = matchValue;
		markDirty("matchValue", matchValue);
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
		markDirty("createdBy", createdBy);
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
		markDirty("cost", cost);
	}

	public Long getPriceTier() {
		return priceTier;
	}

	public void setPriceTier(Long priceTier) {
		this.priceTier = priceTier;
		markDirty("priceTier", priceTier);
	}
}

