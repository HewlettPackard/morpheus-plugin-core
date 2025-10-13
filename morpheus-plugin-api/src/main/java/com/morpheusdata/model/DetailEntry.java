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

/**
 * Represents a single detail entry that can be displayed in the Info section of a resource view.
 * This allows plugins to inject custom information into existing detail views without requiring
 * a full tab implementation.
 *
 * @author Morpheus Development Team
 * @since 1.2.13
 */
public class DetailEntry {

	/**
	 * The internationalized label key for this detail entry.
	 * This should be used first if i18n translation is available.
	 */
	public String i18nLabel;

	/**
	 * The fallback label to use if i18n translation is not available.
	 * This will be displayed as-is if i18nLabel cannot be resolved.
	 */
	public String fallbackLabel;

	/**
	 * The value to display for this detail entry.
	 * This will be rendered as a string in the UI.
	 */
	public String value;

	/**
	 * Default constructor
	 */
	public DetailEntry() {
	}

	/**
	 * Constructor with all fields
	 * @param i18nLabel The internationalized label key
	 * @param fallbackLabel The fallback label if i18n is unavailable
	 * @param value The value to display
	 */
	public DetailEntry(String i18nLabel, String fallbackLabel, String value) {
		this.i18nLabel = i18nLabel;
		this.fallbackLabel = fallbackLabel;
		this.value = value;
	}

	/**
	 * Constructor with fallback label and value only
	 * @param fallbackLabel The label to display
	 * @param value The value to display
	 */
	public DetailEntry(String fallbackLabel, String value) {
		this.fallbackLabel = fallbackLabel;
		this.value = value;
	}
}
