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

import com.morpheusdata.model.projection.PluginInfoIdentityProjection;

/**
 * Represents a registered plugin instance within the Morpheus appliance.
 * This model provides metadata about an installed plugin and is used by the
 * {@link com.morpheusdata.core.admin.MorpheusPluginService} for querying plugin availability.
 * @author David Estes
 * @since 1.4.0
 */
public class PluginInfo extends PluginInfoIdentityProjection {

	protected String version;
	protected String description;
	protected String author;
	protected Boolean enabled;
	protected String status;
	protected String statusMessage;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
		markDirty("version", version, this.version);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		markDirty("description", description, this.description);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		markDirty("author", author, this.author);
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
		markDirty("enabled", enabled, this.enabled);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		markDirty("status", status, this.status);
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		markDirty("statusMessage", statusMessage, this.statusMessage);
	}
}
