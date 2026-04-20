/*
 *  Copyright 2025 Morpheus Data, LLC.
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

package com.morpheusdata.model.projection;

import java.util.Date;

/**
 * Provides a subset of properties from the {@link com.morpheusdata.model.AuditLog}
 * for comparison with less bandwidth usage and memory footprint. This is a DTO Projection object.
 */
public class AuditLogIdentityProjection extends MorpheusIdentityModel {
	protected String eventType;
	protected Integer level;
	protected String objectClass;
	protected String objectId;
	protected Date dateCreated;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
		markDirty("eventType", eventType, this.eventType);
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
		markDirty("level", level, this.level);
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
		markDirty("objectClass", objectClass, this.objectClass);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
		markDirty("objectId", objectId, this.objectId);
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
		markDirty("dateCreated", dateCreated, this.dateCreated);
	}
}
