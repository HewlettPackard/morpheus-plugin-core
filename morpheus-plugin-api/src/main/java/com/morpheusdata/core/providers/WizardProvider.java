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

import com.morpheusdata.model.User;
import com.morpheusdata.model.Wizard;
import com.morpheusdata.model.WizardStep;
import com.morpheusdata.response.ServiceResponse;

import java.util.List;
import java.util.Map;

/**
 * Provides support for defining multi-step wizards with validation and final
 * submission logic.
 * Each wizard consists of multiple steps, where each step contains a form for
 * data collection.
 * Individual step forms handle their own validation, but final submission is
 * handled by the wizard itself.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public interface WizardProvider extends PluginProvider {

	/**
	 * Returns the display name for this wizard
	 * 
	 * @return wizard display name
	 */
	String getWizardName();

	/**
	 * Returns a description of this wizard's purpose
	 * 
	 * @return wizard description
	 */
	default String getWizardDescription() {
		return null;
	}

	/**
	 * Indicates whether a review step should be shown before final submission.
	 * When true, the wizard will display a review screen with data prepared by
	 * prepareReviewData() before calling submitWizard().
	 * 
	 * @return true to show the review step, false to skip directly to submission
	 */
	default boolean showReviewStep() {
		return true;
	}

	/**
	 * Returns a complete Wizard object representing this wizard provider.
	 * This method constructs a Wizard model from the provider's configuration,
	 * including the code, name, description, and steps.
	 * 
	 * @return Wizard object representing this wizard
	 */
	default Wizard getWizard() {
		Wizard wizard = new Wizard();
		wizard.setCode(getCode());
		wizard.setName(getWizardName());
		wizard.setDescription(getWizardDescription());
		wizard.setSteps(getWizardSteps());
		wizard.setActive(true);
		return wizard;
	}

	/**
	 * Determines whether the specified user has permission to access this wizard.
	 * This method allows providers to implement custom authorization logic based on
	 * user roles, permissions, or other criteria.
	 * 
	 * @param user the user to check access for
	 * @return true if the user can access this wizard, false otherwise
	 */
	default boolean canSee(User user) {
		return true;
	}

	/**
	 * Returns the ordered list of wizard steps that make up this wizard flow.
	 * The steps will be presented to the user in the order returned by this list.
	 * 
	 * @return List of WizardStep objects defining the wizard flow
	 */
	List<WizardStep> getWizardSteps();

	/**
	 * Prepares and returns data to be displayed on a review screen before final
	 * submission.
	 * This method allows the wizard to transform, summarize, or format the
	 * collected data
	 * for user review. The returned map should contain data that will be displayed
	 * to the user
	 * for confirmation before submitting the wizard.
	 * 
	 * @param wizardData map containing all data collected from all wizard steps,
	 *                   keyed by step code
	 * @param opts       additional options or context
	 * @return Map containing formatted data to display on the review screen
	 */
	default Map prepareReviewData(Map wizardData, Map opts) {
		return wizardData;
	}

	/**
	 * Validates all wizard data before final submission. This method can perform
	 * cross-step validation and business rule checks that span multiple steps.
	 * Individual step validation is handled by validateWizardStep(), but this
	 * method
	 * provides a final validation point for the complete wizard.
	 * 
	 * @param wizardData map containing all data collected from all wizard steps,
	 *                   keyed by step code
	 * @param opts       additional options or context
	 * @return ServiceResponse containing validation results. If validation fails,
	 *         the response should contain error messages explaining what went
	 *         wrong.
	 */
	ServiceResponse validateWizard(Map wizardData, Map opts);

	/**
	 * Validates the data submitted for a specific wizard step. This method is
	 * called
	 * when a user completes a step and before moving to the next step. It allows
	 * validation logic to consider the entire wizard state collected so far.
	 * 
	 * @param stepCode   the code of the step being validated
	 * @param stepData   map containing data submitted for this specific step
	 * @param wizardData map containing all data collected from previous steps,
	 *                   keyed by step code
	 * @param opts       additional options or context
	 * @return ServiceResponse containing validation results. If validation fails,
	 *         the response should contain error messages explaining what went
	 *         wrong.
	 */
	default ServiceResponse validateWizardStep(String stepCode, Map stepData, Map wizardData, Map opts) {
		// Default implementation - can be overridden for step-specific validation
		return ServiceResponse.success();
	}

	/**
	 * Processes and submits the completed wizard data after all steps have been
	 * completed
	 * and validated. This is the final submission point for the entire wizard
	 * flow.
	 * 
	 * @param wizardData map containing all data collected from all wizard steps,
	 *                   keyed by step code
	 * @param opts       additional options or context
	 * @return ServiceResponse containing the result of the wizard submission,
	 *         including any data that should be returned to the caller
	 */
	ServiceResponse submitWizard(Map wizardData, Map opts);

	/**
	 * Optional method to transform or prepare wizard data before validation.
	 * This can be used to merge data from multiple steps, set defaults, or perform
	 * conversions
	 * across the entire wizard dataset.
	 * 
	 * @param wizardData map containing all data collected from all wizard steps
	 * @param opts       additional options or context
	 * @return the prepared wizard data
	 */
	default Map prepareWizardData(Map wizardData, Map opts) {
		return wizardData;
	}

	/**
	 * Optional method called after successful wizard submission.
	 * Useful for cleanup, notifications, or triggering dependent actions after the
	 * wizard completes.
	 * 
	 * @param wizardData     map containing all data collected from all wizard steps
	 * @param submitResponse the response from submitWizard()
	 * @param opts           additional options or context
	 */
	default void afterSubmit(Map wizardData, ServiceResponse submitResponse, Map opts) {
		// Default implementation does nothing
	}

	/**
	 * Optional method to determine if a specific step should be shown based on
	 * data collected in previous steps. This allows for conditional wizard flows.
	 * 
	 * @param step             the wizard step to evaluate
	 * @param previousStepData map containing data from all previously completed
	 *                         steps
	 * @param opts             additional options or context
	 * @return true if the step should be shown, false to skip it
	 */
	default boolean shouldShowStep(WizardStep step, Map previousStepData, Map opts) {
		return true;
	}
}
