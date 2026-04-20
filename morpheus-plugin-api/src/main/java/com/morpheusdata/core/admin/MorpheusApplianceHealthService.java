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

package com.morpheusdata.core.admin;

import com.morpheusdata.model.health.ApplianceHealth;
import io.reactivex.rxjava3.core.Single;

/**
 * Provides access to appliance health statistics. The data returned mirrors
 * what is available via the {@code /api/health} REST endpoint.
 *
 * @since 1.4.0
 * @author alex.clement
 */
public interface MorpheusApplianceHealthService {

	/**
	 * Returns the most recently cached appliance health data (updated every 5 minutes). Use this method
	 * unless you need real-time health data, as it is more efficient than {@link #getLiveHealth()}.
	 * It reads from stored health snapshots rather than querying live systems.
	 * @return an {@link ApplianceHealth} containing all health metric sections
	 */
	Single<ApplianceHealth> getHealth();

	/**
	 * Returns live appliance health data by querying all subsystems in real time
	 * (CPU, memory, storage, threads, database, RabbitMQ, Elasticsearch).
	 * @return an {@link ApplianceHealth} containing all health metric sections
	 */
	Single<ApplianceHealth> getLiveHealth();
}
