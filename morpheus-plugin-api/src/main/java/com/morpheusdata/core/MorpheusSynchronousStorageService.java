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

package com.morpheusdata.core;

import com.morpheusdata.core.storage.MorpheusVmeQcow2DatastoreService;
import com.morpheusdata.core.synchronous.MorpheusSynchronousStorageBucketService;
import com.morpheusdata.core.synchronous.MorpheusSynchronousStorageControllerService;
import com.morpheusdata.core.synchronous.MorpheusSynchronousStorageServerService;
import com.morpheusdata.core.synchronous.MorpheusSynchronousStorageVolumeService;

public interface MorpheusSynchronousStorageService {
	/**
	 * Returns the StorageVolume Service
	 *
	 * @return An instance of the StorageVolume Service
	 */
	MorpheusSynchronousStorageVolumeService getVolume();

	/**
	 * Returns the StorageController Service
	 *
	 * @return An instance of the StorageController Service
	 */
	MorpheusSynchronousStorageControllerService getController();

	/**
	 * Returns the StorageServer Service
	 *
	 * @return An instance of the StorageServer Service
	 */
	MorpheusSynchronousStorageServerService getServer();

	/**
	 * Returns the StorageBucket Service
	 *
	 * @return An instance of the StorageBucket Service
	 */
	MorpheusSynchronousStorageBucketService getBucket();

	MorpheusVmeQcow2DatastoreService getVmeQcow2DatastoreService();
}
