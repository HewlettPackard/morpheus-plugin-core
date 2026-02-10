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

import java.util.Map;

/**
 * Provides an interface for registering custom input type JavaScript libraries.
 * This allows plugins to provide client-side JavaScript files that extend or
 * customize
 * input types in forms, wizards, and other UI components.
 * 
 * <p>
 * Input type libraries are loaded globally and can be used across multiple
 * forms
 * and wizards within the plugin.
 * </p>
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public interface InputTypeLibraryProvider extends UIExtensionProvider {

	/**
	 * Returns the relative path to the JavaScript library file within the plugin's
	 * assets directory. The path should start with a forward slash.
	 * 
	 * <p>
	 * <strong>Example:</strong>
	 * </p>
	 * 
	 * <pre>{@code @Override
	 * public String getLibraryScriptPath(Map<String, Object> opts) {
	 * 	return "/js/custom-input-types.js";
	 * }
	 * }</pre>
	 * 
	 * <p>
	 * The file should be placed in:
	 * {@code src/main/assets/js/custom-input-types.js}
	 * </p>
	 * 
	 * @param opts optional parameters (may include request context, user info,
	 *             etc.)
	 * @return the relative path to the JavaScript library file (e.g.,
	 *         "/js/custom-input-types.js")
	 */
	String getLibraryScriptPath(Map<String, Object> opts);

}
