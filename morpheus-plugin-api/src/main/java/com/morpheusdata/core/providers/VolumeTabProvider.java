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

import com.morpheusdata.core.providers.UIExtensionProvider;
import com.morpheusdata.model.Account;
import com.morpheusdata.model.StorageVolume;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * Defines the contract for providing custom tabs within a Volume in Morpheus. This can be used to display
 * additional information about a volume, such as performance metrics, replication status, or other
 * provider-specific details. Implementations are responsible for rendering the tab content and determining
 * when the tab should be shown.
 * For a base implementation that integrates with the Morpheus UI rendering framework, see
 * {@code AbstractVolumeTabProvider}.
 *
 * @author David Christiansen
 * @since 0.15.4
 */
public interface VolumeTabProvider extends UIExtensionProvider {
	/**
	 * Volume details provided to your rendering engine
	 * @param volume details
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(StorageVolume volume);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated.
	 *
	 * @param volume Volume details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(StorageVolume volume, User user, Account account);
}
