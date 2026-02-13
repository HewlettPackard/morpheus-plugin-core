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
 * Abstract implementation of {@link InputTypeLibraryProvider} that provides
 * common functionality for loading JavaScript library files.
 * 
 * <p>
 * This class handles the automatic resolution of script paths to the plugin's
 * asset directory structure.
 * </p>
 * 
 * <p>
 * <strong>Example Implementation:</strong>
 * </p>
 * 
 * <pre>{@code
 * public class CustomInputTypeLibraryProvider extends AbstractInputTypeLibraryProvider {
 *
 * 	@Override
 * 	public String getCode() {
 * 		return "custom-input-types";
 * 	}
 *
 * 	@Override
 * 	public String getName() {
 * 		return "Custom Input Types Library";
 * 	}
 *
 * 	@Override
 * 	public String getLibraryScriptPath(Map<String, Object> opts) {
 * 		return "/js/custom-input-types.js";
 * 	}
 * }
 * }</pre>
 * 
 * @author Andy Warner
 * @since 1.2.6
 */
public abstract class AbstractInputTypeLibraryProvider implements InputTypeLibraryProvider {

	/**
	 * Returns the full URL path to the library script, including the plugin asset
	 * prefix.
	 * This method automatically prepends the plugin asset path to the relative
	 * script path.
	 * 
	 * <p>
	 * For example, if the plugin name is "My Plugin" and the library script path is
	 * {@code "/js/custom-input-types.js"}, this method returns:
	 * </p>
	 * <p>
	 * {@code "/assets/plugin/my-plugin/js/custom-input-types.js"}
	 * </p>
	 * 
	 * @param opts optional parameters
	 * @return the full URL path to the library script
	 */
	public String getResolvedLibraryScriptPath(Map<String, Object> opts) {
		String scriptPath = getLibraryScriptPath(opts);
		if (scriptPath != null) {
			if (!scriptPath.startsWith("/")) {
				scriptPath = "/" + scriptPath;
			}
			String pluginName = getPlugin().getName().toLowerCase().replace(" ", "-");
			return "/assets/plugin/" + pluginName + scriptPath;
		}
		return null;
	}

}
