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

package com.morpheusdata.core.providers;

import com.morpheusdata.model.Cloud;
import com.morpheusdata.model.User;
import com.morpheusdata.views.HTMLResponse;

/**
 * Provides a custom UI renderer for the Costing Summary section in the Cloud -> Summary tab.
 * This allows cloud plugins to render custom costing information specific to their cloud provider.
 *
 * <p>Example use cases:</p>
 * <ul>
 *   <li>Display AWS service usage costs breakdown</li>
 *   <li>Show Azure cost analysis charts</li>
 *   <li>Display custom billing/invoice information</li>
 * </ul>
 *
 * @author Saad Mansoor
 * @since 1.2.15
 */
public interface CloudCostingSummaryProvider extends UIExtensionProvider {

	/**
	 * Renders the costing summary content for the specified cloud.
	 * This method is called when the Cloud -> Summary tab is displayed for clouds with costing enabled.
	 *
	 * @param cloud the cloud for which to render the costing summary
	 * @param user the current user viewing the summary
	 * @param data optional data passed from the CloudProvider's getCloudSummary method
	 * @return HTMLResponse containing the rendered costing summary content
	 */
	HTMLResponse renderCostingSummary(Cloud cloud, User user, Object data);
}

