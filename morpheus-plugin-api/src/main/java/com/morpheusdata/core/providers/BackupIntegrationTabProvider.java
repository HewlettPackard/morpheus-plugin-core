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

import com.morpheusdata.core.providers.UIExtensionProvider;
import com.morpheusdata.model.Account;
import com.morpheusdata.model.BackupProvider;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * Renders tabs within a backup integration in Morpheus. Implementing this provider will add a tab to the integration
 * detail page for the associated backup provider.
 * a backup provider integration.
 *
 * @author Dustin DeYoung
 * @since 1.2.6
 */
public interface BackupIntegrationTabProvider extends UIExtensionProvider {
	/**
	 * BackupProvider details provided to the rendering engine
	 * @param backupProvider details
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(BackupProvider backupProvider);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated. To only show this
	 * tab on a particular backup provider, check the code of the backup provider type:
	 * <pre>
	 * {@code
	 * return backupProvider.type.code == new MyBackupProvider().getCode()
	 * }
	 * </pre>
	 * alternatively use a constant for a cleaner implementation:
	 * <pre>
	 * {@code
	 * return backupProvider.type.code == MyBackupProvider.BACKUP_PROVIDER_CODE
	 * }
	 * </pre>
	 *
	 * @param backupProvider integration backup provider details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(BackupProvider backupProvider, User user, Account account);
}
