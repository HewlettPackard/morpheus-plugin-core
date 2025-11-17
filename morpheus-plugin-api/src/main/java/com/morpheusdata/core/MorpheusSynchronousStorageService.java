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

import com.morpheusdata.core.storage.MorpheusDatastoreTypeService;
import com.morpheusdata.core.storage.MorpheusVmeQcow2DatastoreService;
import com.morpheusdata.core.synchronous.*;
import com.morpheusdata.model.CheckLevel;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.UpdateDefinition;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

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

	/**
	 * Returns the StorageGroup Service
	 *
	 * @return An instance of the StorageGroup Service
	 */
	MorpheusSynchronousStorageGroupService getGroup();

	/**
	 * Returns the DatastoreType Service for performing actions on a Datastore
	 *
	 * @return An instance of the DatastoreType Service
	 */
	MorpheusDatastoreTypeService getDatastoreType();

	ServiceResponse validateUpdate(StorageServer storageServer, UpdateDefinition updateDefinition);

	ServiceResponse executeUpdate(StorageServer storageServer, UpdateDefinition updateDefinition);

	ServiceResponse postUpdate(StorageServer storageServer, UpdateDefinition updateDefinition);

	ServiceResponse rollbackUpdate(StorageServer storageServer, UpdateDefinition updateDefinition);

	ServiceResponse refreshUpdate(StorageServer storageServer);

	ServiceResponse runConfigurationDriftCheck(StorageServer storageServer, CheckLevel checkLevel);

	ServiceResponse getConfigurationDriftDetails(StorageServer storageServer);
}
