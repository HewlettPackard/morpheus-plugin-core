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
 * Represents a single step in a wizard flow. Each step contains a form
 * provider for collecting data and validation.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public class WizardStep {

	private String code;
	private String name;
	private String description;
	private FormProvider form;
	private Integer displayOrder;

	public WizardStep() {
	}

	public WizardStep(String code, String name, FormProvider form) {
		this.code = code;
		this.name = name;
		this.form = form;
	}

	/**
	 * Returns the unique code identifier for this wizard step
	 * 
	 * @return step code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code identifier for this wizard step
	 * 
	 * @param code step code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns the display name for this wizard step
	 * 
	 * @return step name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the display name for this wizard step
	 * 
	 * @param name step name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a description of this wizard step's purpose
	 * 
	 * @return step description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets a description of this wizard step's purpose
	 * 
	 * @param description step description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the form provider that handles data collection and validation for
	 * this step
	 * 
	 * @return FormProvider instance
	 */
	public FormProvider getForm() {
		return form;
	}

	/**
	 * Sets the form provider that handles data collection and validation for this
	 * step
	 * 
	 * @param form FormProvider instance
	 */
	public void setForm(FormProvider form) {
		this.form = form;
	}

	/**
	 * Returns the display order for this step in the wizard sequence
	 * 
	 * @return display order
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * Sets the display order for this step in the wizard sequence
	 * 
	 * @param displayOrder display order
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

}
