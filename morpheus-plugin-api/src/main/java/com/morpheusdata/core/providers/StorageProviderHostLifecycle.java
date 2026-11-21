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
import com.morpheusdata.model.HostLifecycleRequest;
import com.morpheusdata.model.HostLifecycleResult;
import com.morpheusdata.model.StorageServer;
import com.morpheusdata.response.ServiceResponse;

/**
 * Optional facet for a {@link StorageProvider} covering host lifecycle events that never enter
 * the OS update pipeline: maintenance mode, a reboot or shutdown issued through Morpheus, and
 * removal of a host from a cluster. Morpheus resolves the facet from the storage server the
 * host's cluster carries, so a host in no storage cluster calls nothing.
 * <p>
 * Every method defaults to a no-op. The {@code validate*} methods may refuse, by returning
 * {@code ServiceResponse.error} with a message the operator is shown;
 * {@link HostLifecycleResult#getOverridable()} says whether the operator may proceed anyway. The
 * remaining methods perform work and report health.
 *
 * @since 1.6.0
 * @see HostLifecycleRequest
 * @see HostLifecycleResult
 */
public interface StorageProviderHostLifecycle {

	/**
	 * Before a host enters maintenance mode, and before its virtual machines are evacuated.
	 * Refuse where the cluster cannot tolerate losing this node: insufficient FTT headroom, a
	 * rebuild already running, or controller quorum that would not survive the drain.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host entering maintenance mode
	 * @param request trigger and override detail
	 * @return a refusal, or a successful response to proceed
	 */
	default ServiceResponse<HostLifecycleResult> validateHostMaintenance(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * After the virtual machines are off the host. Hand off controller ownership and quiesce
	 * this node's storage role.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host entering maintenance mode
	 * @param request trigger and override detail
	 * @return the resulting health, or an error to surface to the operator
	 */
	default ServiceResponse<HostLifecycleResult> enterHostMaintenance(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * When maintenance ends. Rejoin the storage cluster and wait for the resync. Cannot refuse;
	 * reports health as resyncing until the node is whole again.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host leaving maintenance mode
	 * @param request trigger and override detail
	 * @return the resulting health
	 */
	default ServiceResponse<HostLifecycleResult> exitHostMaintenance(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * Before a reboot or shutdown issued through Morpheus. Refuse where losing the node would
	 * take the storage cluster below quorum.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host being rebooted or shut down
	 * @param request trigger and override detail
	 * @return a refusal, or a successful response to proceed
	 */
	default ServiceResponse<HostLifecycleResult> validateHostPowerOff(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * After the check passes and before power is cut. Flush and stop the containers cleanly, so
	 * the array sees a planned departure rather than a node failure.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host being rebooted or shut down
	 * @param request trigger and override detail
	 * @return the resulting health, or an error to surface to the operator
	 */
	default ServiceResponse<HostLifecycleResult> prepareHostPowerOff(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * When the host is running again. Restart the containers, rejoin the cluster, and report
	 * health as the resync progresses.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host that powered back on
	 * @param request trigger and override detail
	 * @return the resulting health
	 */
	default ServiceResponse<HostLifecycleResult> resumeHostAfterPowerOn(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * Before a host is removed from a cluster. Refuse while the node still holds array data that
	 * has not been migrated elsewhere.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host being removed
	 * @param request trigger and override detail
	 * @return a refusal, or a successful response to proceed
	 */
	default ServiceResponse<HostLifecycleResult> validateHostRemoval(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * After the check passes. Remove the node from the storage cluster and release the drives it
	 * claimed, so a future node can use them.
	 *
	 * @param storageServer the storage system the host's cluster carries
	 * @param host the host being removed
	 * @param request trigger and override detail
	 * @return the resulting health, or an error to surface to the operator
	 */
	default ServiceResponse<HostLifecycleResult> releaseHost(StorageServer storageServer, ComputeServer host, HostLifecycleRequest request) {
		return ServiceResponse.success();
	}
}
