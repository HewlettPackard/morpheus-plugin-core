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

import com.morpheusdata.model.HostDrive;
import com.morpheusdata.model.StorageOperation;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.request.StorageOperationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.Collection;
import java.util.Map;

/**
 * Optional facet for a {@link StorageProvider} that can add drives to an existing
 * {@link StorageServerNode}, growing the node's capacity without adding a host.
 * <p>
 * Every method has a default that returns an unsuccessful {@link ServiceResponse}, so a provider
 * implements only what its array supports.
 * <p>
 * The steps reported in {@link StorageOperation#getOpEntry()} are {@code claiming} and
 * {@code rebalancing}. A dry run returns them with the capacity the node holds now and the capacity it
 * would hold once the rebalance ends. The sizes of the drives come from {@link #listUnclaimedDrives},
 * so a form can total a selection without calling the array again.
 *
 * @since 1.6.0
 * @see StorageProviderNodeRole
 * @see StorageOperationRequest
 * @see StorageOperation
 */
public interface StorageProviderDriveAddition {

	/**
	 * Reads the host again and gives the verdict on every drive it reports that the array has not
	 * claimed.
	 *
	 * @param storageServer the storage cluster
	 * @param node the node whose host is read
	 * @param opts additional options
	 * @return the unclaimed drives with {@code candidate}, {@code eligible} and {@code candidacyReason} filled
	 */
	default ServiceResponse<Collection<HostDrive>> listUnclaimedDrives(StorageServer storageServer, StorageServerNode node, Map opts) {
		return ServiceResponse.error("Drive addition not supported");
	}

	/**
	 * Claims the drives and starts the rebalance.
	 * <p>
	 * With {@link StorageOperationRequest.OpType#DRY_RUN} it reports the capacity the node would hold
	 * with these drives claimed and refuses a set the array will not take. With
	 * {@link StorageOperationRequest.OpType#UPDATE} it claims them and returns once the array accepts;
	 * progress is read with {@link #refreshDriveAddition}.
	 *
	 * @param storageServer the storage cluster
	 * @param node the node the drives are added to
	 * @param driveIds the {@code uniqueId} of each drive to claim
	 * @param request whether to judge or perform the change, and how it is tracked
	 * @return the operation with its steps; a refusal is an unsuccessful response
	 */
	default ServiceResponse<StorageOperation> addDrives(StorageServer storageServer, StorageServerNode node, Collection<String> driveIds, StorageOperationRequest request) {
		return ServiceResponse.error("Drive addition not supported");
	}

	/**
	 * Reports how far the rebalance has run.
	 *
	 * @param storageServer the storage cluster
	 * @param operation the operation returned by {@link #addDrives}
	 * @return the operation with its current state and steps
	 */
	default ServiceResponse<StorageOperation> refreshDriveAddition(StorageServer storageServer, StorageOperation operation) {
		return ServiceResponse.error("Drive addition not supported");
	}
}
