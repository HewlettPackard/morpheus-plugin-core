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

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.Datastore;
import com.morpheusdata.response.ServiceResponse;

/**
 * Provides high-level GFS2 filesystem operations for {@link com.morpheusdata.core.providers.DatastoreTypeProvider} implementations.
 * This service wraps the full GFS2 lifecycle including pacemaker cluster resource management, filesystem formatting,
 * journal tuning, and libvirt storage pool creation/removal on each hypervisor in the cluster.
 * <p>
 * GFS2 (Global File System 2) is a shared-disk cluster filesystem used in HPE VME/MVM clusters. Creating a GFS2
 * datastore involves formatting the block device with {@code mkfs.gfs2}, configuring pacemaker fencing and cluster
 * resources, and defining a libvirt storage pool on each hypervisor node.
 * </p>
 * <p>
 * This service is accessible via {@code morpheusContext.getServices().getStorage().getGfs2DatastoreService()}.
 * </p>
 *
 * <p><b>Example usage in a DatastoreTypeProvider:</b></p>
 * <pre>{@code
 *   MorpheusGfs2DatastoreService gfs2Service = morpheusContext.getServices().getStorage().getGfs2DatastoreService();
 *   ServiceResponse result = gfs2Service.createGfs2Datastore(datastore);
 *   if (!result.success) {
 *       // handle error
 *   }
 * }</pre>
 *
 * @author Morpheus
 * @since 1.4.0
 */
public interface MorpheusGfs2DatastoreService {

	/**
	 * Creates a GFS2 datastore on the cluster. This performs the full lifecycle:
	 * <ol>
	 *   <li>Validates the datastore configuration (block device, cluster nodes, etc.)</li>
	 *   <li>Prepares the filesystem (formats with {@code mkfs.gfs2} if needed, or tunes existing journals)</li>
	 *   <li>Configures pacemaker stonith fencing and cluster resources</li>
	 *   <li>Creates libvirt storage pools on each online hypervisor in the cluster</li>
	 * </ol>
	 * The datastore must reference a {@link ComputeServerGroup} via its {@code refType} and {@code refId} fields,
	 * and must have a block device configured in its config map ({@code datastore.configMap.blockDevice}).
	 *
	 * @param datastore the datastore to create, with refType='ComputeServerGroup', refId set, and configMap containing blockDevice
	 * @return a {@link ServiceResponse} indicating success or failure. On success, the datastore data is returned.
	 */
	ServiceResponse<Datastore> createGfs2Datastore(Datastore datastore);

	/**
	 * Removes a GFS2 datastore from the cluster. This performs the full teardown:
	 * <ol>
	 *   <li>Removes libvirt storage pools from each online hypervisor</li>
	 *   <li>Cleans up pacemaker cluster resources associated with the datastore</li>
	 * </ol>
	 *
	 * @param datastore the datastore to remove
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse removeGfs2Datastore(Datastore datastore);

	/**
	 * Prepares the GFS2 filesystem on the block device without creating storage pools. This is useful when
	 * the block device needs to be formatted or tuned independently of pool creation. Performs:
	 * <ul>
	 *   <li>Partition probe on the hypervisor</li>
	 *   <li>Filesystem format with {@code mkfs.gfs2} if block device is unformatted or requires reformatting</li>
	 *   <li>Journal tuning via {@code gfs2_jadd} if the cluster has grown</li>
	 * </ul>
	 *
	 * @param datastore the datastore containing block device configuration
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse prepareGfs2Filesystem(Datastore datastore);

	/**
	 * Creates a GFS2 libvirt storage pool on a specific hypervisor. This is useful for adding a new node
	 * to an existing GFS2 cluster datastore without re-running the full create flow.
	 *
	 * @param hypervisor the hypervisor to create the storage pool on
	 * @param datastore the GFS2 datastore
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse createGfs2StoragePool(ComputeServer hypervisor, Datastore datastore);

	/**
	 * Removes a GFS2 libvirt storage pool from a specific hypervisor. This is useful for removing a node
	 * from a GFS2 cluster without tearing down the entire datastore.
	 *
	 * @param hypervisor the hypervisor to remove the storage pool from
	 * @param datastore the GFS2 datastore
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse removeGfs2StoragePool(ComputeServer hypervisor, Datastore datastore);
}
