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

package com.morpheusdata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.core.providers.WizardProvider;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * Represents a single step in a configuration workflow. Each step is associated
 * with a wizard
 * for collecting configuration data through multiple forms.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class ConfigurationWorkflowStep extends MorpheusModel {

	protected String code;
	protected String name;
	protected String description;
	protected Boolean isRequired = true;
	protected Boolean completed = false;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected Wizard wizard;
	protected ConfigurationWorkflow configurationWorkflow;

	// Transient field for provider reference (not persisted to database)
	@JsonIgnore
	protected transient WizardProvider wizardProvider;

	/**
	 * Returns the unique code identifier for this configuration workflow step
	 * 
	 * @return step code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code identifier for this configuration workflow step
	 * 
	 * @param code step code
	 */
	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	/**
	 * Returns the display name for this configuration workflow step
	 * 
	 * @return step name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name for this configuration workflow step
	 * 
	 * @param name step name
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Returns a description of this configuration workflow step's purpose
	 * 
	 * @return step description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets a description of this configuration workflow step's purpose
	 * 
	 * @param description step description
	 */
	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	/**
	 * Returns whether this step is required
	 * 
	 * @return true if required, false otherwise
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets whether this step is required
	 * 
	 * @param isRequired true if required, false otherwise
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
		markDirty("isRequired", isRequired);
	}

	/**
	 * Returns whether this step has been completed
	 * 
	 * @return true if completed, false otherwise
	 */
	public Boolean getCompleted() {
		return completed;
	}

	/**
	 * Sets whether this step has been completed
	 * 
	 * @param completed true if completed, false otherwise
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
		markDirty("completed", completed);
	}

	/**
	 * Returns the wizard associated with this step.
	 * If a wizard provider is available, returns the wizard from the provider
	 * dynamically.
	 * Otherwise, returns the stored wizard object reference.
	 * 
	 * @return wizard
	 */
	public Wizard getWizard() {
		// If we have a provider, get the wizard from it dynamically
		if (wizardProvider != null) {
			return wizardProvider.getWizard();
		}
		// Otherwise return the stored wizard reference
		return wizard;
	}

	/**
	 * Sets the wizard associated with this step
	 * 
	 * @param wizard wizard
	 */
	public void setWizard(Wizard wizard) {
		this.wizard = wizard;
		markDirty("wizard", wizard);
	}

	/**
	 * Returns the configuration workflow this step belongs to
	 * 
	 * @return configuration workflow
	 */
	public ConfigurationWorkflow getConfigurationWorkflow() {
		return configurationWorkflow;
	}

	/**
	 * Sets the configuration workflow this step belongs to
	 * 
	 * @param configurationWorkflow configuration workflow
	 */
	public void setConfigurationWorkflow(ConfigurationWorkflow configurationWorkflow) {
		this.configurationWorkflow = configurationWorkflow;
		markDirty("configurationWorkflow", configurationWorkflow);
	}

	/**
	 * Returns the wizard provider for this step (transient, not persisted)
	 * 
	 * @return wizard provider
	 */
	@JsonIgnore
	public WizardProvider getWizardProvider() {
		return wizardProvider;
	}

	/**
	 * Sets the wizard provider for this step (transient, not persisted)
	 * 
	 * @param wizardProvider wizard provider
	 */
	@JsonIgnore
	public void setWizardProvider(WizardProvider wizardProvider) {
		this.wizardProvider = wizardProvider;
	}
}
