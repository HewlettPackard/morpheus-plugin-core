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

package com.morpheusdata.core;

import com.morpheusdata.model.StorageAggregate;

/**
 * Context methods for {@link StorageAggregate} in Morpheus.
 *
 * @author Mike Carlin
 * @since 1.3.0
 */
public interface MorpheusStorageAggregateService extends MorpheusDataService<StorageAggregate, StorageAggregate> {

	/**
	 * Returns the StorageAggregateType Service
	 *
	 * @return An instance of the StorageAggregateType Service
	 */
	MorpheusStorageAggregateTypeService getStorageAggregateType();
}

