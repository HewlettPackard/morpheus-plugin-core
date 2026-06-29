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

package com.morpheusdata.core.synchronous.system;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.system.SystemComponent;
import com.morpheusdata.model.projection.SystemComponentIdentityProjection;
import io.reactivex.rxjava3.core.Single;

/**
 * Synchronous access to {@link SystemComponent} objects via the plugin context.
 * Accessible via {@code morpheusContext.services.system.getComponent()}.
 */
public interface MorpheusSynchronousSystemComponentService extends MorpheusSynchronousDataService<SystemComponent, SystemComponentIdentityProjection>, MorpheusSynchronousIdentityService<SystemComponentIdentityProjection> {

	/**
	 * Trigger a refresh on the underlying integration referenced by this system component.
	 * Resolves the component's refType/refId and dispatches to the appropriate service refresh.
	 * Supported refTypes: ComputeZone (Cloud), StorageServer, NetworkServer.
	 * @param component the system component to refresh
	 * @return Boolean returns the result of the refresh request.
	 */
	Boolean refreshComponent(SystemComponent component);

	/**
	 * Trigger a daily (full) refresh on the underlying integration referenced by this system component.
	 * Resolves the component's refType/refId and dispatches to the appropriate service refresh.
	 * Supported refTypes: ComputeZone (Cloud), StorageServer, NetworkServer.
	 * @param component the system component to refresh
	 * @return Boolean returns the result of the refresh request.
	 */
	Boolean refreshComponentDaily(SystemComponent component);
}
