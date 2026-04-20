/*
 *  Copyright 2024 Morpheus Data, LLC.
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

package com.morpheusdata.core.synchronous.admin;

import com.morpheusdata.model.license.ApplianceLicenseInfo;
import com.morpheusdata.response.ServiceResponse;

/**
 * Provides synchronous (blocking) access to appliance license data and operations.
 * Mirrors the functionality of the {@code /api/license} REST endpoints.
 *
 * <p>Example usage:
 * <pre>
 *   ApplianceLicenseInfo info = morpheusContext.services.admin.license.getLicense()
 *   log.info("Product tier: ${info.license?.productTier}")
 * </pre>
 *
 * @since 1.4.0
 * @author Morpheus Data
 */
public interface MorpheusSynchronousApplianceLicenseService {

	/**
	 * Returns the current appliance license information including usage counts and limit checks.
	 * @return the current {@link ApplianceLicenseInfo}
	 */
	ApplianceLicenseInfo getLicense();

	/**
	 * Applies a license key to the appliance.
	 * @param licenseKey  the license key string to apply
	 * @param stackLicense if true, stacks on top of existing licenses; if false, replaces them
	 * @return a {@link ServiceResponse} containing the updated {@link ApplianceLicenseInfo} on success
	 */
	ServiceResponse<ApplianceLicenseInfo> applyLicense(String licenseKey, boolean stackLicense);

	/**
	 * Removes an installed license from the appliance.
	 * @param keyId the key ID of the license to remove, or null to remove all
	 * @return a {@link ServiceResponse} containing the updated {@link ApplianceLicenseInfo} on success
	 */
	ServiceResponse<ApplianceLicenseInfo> removeLicense(String keyId);
}
