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

package com.morpheusdata.core.system;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.system.System;
import com.morpheusdata.model.projection.SystemIdentityProjection;

/**
 * Provides access to {@link System} objects via the plugin context.
 * Accessible via {@code morpheusContext.async.system}.
 * Sub-services are available via {@link #getType()} and {@link #getComponent()}.
 */
public interface MorpheusSystemService extends MorpheusDataService<System, SystemIdentityProjection>, MorpheusIdentityService<SystemIdentityProjection> {

	/**
	 * Returns the {@link MorpheusSystemTypeService} for querying SystemType objects.
	 * @return an instance of MorpheusSystemTypeService
	 */
	MorpheusSystemTypeService getType();

	/**
	 * Returns the {@link MorpheusSystemComponentService} for querying SystemComponent objects.
	 * @return an instance of MorpheusSystemComponentService
	 */
	MorpheusSystemComponentService getComponent();
}
