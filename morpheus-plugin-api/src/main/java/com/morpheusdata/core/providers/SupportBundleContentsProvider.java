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

import com.morpheusdata.model.GenerateSupportBundleContentsRequest;
import com.morpheusdata.model.GenerateSupportBundleContentsResponse;
import com.morpheusdata.model.SupportBundleCategory;
import com.morpheusdata.response.ServiceResponse;

/**
 * A standalone support bundle content provider. Implement this interface to add custom content
 * to a support bundle — for example, appliance logs, health snapshots, or any other diagnostic
 * data that is not tied to a specific cloud, cluster, or other resource type.
 *
 * <p>When registered, Morpheus creates a {@code SupportBundleContentType} row (standalone kind,
 * {@code refType=null}, {@code refId=null}) keyed by {@link #getCode()} with the category from
 * {@link #getCategory()}, name from {@link #getSupportBundleName()}, and description from
 * {@link #getSupportBundleDescription()}. The row's {@code serviceBean} is set to
 * {@code "pluginSupportBundleContentsService"}, the single Morpheus-internal dispatcher that
 * routes generate requests to the correct registered provider by code.
 *
 * <p>To include this provider's content in a bundle, the API caller includes a content entry
 * with {@code "type": "&lt;your-code&gt;"} in the generate request.
 *
 * @since 1.4.0
 * @author Mike Carlin
 */
public interface SupportBundleContentsProvider extends PluginProvider {

	/**
	 * Returns the UI grouping category for this content type. Used by
	 * {@code GET /api/support-bundles/content-types} to group entries in the picker and as the
	 * lowercased directory prefix inside the generated bundle archive.
	 *
	 * @return the category; must not be null
	 */
	SupportBundleCategory getCategory();

	/**
	 * Returns the human-readable display name for this support bundle content type. Shown as
	 * the component label in the bundle picker and in CLI/API output. Defaults to
	 * {@link PluginProvider#getName()} so simple providers need not override it.
	 *
	 * @return the display name; must not be null
	 */
	default String getSupportBundleName() {
		return getName();
	}

	/**
	 * Returns an optional human-readable description of what this content type contributes to
	 * the support bundle. Shown in tooltips and detail views in the CLI and UI.
	 *
	 * @return the description string, or null if none
	 */
	default String getSupportBundleDescription() {
		return null;
	}

	/**
	 * Generate the support bundle contents for this provider. Morpheus calls this when a bundle
	 * generate request includes a component whose {@code type} matches {@link #getCode()}.
	 *
	 * <p>Write any files into {@code request.contentsDir}. Augment {@code request.resourceInfo}
	 * with any key/value pairs that should appear in the generated {@code info.json} for this
	 * component.
	 *
	 * @param request request containing the bundle record, output directory, and resource info map
	 * @return a ServiceResponse indicating success or failure
	 */
	default ServiceResponse<GenerateSupportBundleContentsResponse> generateSupportBundleContents(
			GenerateSupportBundleContentsRequest request) {
		return ServiceResponse.success(new GenerateSupportBundleContentsResponse());
	}
}
