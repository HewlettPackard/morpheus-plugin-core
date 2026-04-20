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
import com.morpheusdata.model.system.System;
import com.morpheusdata.model.projection.SystemIdentityProjection;

/**
 * Synchronous access to {@link System} objects via the plugin context.
 * Accessible via {@code morpheusContext.services.system}.
 * Sub-services are available via {@link #getType()} and {@link #getComponent()}.
 */
public interface MorpheusSynchronousSystemService extends MorpheusSynchronousDataService<System, SystemIdentityProjection>, MorpheusSynchronousIdentityService<SystemIdentityProjection> {

	/**
	 * Returns the {@link MorpheusSynchronousSystemTypeService} for querying SystemType objects.
	 * @return an instance of MorpheusSynchronousSystemTypeService
	 */
	MorpheusSynchronousSystemTypeService getType();

	/**
	 * Returns the {@link MorpheusSynchronousSystemComponentService} for querying SystemComponent objects.
	 * @return an instance of MorpheusSynchronousSystemComponentService
	 */
	MorpheusSynchronousSystemComponentService getComponent();
}
