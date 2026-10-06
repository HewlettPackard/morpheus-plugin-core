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

package com.morpheusdata.response;

/**
 * Represents a single additional read-only field to be rendered on the Backup detail page in the Appliance UI.
 * Backup providers can use this to surface provider specific metadata (such as an SLA Domain name) alongside the
 * standard backup detail fields without requiring a full custom UI tab.
 *
 * @author Morpheus Data
 */
public class BackupDetailField {
	private String labelCode;
	private String label;
	private Object value;

	public BackupDetailField() {
	}

	public BackupDetailField(String labelCode, Object value) {
		this.labelCode = labelCode;
		this.value = value;
	}

	public BackupDetailField(String labelCode, String label, Object value) {
		this.labelCode = labelCode;
		this.label = label;
		this.value = value;
	}

	/**
	 * The i18n message code used to resolve the display label for this field.
	 * @return the label code
	 */
	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	/**
	 * An optional fallback display label to use when no translation exists for the {@link #getLabelCode()}.
	 * @return the fallback label
	 */
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * The value to be displayed for this field.
	 * @return the field value
	 */
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
