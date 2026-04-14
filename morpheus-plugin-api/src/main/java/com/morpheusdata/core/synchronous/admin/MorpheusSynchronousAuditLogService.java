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

package com.morpheusdata.core.synchronous.admin;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.core.MorpheusSynchronousIdentityService;
import com.morpheusdata.model.AuditLog;
import com.morpheusdata.model.projection.AuditLogIdentityProjection;

/**
 * Provides synchronous plugin-accessible access to audit log entries.
 * Plugins may insert new entries and query existing ones.
 * <p>
 * <strong>Note:</strong> Audit log entries cannot be modified or deleted to preserve
 * the integrity of the audit trail.
 * </p>
 *
 * <p>Accessible via {@code morpheusContext.services.admin.getAuditLog()}.</p>
 */
public interface MorpheusSynchronousAuditLogService extends MorpheusSynchronousDataService<AuditLog, AuditLogIdentityProjection>, MorpheusSynchronousIdentityService<AuditLogIdentityProjection> {

	/**
	 * Inserts a DEBUG-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to DEBUG)
	 * @return the saved entry
	 */
	AuditLog logDebug(AuditLog auditLog);

	/**
	 * Inserts an INFO-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to INFO)
	 * @return the saved entry
	 */
	AuditLog logInfo(AuditLog auditLog);

	/**
	 * Inserts a WARN-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to WARN)
	 * @return the saved entry
	 */
	AuditLog logWarn(AuditLog auditLog);

	/**
	 * Inserts an ERROR-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to ERROR)
	 * @return the saved entry
	 */
	AuditLog logError(AuditLog auditLog);

	/**
	 * Inserts a FATAL-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to FATAL)
	 * @return the saved entry
	 */
	AuditLog logFatal(AuditLog auditLog);

	/**
	 * Inserts an audit log entry at the specified level.
	 * @param level the log level (use {@link AuditLog} level constants: LEVEL_DEBUG, LEVEL_INFO, LEVEL_WARN, LEVEL_ERROR, LEVEL_FATAL)
	 * @param auditLog the entry to insert
	 * @return the saved entry
	 */
	AuditLog log(Integer level, AuditLog auditLog);
}
