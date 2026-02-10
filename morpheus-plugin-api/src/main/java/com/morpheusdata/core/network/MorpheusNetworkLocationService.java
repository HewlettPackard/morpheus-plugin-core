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

package com.morpheusdata.core.network;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.AccountIntegration;
import com.morpheusdata.model.Cloud;
import com.morpheusdata.model.NetworkLocation;
import com.morpheusdata.model.projection.NetworkLocationIdentityProjection;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Collection;
import java.util.List;

/**
 * This Context deals with interactions related to {@link com.morpheusdata.model.NetworkLocation} objects. It can normally
 * be accessed via the primary {@link com.morpheusdata.core.MorpheusContext} via the {@link MorpheusNetworkService}
 * Network Locations represent geographic or logical locations associated with networks and cloud resources.
 *
 * <p><strong>Examples:</strong></p>
 * <pre>{@code
 * morpheusContext.getNetwork().getNetworkLocation()
 * }</pre>
 *
 * @see MorpheusNetworkService
 * @author Jordon Saardchit
 */
public interface MorpheusNetworkLocationService extends MorpheusDataService<NetworkLocation, NetworkLocationIdentityProjection>, MorpheusIdentityService<NetworkLocationIdentityProjection> {

}

