/*
 *  Copyright 2026 Morpheus Data, LLC.
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

import com.morpheusdata.model.Account;
import com.morpheusdata.model.Backup;
import com.morpheusdata.model.DetailEntry;
import com.morpheusdata.model.User;

import java.util.List;

/**
 * Adds rows to the existing Backup Info section.
 * <p>
 * Implement this provider when a backup plugin needs to show extra fields next to the standard
 * backup details, without adding a tab or replacing the backup view. Morpheus appends the returned
 * {@link DetailEntry} rows on the instance backup views, the backup detail info section, and the
 * backup API detail payload ({@code backupInfoFields}). Requires Morpheus UI 9.0.2 or later.
 * </p>
 * <p>
 * Register the provider from {@code Plugin.initialize()}:
 * </p>
 * <pre>
 * this.registerProvider(new MyBackupInfoProvider(this, this.morpheus));
 * </pre>
 * <p>
 * {@link #show(Backup, User, Account)} is called after Morpheus has already checked permissions.
 * Return {@code true} only for backups this plugin owns, typically by comparing
 * {@code backup.getBackupProvider().getType().getCode()} to the provider type code.
 * </p>
 * <p>
 * Each {@link DetailEntry} is one label/value row. {@code i18nLabel} is tried first as a message
 * key. {@code fallbackLabel} is shown when that key is missing. Rows with a null or blank
 * {@code value} are skipped. A failure in one provider is logged and does not hide the rest of
 * the info section.
 * </p>
 * <p>Example:</p>
 * <pre>
 * public class MyBackupInfoProvider implements BackupResourceViewUIFacet {
 *
 *     private final Plugin plugin;
 *     private final MorpheusContext morpheus;
 *
 *     public MyBackupInfoProvider(Plugin plugin, MorpheusContext morpheus) {
 *         this.plugin = plugin;
 *         this.morpheus = morpheus;
 *     }
 *
 *     &#64;Override
 *     public Boolean show(Backup backup, User user, Account account) {
 *         return backup.getBackupProvider() != null
 *             &amp;&amp; backup.getBackupProvider().getType() != null
 *             &amp;&amp; "my-backup-provider".equals(backup.getBackupProvider().getType().getCode());
 *     }
 *
 *     &#64;Override
 *     public List&lt;DetailEntry&gt; getDetailViewInfo(Backup backup) {
 *         String policyName = resolvePolicyName(backup);
 *         if (policyName != null &amp;&amp; !policyName.isBlank()) {
 *             return List.of(new DetailEntry("gomorpheus.label.policy", "Policy", policyName));
 *         }
 *         return List.of(new DetailEntry("gomorpheus.label.policy", "Policy", "Manual"));
 *     }
 *
 *     // also implement getCode(), getName(), getPlugin(), and getMorpheus()
 * }
 * </pre>
 *
 * @see ResourceViewUIFacet
 * @see DetailEntry
 * @author Morpheus Development Team
 * @since 1.5.2
 */
public interface BackupResourceViewUIFacet extends ResourceViewUIFacet<Backup> {

	/**
	 * Custom backup rows to render in the info section.
	 * <p>
	 * Return one {@link DetailEntry} per row. Use an existing message key for {@code i18nLabel}
	 * when one fits, and always set {@code fallbackLabel}. Do not return entries with a null or
	 * blank value; Morpheus skips them.
	 * </p>
	 * @param backup the backup being displayed
	 * @return rows to append to the backup info section. An empty list adds nothing.
	 */
	@Override
	List<DetailEntry> getDetailViewInfo(Backup backup);

	/**
	 * Whether this provider's rows should be displayed for the backup.
	 * <p>
	 * Morpheus checks permissions before calling this method. Limit the provider to backups it
	 * owns, for example by matching {@code backup.getBackupProvider().getType().getCode()}.
	 * </p>
	 * @param backup the backup being displayed
	 * @param user the current user
	 * @param account the current account
	 * @return {@code true} to call {@link #getDetailViewInfo(Backup)} and render the rows
	 */
	@Override
	Boolean show(Backup backup, User user, Account account);
}
