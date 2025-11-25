package com.morpheusdata.model.projection;

import com.morpheusdata.model.projection.MorpheusIdentityModel;

/**
 * Provides a subset of properties from the {@link com.morpheusdata.model.AccountPriceHistory} object for doing a sync match
 * comparison with less bandwidth usage and memory footprint. This is a DTO Projection object
 * @since 0.15.4
 */
public class AccountPriceHistoryIdentityProjection extends MorpheusIdentityModel {
	protected String code;
	protected Long accountPriceId;
	protected String currency;
	protected String priceUnit;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	public Long getAccountPriceId() {
		return accountPriceId;
	}

	public void setAccountPriceId(Long accountPriceId) {
		this.accountPriceId = accountPriceId;
		markDirty("accountPriceId", accountPriceId);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		markDirty("currency", currency);
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
		markDirty("priceUnit", priceUnit);
	}
}
