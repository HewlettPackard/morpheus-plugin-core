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

package com.morpheusdata.core.admin;

import com.morpheusdata.core.MorpheusDataService;
import com.morpheusdata.core.MorpheusIdentityService;
import com.morpheusdata.model.AuditLog;
import com.morpheusdata.model.projection.AuditLogIdentityProjection;
import io.reactivex.rxjava3.core.Single;

/**
 * Provides a plugin-accessible interface for interacting with audit log entries.
 * Plugins may insert new audit log entries and query existing ones.
 * <p>
 * <strong>Note:</strong> Audit log entries cannot be modified or deleted via this service.
 * The {@link #save} and {@link #remove} operations are intentionally no-ops to preserve
 * the integrity of the audit trail.
 * </p>
 *
 * <p>Accessible via {@code morpheusContext.async.admin.getAuditLog()}.</p>
 */
public interface MorpheusAuditLogService extends MorpheusDataService<AuditLog, AuditLogIdentityProjection>, MorpheusIdentityService<AuditLogIdentityProjection> {

	/**
	 * Inserts a DEBUG-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to DEBUG)
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> logDebug(AuditLog auditLog);

	/**
	 * Inserts an INFO-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to INFO)
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> logInfo(AuditLog auditLog);

	/**
	 * Inserts a WARN-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to WARN)
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> logWarn(AuditLog auditLog);

	/**
	 * Inserts an ERROR-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to ERROR)
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> logError(AuditLog auditLog);

	/**
	 * Inserts a FATAL-level audit log entry.
	 * @param auditLog the entry to insert (level will be set to FATAL)
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> logFatal(AuditLog auditLog);

	/**
	 * Inserts an audit log entry at the specified level.
	 * @param level the log level (use {@link AuditLog} level constants: LEVEL_DEBUG, LEVEL_INFO, LEVEL_WARN, LEVEL_ERROR, LEVEL_FATAL)
	 * @param auditLog the entry to insert
	 * @return Single wrapping the saved entry
	 */
	Single<AuditLog> log(Integer level, AuditLog auditLog);
}
