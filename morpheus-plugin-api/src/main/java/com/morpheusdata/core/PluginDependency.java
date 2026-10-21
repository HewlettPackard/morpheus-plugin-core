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
	private final String minVersionInclusive;
	private final String maxVersionExclusive;

	public PluginDependency(String code, String minVersionInclusive, String maxVersionExclusive) {
		this.code = code;
		this.minVersionInclusive = minVersionInclusive;
		this.maxVersionExclusive = maxVersionExclusive;
	}

	public String getCode() {
		return code;
	}

	public String getMinVersionInclusive() {
		return minVersionInclusive;
	}

	public String getMaxVersionExclusive() {
		return maxVersionExclusive;
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
			Objects.equals(minVersionInclusive, that.minVersionInclusive) &&
			Objects.equals(maxVersionExclusive, that.maxVersionExclusive);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, minVersionInclusive, maxVersionExclusive);
	}

	@Override
	public String toString() {
		return "PluginDependency{" +
			"code='" + code + '\'' +
			", minVersionInclusive='" + minVersionInclusive + '\'' +
			", maxVersionExclusive='" + maxVersionExclusive + '\'' +
			'}';
	}
}
