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

import com.morpheusdata.model.NodeRoleOption;
import com.morpheusdata.model.StorageOperation;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.model.StorageServerNode;
import com.morpheusdata.request.StorageOperationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.Collection;
import java.util.Map;

/**
 * Optional facet for a {@link StorageProvider} that can change the role of a {@link StorageServerNode}
 * in a software-defined storage cluster. Changing a node's role is also how a host is added to the
 * storage cluster after it joins the compute cluster.
 * <p>
 * Every method has a default that returns an unsuccessful {@link ServiceResponse}, so a provider
 * implements only what its array supports.
 * <p>
 * The role change does not claim drives itself. The drives the role needs are claimed through
 * {@link StorageProviderDriveAddition} once the role is applied.
 * <p>
 * The steps reported in {@link StorageOperation#getOpEntry()} are {@code reserving}, {@code claiming},
 * {@code applying} and {@code resyncing}. A dry run returns them with the reservation the new role
 * lands on and the controller count the cluster is left with.
 *
 * @since 1.6.0
 * @see StorageOperationRequest
 * @see StorageOperation
 */
public interface StorageProviderNodeRole {

	/**
	 * Reports which roles the node may take now. A role the cluster cannot take is returned
	 * unavailable with the reason, so the picker shows it before the operator commits.
	 *
	 * @param storageServer the storage cluster
	 * @param node the node whose role would change
	 * @param opts additional options
	 * @return every role with its availability
	 */
	default ServiceResponse<Collection<NodeRoleOption>> getNodeRoleConstraints(StorageServer storageServer, StorageServerNode node, Map opts) {
		return ServiceResponse.error("Node role constraints not supported");
	}

	/**
	 * Applies the role.
	 * <p>
	 * With {@link StorageOperationRequest.OpType#DRY_RUN} it reports what the change does to the node
	 * and to the cluster, including the reservation the new role requires and the drives it must claim.
	 * With {@link StorageOperationRequest.OpType#UPDATE} it applies the role and returns once the array
	 * accepts; progress is read with {@link #refreshNodeRole}.
	 *
	 * @param storageServer the storage cluster
	 * @param node the node whose role changes
	 * @param role the role code; one of the {@link com.morpheusdata.model.StorageServerNodePersona} names
	 * @param driveIds the {@code uniqueId} of each drive the role will claim
	 * @param request whether to judge or perform the change, and how it is tracked
	 * @return the operation with its steps; a refusal is an unsuccessful response
	 */
	default ServiceResponse<StorageOperation> applyNodeRole(StorageServer storageServer, StorageServerNode node, String role, Collection<String> driveIds, StorageOperationRequest request) {
		return ServiceResponse.error("Node role change not supported");
	}

	/**
	 * Reports how far the role change has run.
	 *
	 * @param storageServer the storage cluster
	 * @param operation the operation returned by {@link #applyNodeRole}
	 * @return the operation with its current state and steps
	 */
	default ServiceResponse<StorageOperation> refreshNodeRole(StorageServer storageServer, StorageOperation operation) {
		return ServiceResponse.error("Node role change not supported");
	}
}
