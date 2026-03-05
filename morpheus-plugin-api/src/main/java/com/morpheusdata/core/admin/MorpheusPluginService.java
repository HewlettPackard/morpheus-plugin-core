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

import com.morpheusdata.core.MorpheusDataQueryService;
import com.morpheusdata.model.PluginInfo;
import com.morpheusdata.model.projection.PluginInfoIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * Provides query access to plugins registered within the Morpheus appliance.
 * This is a read-only service intended for use in system precheck scenarios — for example,
 * verifying that required plugins are present and healthy before initiating an operation.
 *
 * <p>Use {@link #health(String)} to invoke the {@link com.morpheusdata.core.providers.PluginProvider#health()}
 * method on a live provider at runtime.</p>
 *
 * @author David Estes
 * @since 1.4.0
 * @see com.morpheusdata.core.synchronous.admin.MorpheusSynchronousPluginService
 */
public interface MorpheusPluginService extends MorpheusDataQueryService<PluginInfo> {

	/**
	 * Calls the {@link com.morpheusdata.core.providers.PluginProvider#health()} method on the live provider
	 * identified by the given provider code. If no provider with the given code is currently loaded,
	 * an error {@link ServiceResponse} is returned.
	 *
	 * <p><strong>Note:</strong> This is a reactive method and will not perform any operation until subscribed or blockingGet() is called on it.</p>
	 *
	 * @param code the unique provider code to look up
	 * @return a Single containing the {@link ServiceResponse} from the provider's health check
	 */
	Single<ServiceResponse> health(String code);
}
