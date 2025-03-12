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

import com.bertramlabs.plugins.karman.CloudFileInterface;
import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.StorageVolume;
import com.morpheusdata.model.VirtualImage;
import com.morpheusdata.response.ServiceResponse;

/**
 * Provides pass-through operations for the {@link com.morpheusdata.core.providers.DatastoreTypeProvider} in the event the custom datastore type is simply exposing
 * a filesystem based mount where qcow2 files can be created. This could be used when a custom datastore type provider wraps something like an NFS Pool.
 * @author David Estes
 * @since 1.2.5
 */
public interface MorpheusVmeQcow2DatastoreService {
	/**
	 * Perform any operations necessary on the target to remove a volume. This is used to remove a volume on a storage server
	 * It is typically called as part of server teardown.
	 * @param volume the current volume to remove
	 * @param server the server the volume is being removed from (may contain information such as parentServer (hypervisor) or cluster)
	 * @param removeSnapshots whether to remove snapshots associated with the volume. In some implementations this is mandatory and not separate.
	 * @param force whether to force the removal of the volume. This is typically used to force the removal of a volume that is in use.
	 * @return the success state of the removal
	 */
	ServiceResponse removeVolume(StorageVolume volume, ComputeServer server, boolean removeSnapshots, boolean force);

	/**
	 * Perform any operations necessary on the target to create a volume. This is used to create a volume on a storage server
	 * It is typically called as part of server provisioning.
	 * @param volume the current volume to create
	 * @param server the server the volume is being created on (may contain information such as parentServer (hypervisor) or cluster)
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> createVolume(StorageVolume volume, ComputeServer server);

	/**
	 * Clones a volume based on a source volume object. This is one of the most important methods for provisioning as most
	 * {@link VirtualImage} provisioning objects are cloned from a local image cache of source volumes. This is where the QCOW2 may reside
	 * Often times you can infer this from the combination of the sourceVolume object as well as its datastore
	 * <p>
	 *     <code>
	 *         String sourceVolumePath = sourceVolume.datastore.externalPath + '/' + sourceVolume.externalId
	 *         //this is the QCOW2 path typically used for clone operations
	 *         String command = "sudo mkdir -p \"${volume.datastore.externalPath}/${server.externalId ?: server.name}\" ; sudo ionice -c 3 cp -f \"${sourceVolume.datastore.externalPath}/${sourceVolume.externalId}\" \"${volume.datastore.externalPath}/${server.externalId ?: server.name}/${volume.volumeName}\""
	 *		   morpheusContext.executeCommandOnServer(server, command)
	 *     </code>
	 * </p>
	 * @param volume the volume we are creating and cloning into
	 * @param server the server the volume is associated with (typically the workload/vm)
	 * @param sourceVolume the source volume we are cloning from
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, StorageVolume sourceVolume);

	/**
	 * Clones a volume based on a source being the reference to the actual File in the Virtual Image. This can be called in the event there is no image cache or we need to directly stream to an image target.
	 * Remember, this code runs in the manager or morpheus appliance and not on the host itself. In order to stream contents directly to the target , we need to create a link we can fetch using the {@link com.morpheusdata.core.MorpheusFileCopyService}
	 * @see com.morpheusdata.core.MorpheusFileCopyService
	 * @see com.morpheusdata.core.synchronous.MorpheusSynchronousFileCopyService
	 * @param volume the volume we are creating and cloning into
	 * @param server the server the volume is associated with (typically the workload/vm)
	 * @param virtualImage the virtual image this volume is being cloned out of
	 * @param cloudFile the specific disk file (Karman abstraction) that is being cloned
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> cloneVolume(StorageVolume volume, ComputeServer server, VirtualImage virtualImage, CloudFileInterface cloudFile);

	/**
	 * Perform any operations necessary on the target to resize a volume. This is used to resize a volume on a storage server
	 * @param volume the current volume to resize
	 * @param server the server the volume is being resized on (may contain information such as parentServer (hypervisor) or cluster)
	 * @param newSize the new size of the volume... TODO: this exists on the volume record already, is newSize needed?
	 * @return the success state and a copy of the volume
	 */
	ServiceResponse<StorageVolume> resizeVolume(StorageVolume volume, ComputeServer server, Long newSize);

}
