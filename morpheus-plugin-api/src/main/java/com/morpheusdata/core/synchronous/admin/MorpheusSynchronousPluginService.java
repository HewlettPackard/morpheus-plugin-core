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

import com.morpheusdata.core.synchronous.MorpheusSynchronousDataQueryService;
import com.morpheusdata.model.PluginInfo;
import com.morpheusdata.response.ServiceResponse;

/**
 * Provides synchronous (blocking) query access to plugins registered within the Morpheus appliance.
 * This is the synchronous counterpart to {@link com.morpheusdata.core.admin.MorpheusPluginService}.
 *
 * @author David Estes
 * @since 1.4.0
 * @see com.morpheusdata.core.admin.MorpheusPluginService
 */
public interface MorpheusSynchronousPluginService extends MorpheusSynchronousDataQueryService<PluginInfo> {

	/**
	 * Calls the {@link com.morpheusdata.core.providers.PluginProvider#health()} method on the live provider
	 * identified by the given provider code. If no provider with the given code is currently loaded,
	 * an error {@link ServiceResponse} is returned.
	 *
	 * @param code the unique provider code to look up
	 * @return a {@link ServiceResponse} from the provider's health check
	 */
	ServiceResponse health(String code);
}
