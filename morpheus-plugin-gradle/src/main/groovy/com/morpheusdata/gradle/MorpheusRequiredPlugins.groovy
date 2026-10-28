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

package com.morpheusdata.gradle

import groovy.json.JsonOutput
import org.gradle.api.GradleException

import java.util.regex.Pattern

class MorpheusRequiredPlugins {

	private static final Pattern VERSION_BOUND_PATTERN = Pattern.compile(/\d+(?:\.\d+)*/)

	final List<MorpheusRequiredPlugin> plugins = []
	boolean configured = false

	void plugin(String code, Closure configuration = null) {
		configured = true
		def requiredPlugin = new MorpheusRequiredPlugin(code: code)
		if(configuration) {
			configure(requiredPlugin, configuration)
		}
		plugins << requiredPlugin
	}

	String toManifestJson(String pluginCode) {
		validate(pluginCode)
		JsonOutput.toJson(plugins.collect { requiredPlugin ->
			Map<String, String> value = [code: requiredPlugin.code]
			if(requiredPlugin.minVersion) {
				value.minVersion = requiredPlugin.minVersion
			}
			if(requiredPlugin.maxVersion) {
				value.maxVersion = requiredPlugin.maxVersion
			}
			value
		})
	}

	private void validate(String pluginCode) {
		Set<String> codes = []
		plugins.each { requiredPlugin ->
			if(!requiredPlugin.code?.trim()) {
				throw new GradleException('Required plugin code must be specified')
			}
			if(!codes.add(requiredPlugin.code)) {
				throw new GradleException("Required plugins declare duplicate plugin '${requiredPlugin.code}'")
			}
			if(requiredPlugin.code == pluginCode) {
				throw new GradleException("Plugin '${pluginCode}' cannot require itself")
			}
			validateVersionBound(requiredPlugin.code, 'minVersion', requiredPlugin.minVersion)
			validateVersionBound(requiredPlugin.code, 'maxVersion', requiredPlugin.maxVersion)
			if(requiredPlugin.minVersion && requiredPlugin.maxVersion &&
				compareVersions(requiredPlugin.minVersion, requiredPlugin.maxVersion) >= 0) {
				throw new GradleException("Required plugin '${requiredPlugin.code}' must have minVersion lower than maxVersion")
			}
		}
	}

	private static void validateVersionBound(String requiredPluginCode, String field, String value) {
		if(value && !VERSION_BOUND_PATTERN.matcher(value).matches()) {
			throw new GradleException("Required plugin '${requiredPluginCode}' ${field} must be a dotted numeric version")
		}
	}

	private static int compareVersions(String left, String right) {
		def leftParts = left.tokenize('.').collect { new BigInteger(it) }
		def rightParts = right.tokenize('.').collect { new BigInteger(it) }
		int size = Math.max(leftParts.size(), rightParts.size())
		for(int i = 0; i < size; i++) {
			BigInteger leftPart = i < leftParts.size() ? leftParts[i] : BigInteger.ZERO
			BigInteger rightPart = i < rightParts.size() ? rightParts[i] : BigInteger.ZERO
			int comparison = leftPart <=> rightPart
			if(comparison != 0) {
				return comparison
			}
		}
		return 0
	}

	private static void configure(Object target, Closure configuration) {
		Closure configured = configuration.rehydrate(target, configuration.owner, configuration.thisObject)
		configured.resolveStrategy = Closure.DELEGATE_FIRST
		configured.call()
	}
}
