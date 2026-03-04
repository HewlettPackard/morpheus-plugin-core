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

package com.morpheusdata.core.synchronous.admin;

import com.morpheusdata.response.ServiceResponse;

import java.io.InputStream;

/**
 * Synchronous counterpart to {@link com.morpheusdata.core.admin.MorpheusPackageService}.
 * Provides plugin-accessible methods to download and install Morpheus package files (.morpkg).
 *
 * <p>Accessible via {@code morpheusContext.services.admin.getPackage()}.</p>
 */
public interface MorpheusSynchronousPackageService {

	/**
	 * Downloads a Morpheus package file from the given URL and installs it.
	 *
	 * @param url   the URL of the package file to download and install
	 * @param force if {@code true}, reinstalls the package even if it is already installed
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse downloadAndInstall(String url, Boolean force);

	/**
	 * Installs a Morpheus package from an InputStream.
	 *
	 * @param stream   the input stream of the package file contents
	 * @param filename the filename of the package (e.g. {@code "mypackage.morpkg"})
	 * @param force    if {@code true}, reinstalls the package even if it is already installed
	 * @return a {@link ServiceResponse} indicating success or failure
	 */
	ServiceResponse install(InputStream stream, String filename, Boolean force);
}
