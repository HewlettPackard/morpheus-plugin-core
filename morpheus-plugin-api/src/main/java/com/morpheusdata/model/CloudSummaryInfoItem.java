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
 * Represents an individual info item to be displayed in the Cloud Summary info section.
 * Each item consists of a label (name or i18n code) and a value.
 *
 * @author Saad Mansoor
 * @since 1.2.15
 */
public class CloudSummaryInfoItem {
	/**
	 * The display name for this info item. Used if {@code code} is not specified.
	 */
	protected String name;

	/**
	 * The i18n message code for this info item's label. If specified, this takes precedence over {@code name}.
	 * Example: "gomorpheus.label.apiVersion"
	 */
	protected String code;

	/**
	 * The value to display for this info item. This will be rendered as a string.
	 */
	protected String value;

	public CloudSummaryInfoItem() {
	}

	public CloudSummaryInfoItem(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public CloudSummaryInfoItem(String name, String code, String value) {
		this.name = name;
		this.code = code;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
