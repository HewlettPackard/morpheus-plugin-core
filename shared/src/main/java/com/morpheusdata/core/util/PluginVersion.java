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

package com.morpheusdata.core.util;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Required-plugin versions use dotted numbers with optional qualifiers such as
 * .dev or -SNAPSHOT. Compatibility compares only the numeric components.
 */
public final class PluginVersion {

	private static final Pattern VERSION_PATTERN =
		Pattern.compile("(\\d+(?:\\.\\d+)*)(?:[.-][A-Za-z][A-Za-z0-9]*(?:[.-][A-Za-z0-9]+)*)?");

	private PluginVersion() {
	}

	public static boolean isValid(String version) {
		return version != null && VERSION_PATTERN.matcher(version).matches();
	}

	public static int compare(String left, String right) {
		String[] leftParts = numericParts(left);
		String[] rightParts = numericParts(right);
		int size = Math.max(leftParts.length, rightParts.length);
		for (int i = 0; i < size; i++) {
			BigInteger leftPart = i < leftParts.length ? new BigInteger(leftParts[i]) : BigInteger.ZERO;
			BigInteger rightPart = i < rightParts.length ? new BigInteger(rightParts[i]) : BigInteger.ZERO;
			int comparison = leftPart.compareTo(rightPart);
			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;
	}

	private static String[] numericParts(String version) {
		if (version == null) {
			throw new IllegalArgumentException("Plugin version must not be null");
		}
		Matcher matcher = VERSION_PATTERN.matcher(version);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid plugin version: " + version);
		}
		return matcher.group(1).split("\\.");
	}
}
