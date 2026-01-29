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

/**
 * Represents a single step in an orchestration flow. Each step contains a
 * wizard provider for collecting configuration data through multiple forms.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class OrchestrationStep {

	private String code;
	private String name;
	private String description;
	private WizardProvider wizard;
	private Integer displayOrder;
	private Boolean skipable;
	private Boolean completed;

	public OrchestrationStep() {
	}

	public OrchestrationStep(String code, String name, WizardProvider wizard) {
		this.code = code;
		this.name = name;
		this.wizard = wizard;
	}

	/**
	 * Returns the unique code identifier for this orchestration step
	 * 
	 * @return step code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code identifier for this orchestration step
	 * 
	 * @param code step code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the display name for this orchestration step
	 * 
	 * @return step name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name for this orchestration step
	 * 
	 * @param name step name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a description of this orchestration step's purpose
	 * 
	 * @return step description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets a description of this orchestration step's purpose
	 * 
	 * @param description step description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the wizard provider that handles multi-step data collection and
	 * validation for this orchestration step
	 * 
	 * @return WizardProvider instance
	 */
	public WizardProvider getWizard() {
		return wizard;
	}

	/**
	 * Sets the wizard provider that handles multi-step data collection and
	 * validation for this orchestration step
	 * 
	 * @param wizard WizardProvider instance
	 */
	public void setWizard(WizardProvider wizard) {
		this.wizard = wizard;
	}

	/**
	 * Returns the display order for this step in the orchestration sequence
	 * 
	 * @return display order
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * Sets the display order for this step in the orchestration sequence
	 * 
	 * @param displayOrder display order
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * Returns whether this step can be skipped by the user
	 * 
	 * @return true if step is skipable
	 */
	public Boolean getSkipable() {
		return skipable;
	}

	/**
	 * Sets whether this step can be skipped by the user
	 * 
	 * @param skipable true if step is skipable
	 */
	public void setSkipable(Boolean skipable) {
		this.skipable = skipable;
	}

	/**
	 * Returns whether this step has been completed
	 * 
	 * @return true if step is completed
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * Sets whether this step has been completed
	 * 
	 * @param completed true if step is completed
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}
