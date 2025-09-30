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

import com.morpheusdata.model.Instance;
import com.morpheusdata.model.Workload;
import com.morpheusdata.request.ResizeRequest;
import com.morpheusdata.request.ResizeV2Request;

import java.util.Map;

/**
 * Response model for {@link com.morpheusdata.core.providers.WorkloadProvisionProvider.ResizeV2Facet#validateResizeWorkload(Instance, Workload, ResizeV2Request, Map)}
 */
public class ValidateResizeV2WorkloadResponse {
	public Boolean allowed;
	public Boolean hotResize;

	/**
	 * @return true if we're allowed to resize, false otherwise
	 */
	public Boolean getAllowed() {
		return allowed;
	}

	/**
	 * @param allowed true if we're allowed to resize, false otherwise
	 */
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	/**
	 * @return true if resizing can be done without a restart, false if a restart is required
	 */
	public Boolean getHotResize() {
		return hotResize;
	}

	/**
	 * Flags if the resize requires a restart to take effect. The workload being operated on will be
	 * restarted if hotResize = false.
	 * @param hotResize true if resizing can be done without a restart, false if a restart is required
	 */
	public void setHotResize(Boolean hotResize) {
		this.hotResize = hotResize;
	}
}
