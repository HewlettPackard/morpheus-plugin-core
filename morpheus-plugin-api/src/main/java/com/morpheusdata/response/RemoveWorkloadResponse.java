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

package com.morpheusdata.response;

import com.morpheusdata.model.Workload;

import java.util.Map;

public class RemoveWorkloadResponse {
	protected Workload workload;

	protected Map<String,Object> options;

	protected Boolean removeServer = true;
	protected Boolean preserveVolumes = false;

	public Workload getWorkload() {
		return workload;
	}

	public void setWorkload(Workload workload) {
		this.workload = workload;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}

	public Boolean getRemoveServer() {
		return removeServer;
	}

	public void setRemoveServer(Boolean removeServer) {
		this.removeServer = removeServer;
	}

	public Boolean getPreserveVolumes() {
		return preserveVolumes;
	}

	public void setPreserveVolumes(Boolean preserveVolumes) {
		this.preserveVolumes = preserveVolumes;
	}
}
