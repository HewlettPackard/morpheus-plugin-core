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

package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServerInterface;
import com.morpheusdata.model.ServicePlan;
import com.morpheusdata.model.StorageController;
import com.morpheusdata.model.StorageVolume;

import java.util.List;
import java.util.Map;

public class ResizeRequest {

	/**
	 * The desired ServicePlan
	 */
	public ServicePlan plan;

	/**
	 * The desired maxMemory in bytes
	 */
	public Long maxMemory;

	/**
	 * The desired max cores
	 */
	public Long maxCores;

	/**
	 * The desired cores per socket
	 */
	public Long coresPerSocket;

	/**
	 * The desired max storage.
	 */
	public Long maxStorage;

	/**
	 * StorageVolumes that should be deleted
	 */
	public List<StorageVolume> volumesDelete;

	/**
	 * StorageVolumes that should be added. Per-volume option values are carried in
	 * {@code volumesAdd[n].config}, a map that may contain nested values, false, or null.
	 * The SDK transports this map without filtering, merging, validation, or execution.
	 */
	public List<Map> volumesAdd;

	/**
	 * StorageVolumes that should be updated, including config-only changes. Desired per-volume option
	 * values are carried in {@code volumesUpdate[n].updateProps.config}. Existing values are available
	 * from {@code volumesUpdate[n].existingModel.configMap}. Providers must preserve config data when
	 * handling the changelist; the SDK does not apply it to the existing model or the underlying disk.
	 */
	public List<UpdateModel<StorageVolume>> volumesUpdate;

	/**
	 * StorageControllers that should be deleted
	 */
	public List<StorageController> controllersDelete;

	/**
	 * StorageControllers that should be added
	 */
	public List<Map> controllersAdd;

	/**
	 * StorageControllers that should be updated
	 */
	public List<UpdateModel<StorageController>> controllersUpdate;

	/**
	 * ComputeServerInterfaces that should be deleted
	 */
	public List<ComputeServerInterface> interfacesDelete;

	/**
	 * ComputeServerInterfaces that should be added
	 */
	public List<Map> interfacesAdd;

	/**
	 * ComputeServerInterfaces that should be updated
	 */
	public List<UpdateModel<ComputeServerInterface>> interfacesUpdate;

	public ResizeRequest(){
	}

	public ResizeRequest(ServicePlan plan,
						 Long maxMemory, Long maxCores, Long maxStorage, Long coresPerSocket,
						 List<StorageVolume> volumesDelete, List<Map> volumesAdd, List<UpdateModel<StorageVolume>> volumesUpdate,
						 List<StorageController> controllersDelete, List<Map> controllersAdd, List<UpdateModel<StorageController>> controllersUpdate,
						 List<ComputeServerInterface> interfacesDelete, List<Map> interfacesAdd, List<UpdateModel<ComputeServerInterface>> interfacesUpdate) {
		this.plan = plan;
		this.maxMemory = maxMemory;
		this.maxCores = maxCores;
		this.coresPerSocket = coresPerSocket;
		this.maxStorage = maxStorage;
		this.volumesDelete = volumesDelete;
		this.volumesAdd = volumesAdd;
		this.volumesUpdate = volumesUpdate;
		this.controllersDelete = controllersDelete;
		this.controllersAdd = controllersAdd;
		this.controllersUpdate = controllersUpdate;
		this.interfacesDelete = interfacesDelete;
		this.interfacesAdd = interfacesAdd;
		this.interfacesUpdate = interfacesUpdate;
	}
}
