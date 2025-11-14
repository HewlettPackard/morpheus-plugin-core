/*
 *  Copyright 2025 Morpheus Data, LLC.
 *
 * Licensed under the PLUGIN CORE SOURCE LICENSE (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://raw.githubusercontent.com/gomorpheus/morpheus-plugin-core/v1.0.x/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.morpheusdata.core;

import io.reactivex.rxjava3.core.Single;
import java.util.Date;

/**
 * The {@code MorpheusCurrencyConversionService} interface provides methods for retrieving currency conversion rates
 * and performing currency conversions for specified dates and currency types. Implementations of this service
 * should provide asynchronous access to conversion rates and conversion operations using RxJava's {@code Single}.
 * <p>
 * Typical usage involves calling {@link #getCurrencyConversionRate(String, Date, String)} to obtain the conversion
 * rate between two currencies for a given date, and using that rate to convert monetary values as needed.
 * </p>
 */
public interface MorpheusCurrencyConversionService {

	/**
	 * Retrieves the currency conversion rate between two currency types for a specific date.
	 *
	 * @param targetType the target currency type (e.g., "USD", "EUR").
	 * @param rateDate the date for which the conversion rate is required.
	 * @param sourceType the source currency type (e.g., "INR", "GBP").
	 * @return a {@code Single} observable containing a double value with the conversion rate.
	 */
	Single<Double> getCurrencyConversionRate(String targetType, Date rateDate, String sourceType);
}
