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

import java.util.List;

/**
 * Represents a multi-step wizard for collecting configuration data.
 * A wizard consists of multiple steps that guide users through a process,
 * collecting and validating data at each step.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class Wizard extends MorpheusModel implements IModelCodeName {

	protected String code;
	protected String name;
	protected String description;
	protected List<WizardStep> steps;
	protected Boolean active = true;

	public Wizard() {
	}

	public Wizard(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * Returns the unique code identifier for this wizard
	 * 
	 * @return wizard code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code identifier for this wizard
	 * 
	 * @param code wizard code
	 */
	public void setCode(String code) {
		this.code = code;
		markDirty("code", code);
	}

	/**
	 * Returns the display name for this wizard
	 * 
	 * @return wizard name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name for this wizard
	 * 
	 * @param name wizard name
	 */
	public void setName(String name) {
		this.name = name;
		markDirty("name", name);
	}

	/**
	 * Returns a description of this wizard's purpose
	 * 
	 * @return wizard description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets a description of this wizard's purpose
	 * 
	 * @param description wizard description
	 */
	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description);
	}

	/**
	 * Returns the ordered list of steps that make up this wizard
	 * 
	 * @return list of WizardStep
	 */
	public List<WizardStep> getSteps() {
		return steps;
	}

	/**
	 * Sets the ordered list of steps that make up this wizard
	 * 
	 * @param steps list of WizardStep
	 */
	public void setSteps(List<WizardStep> steps) {
		this.steps = steps;
		markDirty("steps", steps);
	}

	/**
	 * Returns whether this wizard is active
	 * 
	 * @return true if active, false otherwise
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets whether this wizard is active
	 * 
	 * @param active true to activate, false to deactivate
	 */
	public void setActive(Boolean active) {
		this.active = active;
		markDirty("active", active);
	}

}
