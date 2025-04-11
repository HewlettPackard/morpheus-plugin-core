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

package com.morpheusdata.model.provisioning;

/**
 * Parameters for removing a workload
 *
 * @author Mike Carlin
 * @since 1.2.6
 */
public class RemoveWorkloadRequest {
	/**
	 * Whether the workload should be forcibly removed
	 * <p>
	 * If set to true, morpheus will remove the workload regardless of whether an error is returned or an exception
	 * is thrown.
	 */
	public boolean force;
}
