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

import com.morpheusdata.model.StorageConfigurationConstraints;
import com.morpheusdata.model.StorageOperation;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.request.StorageOperationRequest;
import com.morpheusdata.response.ServiceResponse;

import java.util.Map;

/**
 * Optional facet for a {@link StorageProvider} that can change the configuration of an installed storage
 * cluster, such as a node's Remote Copy or data-path address or the cluster's admin floating address.
 * <p>
 * The fields and their values are carried in {@link StorageOperationRequest#getConfig()}, with what is in
 * force now in {@link StorageOperationRequest#getPreviousConfig()} and the edited keys in
 * {@link StorageOperationRequest#getChangedKeys()}, so the plugin defines and decides which fields it accepts.
 * <p>
 * Every method has a default that returns an unsuccessful {@link ServiceResponse}, so a provider
 * implements only what its array supports.
 * <p>
 * The steps reported in {@link StorageOperation#getOpEntry()} are the nodes, one entry per node in the order
 * the change takes them, each carrying the reservation that host lands on. A node that needs a reboot says so
 * in its messages. The last node to report {@code PASSED} is the one the change has reached.
 *
 * @since 1.6.0
 * @see StorageConfigurationConstraints
 * @see StorageOperationRequest
 * @see StorageOperation
 */
public interface StorageProviderConfiguration {

	/**
	 * Reports which fields may not change once the cluster is installed, and why. Read while the form
	 * renders, so a fixed field is read-only with its reason beside it rather than refused after the
	 * operator commits.
	 *
	 * @param storageServer the storage cluster
	 * @param opts additional options
	 * @return the fixed fields, each with the reason it cannot change
	 */
	default ServiceResponse<StorageConfigurationConstraints> getConfigurationConstraints(StorageServer storageServer, Map opts) {
		return ServiceResponse.error("Configuration not supported");
	}

	/**
	 * Applies the change, one node at a time.
	 * <p>
	 * With {@link StorageOperationRequest.OpType#DRY_RUN} it judges the proposed configuration against the
	 * one in force and reports what applying it costs: the nodes it touches and the order it takes them in,
	 * and the reservation each host lands on with the impact of reaching it. It changes nothing, so it can be
	 * called as often as the operator edits the form.
	 * <p>
	 * With {@link StorageOperationRequest.OpType#UPDATE} the provider applies the array-side change and
	 * returns what it settled on. Morpheus writes the result to the
	 * {@link com.morpheusdata.model.StorageServerNode}, revises the host reservation, and rewrites the
	 * storage server endpoint when the admin address moved. Progress is read with
	 * {@link #refreshConfiguration}.
	 *
	 * @param storageServer the storage cluster
	 * @param request the proposed configuration, what is in force now, and whether to judge or perform the change
	 * @return the operation with one step per node; a refusal is an unsuccessful response
	 */
	default ServiceResponse<StorageOperation> applyConfiguration(StorageServer storageServer, StorageOperationRequest request) {
		return ServiceResponse.error("Configuration not supported");
	}

	/**
	 * Reports where the change has reached, and on which node. Polled by the process driving the sequence.
	 *
	 * @param storageServer the storage cluster
	 * @param operation the operation returned by {@link #applyConfiguration}
	 * @return the operation with its current state and steps
	 */
	default ServiceResponse<StorageOperation> refreshConfiguration(StorageServer storageServer, StorageOperation operation) {
		return ServiceResponse.error("Configuration not supported");
	}
}
