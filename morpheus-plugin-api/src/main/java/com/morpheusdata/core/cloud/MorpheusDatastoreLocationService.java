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

package com.morpheusdata.core.cloud;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.model.DatastoreLocation;

/**
 * Provides service/context methods for querying {@link com.morpheusdata.model.DatastoreLocation} objects for use with syncing datastore location status.
 *
 * @author Dan DeVilbiss
 * @since 1.2.4
 */
public interface MorpheusDatastoreLocationService extends MorpheusDataService<DatastoreLocation,DatastoreLocation> {
}
