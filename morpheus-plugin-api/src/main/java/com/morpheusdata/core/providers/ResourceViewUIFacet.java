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

import com.morpheusdata.model.*;
import java.util.List;

/**
 * Provides support for custom detail entries in resource Info sections.
 * This interface allows plugins to inject custom information into existing
 * detail views without requiring a full tab implementation.
 *
 * @param <T> The type of MorpheusModel this facet applies to
 * @author Morpheus Development Team
 * @since 0.16.0
 */
public interface ResourceViewUIFacet<T extends MorpheusModel> extends PluginProvider {

	/**
	 * Get custom details from the plugin to render in the relevant info section
	 * @param model The model instance to get details for
	 * @return List of DetailEntry objects to display in the Info section
	 */
	List<DetailEntry> getDetailViewInfo(T model);

	/**
	 * Provide logic to determine when these details should be displayed.
	 * This logic is checked after permissions are validated.
	 *
	 * @param model The model instance
	 * @param user Current User details
	 * @param account Account details
	 * @return whether the details should be displayed
	 */
	Boolean show(T model, User user, Account account);
}
