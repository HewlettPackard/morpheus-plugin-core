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

package com.morpheusdata.core.synchronous.compute;

import com.morpheusdata.core.MorpheusComputeServerService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.model.*;
import com.morpheusdata.model.projection.ComputeServerIdentityProjection;
import com.morpheusdata.response.ServiceResponse;
import io.reactivex.rxjava3.core.Single;

public interface MorpheusSynchronousComputeServerService extends MorpheusSynchronousDataService<ComputeServer, ComputeServerIdentityProjection>, MorpheusSynchronousIdentityService<ComputeServerIdentityProjection> {

	/**
	 * Returns the ComputeServerInterfaceContext used for performing updates or queries on {@link ComputeServerInterface} related assets within Morpheus.
	 * @return An instance of the ComputeServerInterface Context
	 */
	MorpheusSynchronousComputeServerInterfaceService getComputeServerInterface();

	/**
	 * Returns the ComputeDevice context used for performing sync operations on {@link ComputeDevice} related assets within Morpheus.
	 * @return An instance of the ComputeDevice context
	 */
	MorpheusSynchronousComputeDeviceService getComputeDevice();

	/**
	 * Returns the ComputePort context used for performing sync operations on {@link ComputePort} related assets within Morpheus.
	 * @return An instance of the ComputePort context
	 */
	MorpheusSynchronousComputePortService getComputePort();


	/**
	 * Returns the ComputePort context used for performing sync operations on {@link ComputeServerAccess} related assets within Morpheus.
	 * @return An instance of the ComputeServerAccess context
	 */
	MorpheusSynchronousComputeServerAccessService getAccess();

	ServiceResponse validateUpdate(UpdateDefinition updateDefinition, ComputeServer... computeServer);

	ServiceResponse executeUpdate(UpdateDefinition updateDefinition, ComputeServer... computeServer);

	ServiceResponse postUpdate(UpdateDefinition updateDefinition, ComputeServer... computeServer);

	ServiceResponse rollbackUpdate(UpdateDefinition updateDefinition, ComputeServer... computeServer);

	ServiceResponse refreshUpdate(ComputeServer... computeServer);

	ServiceResponse runConfigurationDriftCheck(CheckLevel checkLevel, ComputeServer... computeServer);

	ServiceResponse getConfigurationDriftDetails(ComputeServer... computeServer);

	/**
	 * Stop a ComputeServer. This is an async operation and the server may not be stopped immediately.
	 * @param computeServerId ComputeServer id to stop
	 * @return success if the request to stop the server was successful
	 */
	Single<Boolean> stopServer(Long computeServerId);

	/**
	 * Start a ComputeServer. This is an async operation and the server may not be started immediately.
	 * @param computeServerId ComputeServer id to start
	 * @return success if the request to start the server was successful
	 */
	Single<Boolean> startServer(Long computeServerId);

	/**
	 * Restart a ComputeServer. This is an async operation and the server may not be restarted immediately.
	 * @param computeServerId ComputeServer id to restart
	 * @return success if the request to restart the server was successful
	 */
	Single<Boolean> restartServer(Long computeServerId);

	/**
	 * Build a ComputeNetworkInterface from the provided parameters.
	 *
	 * @param account           Account
	 * @param instance          Instance
	 * @param server            ComputeServer
	 * @param networkInterfaceConfig  NetworkInterfaceConfig
	 * @return populated ComputeServerInterface
	 */
	ComputeServerInterface buildComputeServerInterface(Account account, Instance instance, ComputeServer server, MorpheusComputeServerService.NetworkInterfaceConfig networkInterfaceConfig);
}
