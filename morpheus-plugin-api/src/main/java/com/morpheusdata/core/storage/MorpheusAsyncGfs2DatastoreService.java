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

package com.morpheusdata.core.storage;

import com.morpheusdata.model.Datastore;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

/**
 * Async variant of {@link MorpheusGfs2DatastoreService}. All methods return {@link Single} wrappers
 * around their synchronous counterparts to support reactive plugin implementations.
 * <p>
 * This service is accessible via {@code morpheusContext.getAsync().getStorage().getGfs2DatastoreService()}.
 * </p>
 *
 * @see MorpheusGfs2DatastoreService
 * @author Morpheus
 * @since 1.4.0
 */
public interface MorpheusAsyncGfs2DatastoreService {

	/**
	 * Creates a GFS2 datastore on the cluster. This performs the full lifecycle:
	 * <ol>
	 *   <li>Validates the datastore configuration (block device, cluster nodes, etc.)</li>
	 *   <li>Prepares the filesystem (formats with {@code mkfs.gfs2} if needed, or tunes existing journals)</li>
	 *   <li>Configures pacemaker stonith fencing and cluster resources</li>
	 *   <li>Creates libvirt storage pools on each online hypervisor in the cluster</li>
	 * </ol>
	 *
	 * @param datastore the datastore to create
	 * @return a {@link Single} emitting a {@link ServiceResponse} indicating success or failure
	 */
	Single<ServiceResponse<Datastore>> createGfs2Datastore(Datastore datastore);

	/**
	 * Removes a GFS2 datastore from the cluster. This performs the full teardown:
	 * <ol>
	 *   <li>Removes libvirt storage pools from each online hypervisor</li>
	 *   <li>Cleans up pacemaker cluster resources associated with the datastore</li>
	 * </ol>
	 *
	 * @param datastore the datastore to remove
	 * @return a {@link Single} emitting a {@link ServiceResponse} indicating success or failure
	 */
	Single<ServiceResponse> removeGfs2Datastore(Datastore datastore);

	/**
	 * Updates a GFS2 datastore on the cluster.
	 *
	 * @param datastore the datastore to update
	 * @return a {@link Single} emitting a {@link ServiceResponse} indicating success or failure
	 */
	Single<ServiceResponse<Datastore>> updateGfs2Datastore(Datastore datastore);
}
