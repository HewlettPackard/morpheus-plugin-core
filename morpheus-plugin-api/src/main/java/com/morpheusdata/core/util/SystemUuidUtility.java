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

package com.morpheusdata.core.util;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Normalizes and validates system (SMBIOS/hardware) UUIDs so the same physical host can be recognized across
 * providers regardless of how each one formats the value.
 *
 * @since 1.5.1
 */
public class SystemUuidUtility {

	private static final Pattern CANONICAL = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
	private static final Pattern COMPACT = Pattern.compile("^[0-9a-fA-F]{32}$");

	private SystemUuidUtility() {
	}

	/**
	 * Normalizes a system UUID to lowercase 8-4-4-4-12 form. Surrounding whitespace and one pair of enclosing braces
	 * are removed, and both hyphenated and 32 digit compact input are accepted.
	 *
	 * @param value the raw system UUID
	 * @return the normalized UUID, or null if the value is null, malformed, all zeros or all f's
	 * @since 1.5.1
	 */
	public static String normalize(String value) {
		if(value == null) {
			return null;
		}
		String candidate = value.trim();
		if(candidate.startsWith("{") && candidate.endsWith("}")) {
			candidate = candidate.substring(1, candidate.length() - 1);
		}
		if(!CANONICAL.matcher(candidate).matches() && !COMPACT.matcher(candidate).matches()) {
			return null;
		}

		String hex = candidate.replace("-", "").toLowerCase(Locale.ROOT);
		// All zeros and all f's are placeholder values firmware reports when no real UUID is set
		if(hex.chars().allMatch(c -> c == '0') || hex.chars().allMatch(c -> c == 'f')) {
			return null;
		}

		return hex.substring(0, 8) + "-" + hex.substring(8, 12) + "-" + hex.substring(12, 16) + "-" +
			hex.substring(16, 20) + "-" + hex.substring(20);
	}

	/**
	 * Checks whether a value is a usable system UUID.
	 *
	 * @param value the raw system UUID
	 * @return true if {@link #normalize(String)} returns a value
	 * @since 1.5.1
	 */
	public static boolean isValid(String value) {
		return normalize(value) != null;
	}
}
