/*
 *  Copyright 2026 Morpheus Data, LLC.
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

package com.morpheusdata.core.providers;

import com.morpheusdata.model.ComputeServer;
import com.morpheusdata.model.HostDrive;
import com.morpheusdata.model.StorageOperation;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNodeDisk;
import com.morpheusdata.request.StorageOperationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.Collection;
import java.util.Map;

/**
 * Optional facet for a {@link StorageProvider} that can replace a failed or failing drive in a
 * {@link com.morpheusdata.model.StorageServerNode}.
 * <p>
 * A provider implements this facet only when its array supports the operation, and implements every
 * method of it. Morpheus checks for the facet before dispatching and reports the operation as not supported
 * where the provider does not implement it.
 * <p>
 * The steps reported in {@link StorageOperation#getOpEntry()} are {@code evacuating}, {@code unclaiming},
 * {@code awaiting-swap}, {@code identifying}, {@code claiming} and {@code rebuilding}. A dry run on
 * {@link #evacuateDrive} returns them with the headroom the cluster keeps while the drive is out. A drive
 * that has already failed outright comes back with {@code evacuating} already {@code PASSED} and nothing
 * to move. The operation rests at {@code awaiting-swap} while the drive is physically swapped, because that
 * step has no meaningful timeout.
 * <p>
 * The host-side steps are not part of this facet: the drive is lit with
 * {@link com.morpheusdata.core.MorpheusComputeServerService#locateHostDrive} and what was fitted is read with
 * {@link com.morpheusdata.core.MorpheusComputeServerService#listHostDrives}.
 *
 * @since 1.6.0
 * @see StorageProviderDriveAddition
 * @see StorageOperationRequest
 * @see StorageOperation
 */
public interface StorageProviderDriveReplacement {

	/**
	 * Moves data off the drive.
	 * <p>
	 * With {@link StorageOperationRequest.OpType#DRY_RUN} it reports what the cluster gives up while the
	 * drive is gone and refuses where the drive cannot be taken out now. With
	 * {@link StorageOperationRequest.OpType#UPDATE} it starts the evacuation and returns once the array
	 * accepts; progress is read with {@link #refreshDriveReplacement}.
	 *
	 * @param storageServer the storage cluster
	 * @param disk the drive to evacuate
	 * @param request whether to judge or perform the change, and how it is tracked
	 * @return the operation with its steps; a refusal is an unsuccessful response
	 */
	ServiceResponse<StorageOperation> evacuateDrive(StorageServer storageServer, StorageServerNodeDisk disk, StorageOperationRequest request);

	/**
	 * Releases the drive from the array so it can be pulled.
	 *
	 * @param storageServer the storage cluster
	 * @param disk the drive to release
	 * @param request whether to judge or perform the change, and how it is tracked
	 * @return the operation with its steps; a refusal is an unsuccessful response
	 */
	ServiceResponse<StorageOperation> unclaimDrive(StorageServer storageServer, StorageServerNodeDisk disk, StorageOperationRequest request);

	/**
	 * Picks the drive that was fitted out of what the host now reports, and gives the verdict on it.
	 *
	 * @param storageServer the storage cluster
	 * @param removed the drive that was pulled, for its {@code uniqueId}
	 * @param discovered every drive the host reports after the swap
	 * @param opts additional options
	 * @return the replacement, with {@code candidate}, {@code eligible} and {@code candidacyReason} filled.
	 *         A drive carrying the removed {@code uniqueId} is the same drive reseated and is returned
	 *         ineligible with that as the reason.
	 */
	ServiceResponse<HostDrive> identifyReplacement(StorageServer storageServer, StorageServerNodeDisk removed, Collection<HostDrive> discovered, Map opts);

	/**
	 * Claims the replacement and starts the rebuild.
	 *
	 * @param storageServer the storage cluster
	 * @param server the host the replacement is fitted in
	 * @param drive the replacement returned by {@link #identifyReplacement}
	 * @param request whether to judge or perform the change, and how it is tracked
	 * @return the operation with its steps; a refusal is an unsuccessful response
	 */
	ServiceResponse<StorageOperation> claimDrive(StorageServer storageServer, ComputeServer server, HostDrive drive, StorageOperationRequest request);

	/**
	 * Reports where the operation has reached, including rebuild progress after the claim. Polled by the
	 * process driving the sequence.
	 *
	 * @param storageServer the storage cluster
	 * @param operation the operation returned by an earlier step
	 * @return the operation with its current state and steps
	 */
	ServiceResponse<StorageOperation> refreshDriveReplacement(StorageServer storageServer, StorageOperation operation);

	/**
	 * Abandons the operation and returns the node to the state it was in, for an evacuated drive put back
	 * rather than replaced.
	 *
	 * @param storageServer the storage cluster
	 * @param operation the operation to abandon
	 * @return the operation with its final state and steps
	 */
	ServiceResponse<StorageOperation> cancelDriveReplacement(StorageServer storageServer, StorageOperation operation);
}
