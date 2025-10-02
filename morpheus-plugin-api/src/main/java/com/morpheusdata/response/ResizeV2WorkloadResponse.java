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
import com.morpheusdata.request.ResizeV2Request;

import java.util.Map;

/**
 * Response model for {@link com.morpheusdata.core.providers.WorkloadProvisionProvider.ResizeV2Facet#resizeWorkload(Instance, Workload, ResizeV2Request, Map)}
 * <p>
 * This serves as a placeholder for future enhancement.
 */
public class ResizeV2WorkloadResponse {
	/**
	 * Indicates whether we should preserve volumes during removal. If true, we will delete the volume from morpheus
	 * but not on the datastore. If false, we will delete volumes both from morpheus and on the datastore.
	 */
	public Boolean preserveVolumes = false;

	public Boolean getPreserveVolumes() {
		return preserveVolumes;
	}

	public void setPreserveVolumes(Boolean preserveVolumes) {
		this.preserveVolumes = preserveVolumes;
	}
}
