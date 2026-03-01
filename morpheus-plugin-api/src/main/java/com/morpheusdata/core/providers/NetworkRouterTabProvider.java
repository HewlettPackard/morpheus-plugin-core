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
import com.morpheusdata.model.NetworkRouter;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * This provider will render additional tabs for Network Router details (NetworkRouter). This can be useful when building
 * a custom network integration provider that has remote accessible controls for additional information about a router or other network device.
 *
 * @author Jordon Saardchit
 * @since 1.3.0
 */
public interface NetworkRouterTabProvider extends UIExtensionProvider {
	/**
	 * NetworkRouter details provided to your rendering engine
	 * @param networkRouter details
	 * @return result of rendering a template
	 */
	HTMLResponse renderTemplate(NetworkRouter networkRouter);

	/**
	 * Provide logic when tab should be displayed. This logic is checked after permissions are validated.
	 *
	 * @param networkRouter Network Router details
	 * @param user current User details
	 * @param account Account details
	 * @return whether the tab should be displayed
	 */
	Boolean show(NetworkRouter networkRouter, User user, Account account);
}

