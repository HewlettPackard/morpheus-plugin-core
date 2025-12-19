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

package com.morpheusdata.response;

import com.morpheusdata.model.StorageVolume;
import com.morpheusdata.request.UpdateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Response object for building a resize volume changelist.
 * @since 1.3.0
 */
public class BuildResizeVolumeChangelistResponse{
	/**
	 * List of storage volume maps to be added.
	 */
	protected List<Map<String, Object>> addList = new ArrayList<>();
	/**
	 * List of storage volumes to be updated.
	 */
	protected List<UpdateModel<StorageVolume>> updateList = new ArrayList<>();
	/**
	 * List of storage volumes to be removed.
	 */
	protected List<StorageVolume> removeList = new ArrayList<>();
	/**
	 * Total storage size in the changelist.
	 */
	double totalStorage;

	public List<Map<String, Object>> getAddList() {
		return addList;
	}

	public void setAddList(List<Map<String, Object>> addList) {
		this.addList = addList;
	}

	public List<UpdateModel<StorageVolume>> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<UpdateModel<StorageVolume>> updateList) {
		this.updateList = updateList;
	}

	public List<StorageVolume> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<StorageVolume> removeList) {
		this.removeList = removeList;
	}

	public double getTotalStorage() {
		return totalStorage;
	}

	public void setTotalStorage(double totalStorage) {
		this.totalStorage = totalStorage;
	}
}
