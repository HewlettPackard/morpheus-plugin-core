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

package com.morpheusdata.model;

import com.bertramlabs.plugins.karman.Directory;
import java.util.HashMap;
import java.util.Map;

/**
 * Request object for generating support bundle contents.
 * Contains the bundle and directory for placing support bundle files.
 *
 * @author Mike Carlin
 * @since 1.4.0
 */
public class GenerateSupportBundleContentsRequest {

	private SupportBundle bundle;
	private Directory contentsDir;
	private Map<String, Object> resourceInfo = new HashMap<>();

	public SupportBundle getBundle() {
		return bundle;
	}

	public void setBundle(SupportBundle bundle) {
		this.bundle = bundle;
	}

	public Directory getContentsDir() {
		return contentsDir;
	}

	public void setContentsDir(Directory contentsDir) {
		this.contentsDir = contentsDir;
	}

	/**
	 * Get the resource information map. This map is populated by Morpheus with
	 * basic resource information and can be augmented by the plugin provider.
	 * The augmented map will be written to the info JSON file by Morpheus.
	 *
	 * @return the resource information map
	 */
	public Map<String, Object> getResourceInfo() {
		return resourceInfo;
	}

	/**
	 * Set the resource information map. Plugins can add or modify entries in this map
	 * to include additional information in the final info JSON file.
	 *
	 * @param resourceInfo the resource information map
	 */
	public void setResourceInfo(Map<String, Object> resourceInfo) {
		this.resourceInfo = resourceInfo;
	}
}
