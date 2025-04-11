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

package com.morpheusdata.request;

import java.util.Collection;
import java.util.List;

import com.morpheusdata.model.Cloud;
import com.morpheusdata.model.CloudPool;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeTypeSet;
import com.morpheusdata.model.InstanceTypeLayout;

/**
 * Criteria for filtering service plans in the Morpheus UI.
 * <p>
 * This can be used by plugins to filter the list of service plans available when provisioning an
 * instance or in related areas that list service plans.
 * @author Mike Carlin
 * @since 1.2.6
 * @see com.morpheusdata.core.providers.CloudProvider#filterServicePlans(Cloud, Collection, FilterServicePlansCriteria)
 */
public class FilterServicePlansCriteria {

	public InstanceTypeLayout layout;
	public ComputeServer server;
	public String subRegionCode;
	public List<CloudPool> cloudPools;
	public ComputeTypeSet computeTypeSet;

	/**
	 * Set to true if filtering should skip any criteria based on permissions. For example, this could be in relation
	 * to cloud pools.
	 */
	public boolean skipPermissions;
}
