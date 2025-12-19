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

package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.model.NetworkGroup;

/**
 * This data service deals with interactions related to {@link NetworkGroup} objects.
 *
 * <p><strong>Examples:</strong></p>
 * <pre>{@code
 *   morpheusContext.getAsync().getNetwork().getNetworkGroup()
 * }</pre>
 *
 * @since 1.3.0
 */
public interface MorpheusNetworkGroupService extends MorpheusDataService<NetworkGroup, NetworkGroup> {

}
