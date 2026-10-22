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

package com.morpheusdata.core;

import java.util.Objects;

/**
 * A required plugin and its supported version range.
 */
public final class PluginDependency {

	private final String code;
	private final String minVersion;
	private final String maxVersion;

	public PluginDependency(String code, String minVersion, String maxVersion) {
		this.code = code;
		this.minVersion = minVersion;
		this.maxVersion = maxVersion;
	}

	public String getCode() {
		return code;
	}

	/**
	 * Returns the inclusive minimum required version.
	 */
	public String getMinVersion() {
		return minVersion;
	}

	/**
	 * Returns the exclusive maximum supported version.
	 */
	public String getMaxVersion() {
		return maxVersion;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PluginDependency)) {
			return false;
		}
		PluginDependency that = (PluginDependency) o;
		return Objects.equals(code, that.code) &&
			Objects.equals(minVersion, that.minVersion) &&
			Objects.equals(maxVersion, that.maxVersion);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, minVersion, maxVersion);
	}

	@Override
	public String toString() {
		return "PluginDependency{" +
			"code='" + code + '\'' +
			", minVersion='" + minVersion + '\'' +
			", maxVersion='" + maxVersion + '\'' +
			'}';
	}
}
