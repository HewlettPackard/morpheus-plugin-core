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

package com.morpheusdata.model;

import com.morpheusdata.model.projection.AuditLogIdentityProjection;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.morpheusdata.model.serializers.ModelAsIdOnlySerializer;

/**
 * Represents an audit log entry recording a significant action in Morpheus.
 * AuditLog entries can be inserted by plugins but cannot be modified or deleted
 * to preserve the integrity of the audit trail.
 */
public class AuditLog extends AuditLogIdentityProjection {

	public static final int LEVEL_DEBUG = 0;
	public static final int LEVEL_INFO = 1;
	public static final int LEVEL_WARN = 2;
	public static final int LEVEL_ERROR = 3;
	public static final int LEVEL_FATAL = 4;

	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected Account account;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected User user;
	@JsonSerialize(using = ModelAsIdOnlySerializer.class)
	protected User actualUser;
	protected String description;
	protected String logSignature;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		markDirty("account", account);
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		markDirty("user", user);
		this.user = user;
	}

	public User getActualUser() {
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		markDirty("actualUser", actualUser);
		this.actualUser = actualUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		markDirty("description", description);
		this.description = description;
	}

	public String getLogSignature() {
		return logSignature;
	}

	public void setLogSignature(String logSignature) {
		markDirty("logSignature", logSignature);
		this.logSignature = logSignature;
	}
}
