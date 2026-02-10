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

import com.morpheusdata.model.OptionType;
import com.morpheusdata.model.OptionTypeFieldGroup;
import com.morpheusdata.response.ServiceResponse;

import java.util.Collection;
import java.util.Map;

/**
 * Provides support for defining custom forms with validation and submission
 * logic.
 * This interface allows plugins to create custom forms with server-side
 * validation
 * and processing capabilities.
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public interface FormProvider extends PluginProvider {

	/**
	 * Returns a unique identifier for this form. This should be unique across all
	 * form providers
	 * to prevent conflicts.
	 * 
	 * @return unique form identifier
	 */
	String getFormKey();

	/**
	 * Returns the display name for this form
	 * 
	 * @return form display name
	 */
	String getFormName();

	/**
	 * Returns a description of this form's purpose
	 * 
	 * @return form description
	 */
	default String getFormDescription() {
		return null;
	}

	/**
	 * Provides the collection of OptionType fields that make up this form
	 * 
	 * @return Collection of OptionType inputs for the form
	 */
	Collection<OptionType> getFormFields();

	/**
	 * Provides the collection of OptionTypeFieldGroup objects that define
	 * groupings for form fields. Field groups allow organizing related fields
	 * together in the UI with collapsible sections and custom headings.
	 * 
	 * @return Collection of OptionTypeFieldGroup for the form, or null/empty if no
	 *         groups are defined
	 */
	default Collection<OptionTypeFieldGroup> getFormFieldGroups() {
		return null;
	}

	/**
	 * Validates the submitted form data before processing. This method should check
	 * all required fields, data formats, business rules, etc.
	 * 
	 * @param data the form data submitted by the user
	 * @param opts additional options or context for validation
	 * @return ServiceResponse containing validation results. If validation fails,
	 *         the response should contain error messages explaining what went
	 *         wrong.
	 */
	ServiceResponse validateForm(Map data, Map opts);

	/**
	 * Processes and submits the form data after successful validation.
	 * This method will only be called if validateForm() returns a successful
	 * response.
	 * 
	 * @param data the form data to process
	 * @param opts additional options or context for submission
	 * @return ServiceResponse containing the result of the form submission,
	 *         including any data that should be returned to the caller
	 */
	ServiceResponse submitForm(Map data, Map opts);

	/**
	 * Optional method to transform or prepare form data before validation.
	 * This can be used to normalize data, set defaults, or perform conversions.
	 * 
	 * @param data the raw form data
	 * @param opts additional options or context
	 * @return the prepared form data
	 */
	default Map prepareFormData(Map data, Map opts) {
		return data;
	}

}
