package com.morpheusdata.core;

import com.morpheusdata.model.AccountPrice;
import com.morpheusdata.model.AccountPriceHistory;
import com.morpheusdata.model.projection.AccountPriceHistoryIdentityProjection;
import io.reactivex.rxjava3.core.Observable;

import java.util.Collection;
import java.util.List;

/**
 * Context methods for syncing AccountPriceHistory in Morpheus
 * @since 0.15.4
 */
public interface MorpheusAccountPriceHistoryService extends MorpheusDataService<AccountPriceHistory, AccountPriceHistoryIdentityProjection>, MorpheusIdentityService<AccountPriceHistoryIdentityProjection> {

	/**
	 * Get a list of AccountPriceHistory projections based on {@link com.morpheusdata.model.AccountPrice}
	 * AccountPrice must, at least, have an id
	 * @param accountPrice {@link AccountPrice}
	 * @return Observable stream of sync projection
	 */
	Observable<AccountPriceHistoryIdentityProjection> listIdentityProjections(AccountPrice accountPrice);

	/**
	 * Get a list of AccountPriceHistory projections based on code
	 * @param codeList a list of AccountPriceHistory codes
	 * @return Observable stream of sync projection
	 */
	Observable<AccountPriceHistoryIdentityProjection> listIdentityProjectionsByCode(List<String> codeList);

	/**
	 * Get a list of AccountPriceHistory objects from a list of projection ids
	 * @param ids AccountPriceHistory ids
	 * @return Observable stream of AccountPriceHistory objects
	 */
	@Deprecated(since="0.15.4")
	Observable<AccountPriceHistory> listById(Collection<Long> ids);

	/**
	 * Get a list of AccountPriceHistory objects from a list of projection codes
	 * @param codes AccountPriceHistory codes
	 * @return Observable stream of AccountPriceHistory objects
	 */
	@Deprecated(since="0.15.4")
	Observable<AccountPriceHistory> listByCode(Collection<String> codes);

	/**
	 * Get a list of AccountPriceHistory objects from a list of account price ids
	 * @param accountPriceIds AccountPrice ids
	 * @return Observable stream of AccountPriceHistory objects
	 */
	@Deprecated(since="0.15.4")
	Observable<AccountPriceHistory> listByAccountPriceIds(Collection<Long> accountPriceIds);
}

