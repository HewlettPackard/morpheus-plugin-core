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

import com.morpheusdata.model.OptionType;
import com.morpheusdata.model.process.ProcessJobExecutionRequest;
import com.morpheusdata.model.process.ProcessJobExecutionResponse;
import com.morpheusdata.response.ServiceResponse;

import java.util.List;

/**
 * A {@link PluginProvider} that defines a unit of executable async work for a {@link com.morpheusdata.model.ProcessEvent}.
 * <p>
 * The provider runs against a single ProcessEvent — it receives the current step's inputs and returns success or
 * failure, plus optional {@code nextOpts} that get forwarded to the next step in the chain. The provider defines
 * what the step does. {@code MorpheusProcessService}, {@code ApplianceJobService}, and {@code PluginProcessJobService}
 * handle scheduling, dispatch, and progression.
 * <p>
 * When the plugin loads, Morpheus syncs the provider's metadata into a {@code ProcessJobType} domain row. The
 * provider's {@link #getCode()} becomes the identifier across the system: it is the {@code ProcessJobType.code}
 * and the value stored in {@code ProcessEvent.jobName}.
 *
 * @since 1.4.0
 * @author Sean Ridgley
 */
public interface ProcessJobProvider extends PluginProvider {

	/**
	 * A description of this process job provider. Used when syncing provider metadata into the
	 * ProcessJobType domain row.
	 *
	 * @return human-readable description of what this process job does
	 */
	String getDescription();

	/**
	 * Execute the process step.
	 *
	 * @param request contains the processEventId and the current step's merged opts
	 * @return success with optional {@link ProcessJobExecutionResponse#nextOpts} to forward to the next step,
	 *         or failure to trigger retry/onFail handling
	 */
	ServiceResponse<ProcessJobExecutionResponse> execute(ProcessJobExecutionRequest request);

	/**
	 * Called when a step fails terminally (retries exhausted or not retryable).
	 * Use for cleanup of side effects from this step. Exceptions are logged
	 * but do not change the outcome — the step is already failed.
	 * Not called on cancel.
	 *
	 * @param request the same request that was passed to {@link #execute(ProcessJobExecutionRequest)}
	 * @return a ServiceResponse (success/failure is logged but does not affect the outcome)
	 */
	default ServiceResponse onFail(ProcessJobExecutionRequest request) {
		return ServiceResponse.success();
	}

	/**
	 * Whether this step should be automatically retried on failure.
	 *
	 * @return true if retryable, false otherwise. Default is false.
	 */
	default Boolean isRetryable() {
		return false;
	}

	/**
	 * The maximum number of automatic retry attempts before the step fails terminally.
	 *
	 * @return retry count. Default is 5.
	 */
	default Integer getRetryCount() {
		return 5;
	}

	/**
	 * The delay in seconds between automatic retry attempts.
	 *
	 * @return delay in seconds. Default is 10.
	 */
	default Integer getRetryDelaySeconds() {
		return 10;
	}

	/**
	 * Whether this step can be canceled by a user. Canceling sets a flag on the process event;
	 * the plugin is responsible for checking the canceled flag during execution.
	 *
	 * @return true if cancelable, false otherwise. Default is false.
	 */
	default Boolean isCancelable() {
		return false;
	}

	/**
	 * Option types that define configurable inputs for this step. These are rendered in the retry
	 * dialog so users can modify inputs before manually retrying a failed step.
	 *
	 * @return list of OptionType definitions. Default is an empty list.
	 */
	default List<OptionType> getOptionTypes() {
		return List.of();
	}
}
