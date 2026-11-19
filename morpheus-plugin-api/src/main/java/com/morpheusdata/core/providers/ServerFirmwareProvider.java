/*
 *  Copyright 2026 HPE Development Company, L.P.
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
import com.morpheusdata.model.ComputeServerGroup;
import com.morpheusdata.model.FirmwareBaseline;
import com.morpheusdata.model.FirmwareConformanceResult;
import com.morpheusdata.model.FirmwareInventory;
import com.morpheusdata.model.FirmwareStageResult;
import com.morpheusdata.model.UpdateDefinition;
import com.morpheusdata.model.UpdateOperation;
import com.morpheusdata.response.ServiceResponse;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Drives server firmware and driver updates: BIOS, BMC, NIC, storage controller.
 *
 * Registered against the clusters running on that vendor's hardware. A cluster
 * resolves to one firmware provider.
 *
 * It declares the same five update phases as the OS Update Provider, under firmware
 * names, so the orchestrator calls both the same way. Firmware runs either inside an OS update or on its own. In
 * both cases the orchestrator owns the maintenance window and calls the OS Update
 * Provider for the host lifecycle.
 *
 * The BareMetal plugin is the expected primary implementor, using iLO, iDRAC or
 * XClarity Redfish APIs. Work is out-of-band through the BMC, not through the
 * morpheus-agent.
 *
 * Services are reached through getMorpheus(). The existing BareMetal ComputeUpdateFacet
 * remains the entry point for a firmware update outside any cluster.
 */
public interface ServerFirmwareProvider extends PluginProvider {
	/** The cluster type codes this provider serves. A cluster resolves to one provider. */
	Collection<String> getSupportedClusterTypeCodes();

	/**
	 * Applies the firmware payload to a host. The same five phases as the OS Update
	 * Provider, named distinctly, so a cluster can carry both providers and a single
	 * plugin class can implement both.
	 */
	ServiceResponse<UpdateOperation> validateFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts);

	ServiceResponse<UpdateOperation> executeFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts);

	/** Performs post-update verification on the hosts in this update wave. */
	ServiceResponse<UpdateOperation> postFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts);

	/** Rolls back the firmware update on the hosts in this update wave. */
	ServiceResponse<UpdateOperation> rollbackFirmwareUpdate(UpdateDefinition update, ComputeServer... hosts);

	/** Poll a long-running operation. Not a rollback. */
	ServiceResponse<UpdateOperation> refreshFirmwareUpdate(UpdateOperation operation, ComputeServer... hosts);

	/** Collect firmware inventory from the host BMC: BIOS, BMC, NIC, storage controller. */
	ServiceResponse<FirmwareInventory> collectFirmwareInventory(ComputeServer host);

	/** List the baselines available to this cluster. Connected: the vendor catalog. Disconnected: uploaded bundles. */
	ServiceResponse<List<FirmwareBaseline>> getAvailableBaselines(ComputeServerGroup cluster);

	/** Upload and validate an offline firmware bundle, such as an SPP ISO. */
	ServiceResponse<FirmwareBaseline> uploadFirmwareBundle(ComputeServerGroup cluster, InputStream stream, String filename);

	/**
	 * Compare the host firmware inventory against the baseline, component by component.
	 * The orchestrator calls this before it opens a maintenance window, so a host that
	 * already matches the baseline is not taken out of service for nothing.
	 */
	ServiceResponse<FirmwareConformanceResult> checkFirmwareConformance(ComputeServer host, FirmwareBaseline baseline);

	/**
	 * Whether a firmware-only operation can run on this cluster, with no OS update.
	 * When false the UI does not offer one and firmware runs only alongside an OS update.
	 */
	default boolean supportsStandaloneUpdate() {
		return true;
	}

	/** Whether firmware can be staged before the maintenance window opens. */
	default boolean supportsStaging() {
		return false;
	}

	/** Whether applying firmware needs a host reboot. */
	default boolean requiresReboot() {
		return true;
	}

	/** Stage firmware on or near the host, before the window opens. iLO virtual media or the iLO Repository. */
	ServiceResponse<FirmwareStageResult> stageFirmware(ComputeServer host, UpdateDefinition update);

	/**
	 * Applying firmware is declared above. The host
	 * is already in maintenance mode and empty, and the OS update has already run when
	 * both are in play. The orchestrator reboots afterwards.
	 *
	 *   validateFirmwareUpdate()  with UpdateDefinition.OpType.DRY_RUN is the inventory
	 *                             scan that decides which components need work
	 *   executeFirmwareUpdate()   flashes the components
	 *   postFirmwareUpdate()      verifies the flashed versions
	 *   rollbackFirmwareUpdate()  reverts where the vendor supports it
	 *
	 * Progress is reported against the UpdateOperation the orchestrator opened.
	 */

	/** Collect firmware logs from the BMC. Called after the update, on success and on failure. */
	ServiceResponse<String> collectFirmwareLogs(ComputeServer host);
}
