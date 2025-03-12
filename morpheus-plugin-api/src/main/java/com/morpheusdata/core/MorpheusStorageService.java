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

/**
 * Provides a top level interface for interacting with Storage related services in Morpheus
 * @since 1.2.5
 * @author David Estes
 */
public interface MorpheusStorageService {
	/**
	 * Returns the StorageVolume Service
	 *
	 * @return An instance of the StorageVolume Service
	 */
	MorpheusStorageVolumeService getVolume();

	/**
	 * Returns the StorageController Service
	 *
	 * @return An instance of the StorageController Service
	 */
	MorpheusStorageControllerService getController();

	/**
	 * Returns the StorageServer Service
	 *
	 * @return An instance of the StorageServer Service
	 */
	MorpheusStorageServerService getServer();

	/**
	 * Returns the StorageBucket Service
	 *
	 * @return An instance of the StorageBucket Service
	 */
	MorpheusStorageBucketService getBucket();


}
