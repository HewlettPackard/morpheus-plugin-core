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

import com.morpheusdata.model.health.ApplianceHealth;

/**
 * Synchronous version of {@link com.morpheusdata.core.admin.MorpheusApplianceHealthService}.
 * Provides access to appliance health statistics in a blocking manner.
 *
 * @since 1.4.0
 * @author alex.clement
 */
public interface MorpheusSynchronousApplianceHealthService {

	/**
	 * Returns the most recently cached appliance health data.
	 * @return an {@link ApplianceHealth} containing all health metric sections
	 */
	ApplianceHealth getHealth();

	/**
	 * Returns live appliance health data by querying all subsystems in real time.
	 * @return an {@link ApplianceHealth} containing all health metric sections
	 */
	ApplianceHealth getLiveHealth();
}
