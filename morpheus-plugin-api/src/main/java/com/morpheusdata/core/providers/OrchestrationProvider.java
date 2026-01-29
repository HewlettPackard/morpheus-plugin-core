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

package com.morpheusdata.core.providers;

import com.morpheusdata.response.ServiceResponse;

import java.util.List;
import java.util.Map;

/**
 * Provides support for defining complex multi-step orchestration workflows.
 * Each orchestration consists of multiple steps, where each step contains a
 * wizard for configuration.
 * The orchestrator maintains state across all steps and coordinates with a
 * parent object to persist
 * the orchestration state. Final submission triggers a long-running process
 * that executes the
 * orchestrated workflow.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public interface OrchestrationProvider extends PluginProvider {

	/**
	 * Returns a unique identifier for this orchestration. This should be unique
	 * across all orchestration providers to prevent conflicts.
	 * 
	 * @return unique orchestration identifier
	 */
	String getOrchestrationKey();

	/**
	 * Returns the display name for this orchestration
	 * 
	 * @return orchestration display name
	 */
	String getOrchestrationName();

	/**
	 * Returns a description of this orchestration's purpose
	 * 
	 * @return orchestration description
	 */
	default String getOrchestrationDescription() {
		return null;
	}

	/**
	 * Returns the ordered list of orchestration steps that make up this workflow.
	 * The steps will be presented to the user in the order returned by this list.
	 * 
	 * @return List of OrchestrationStep objects defining the orchestration flow
	 */
	List<OrchestrationStep> getOrchestrationSteps();

	/**
	 * Saves the configuration from a completed step to the orchestration state.
	 * This method is called after each step's wizard is successfully completed,
	 * allowing the orchestration to accumulate configuration across all steps.
	 * 
	 * @param stepCode     the code of the step that was completed
	 * @param stepData     the configuration data collected from the step's wizard
	 * @param currentState the current orchestration state before this update
	 * @param opts         additional options or context
	 * @return ServiceResponse containing the updated orchestration state
	 */
	ServiceResponse saveStepConfiguration(String stepCode, Map stepData, Map currentState, Map opts);

	/**
	 * Updates the parent object with the current orchestration state.
	 * This method is called after each step configuration is saved, allowing
	 * the parent object to persist and track the orchestration progress.
	 * 
	 * @param parentObject       the parent object that holds the orchestration
	 *                           state
	 * @param orchestrationState the current complete state of the orchestration
	 * @param opts               additional options or context
	 * @return ServiceResponse indicating success or failure of the update
	 */
	ServiceResponse updateParentState(Object parentObject, Map orchestrationState, Map opts);

	/**
	 * Validates the complete orchestration state before final submission.
	 * This method performs cross-step validation and business rule checks that
	 * span multiple orchestration steps.
	 * 
	 * @param orchestrationState map containing all configuration data from all
	 *                           steps, keyed by step code
	 * @param parentObject       the parent object that holds the orchestration
	 *                           state
	 * @param opts               additional options or context
	 * @return ServiceResponse containing validation results. If validation fails,
	 *         the response should contain error messages explaining what went
	 *         wrong.
	 */
	ServiceResponse validateOrchestration(Map orchestrationState, Object parentObject, Map opts);

	/**
	 * Submits and executes the orchestration workflow.
	 * This method is called after all steps are completed and validated.
	 * It should initiate the actual execution of the orchestrated workflow.
	 * 
	 * Since this method typically triggers another long-running process or method,
	 * it returns a synchronous ServiceResponse. The long-running execution should
	 * be handled by the method this calls.
	 * 
	 * @param orchestrationState map containing all configuration data from all
	 *                           steps
	 * @param parentObject       the parent object that holds the orchestration
	 *                           state
	 * @param opts               additional options or context
	 * @return ServiceResponse containing the result of the orchestration submission
	 */
	ServiceResponse submitOrchestration(Map orchestrationState, Object parentObject, Map opts);

	/**
	 * Optional method to retrieve the current orchestration state from the parent
	 * object.
	 * This is useful for resuming an orchestration or reviewing its current
	 * progress.
	 * 
	 * @param parentObject the parent object that holds the orchestration state
	 * @param opts         additional options or context
	 * @return Map containing the current orchestration state
	 */
	default Map getOrchestrationState(Object parentObject, Map opts) {
		return null;
	}

	/**
	 * Optional method to determine if a specific step should be shown based on
	 * the current orchestration state. This allows for conditional orchestration
	 * flows.
	 * 
	 * @param step               the orchestration step to evaluate
	 * @param orchestrationState map containing the current state from all
	 *                           previously
	 *                           completed steps
	 * @param opts               additional options or context
	 * @return true if the step should be shown, false to skip it
	 */
	default boolean shouldShowStep(OrchestrationStep step, Map orchestrationState, Map opts) {
		return true;
	}

	/**
	 * Optional method called after successful orchestration submission.
	 * Useful for cleanup, notifications, or triggering dependent actions after the
	 * orchestration process completes.
	 * 
	 * @param orchestrationState map containing all configuration data from all
	 *                           steps
	 * @param submitResponse     the response from submitOrchestration()
	 * @param parentObject       the parent object that holds the orchestration
	 *                           state
	 * @param opts               additional options or context
	 */
	default void afterSubmit(Map orchestrationState, ServiceResponse submitResponse, Object parentObject, Map opts) {
		// Default implementation does nothing
	}

	/**
	 * Optional method to cancel or abort a running orchestration process.
	 * 
	 * @param orchestrationState map containing the current orchestration state
	 * @param parentObject       the parent object that holds the orchestration
	 *                           state
	 * @param opts               additional options or context
	 * @return ServiceResponse indicating success or failure of the cancellation
	 */
	default ServiceResponse cancelOrchestration(Map orchestrationState, Object parentObject, Map opts) {
		return ServiceResponse.error("Cancellation not supported");
	}
}
