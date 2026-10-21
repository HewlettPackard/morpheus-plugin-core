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

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.util.regex.Pattern

class GenerateMorpheusPluginDescriptor extends DefaultTask {

	private static final Pattern VERSION_BOUND_PATTERN = Pattern.compile(/\d+(?:\.\d+)*/)

	@Input
	Integer schemaVersion

	@Input
	String pluginClass

	@Input
	String code

	@Input
	String version

	@Internal
	List<MorpheusPluginDependency> dependencies = []

	@OutputFile
	File outputFile

	@Input
	List<String> getDependencySpecifications() {
		dependencies.collect {
			"${it.code ?: ''}|${it.minVersionInclusive ?: ''}|${it.maxVersionExclusive ?: ''}".toString()
		}
	}

	@TaskAction
	void generate() {
		validateDescriptor()
		outputFile.parentFile.mkdirs()
		outputFile.setText(renderDescriptor(), 'UTF-8')
	}

	private void validateDescriptor() {
		if(schemaVersion != 1) {
			throw new GradleException("Unsupported plugin descriptor schemaVersion ${schemaVersion}; supported version is 1")
		}
		[
			pluginClass: pluginClass,
			code: code,
			version: version
		].each { field, value ->
			if(!value?.trim()) {
				throw new GradleException("Plugin descriptor '${field}' must be specified")
			}
		}

		Set<String> codes = []
		dependencies.each { dependency ->
			if(!dependency.code?.trim()) {
				throw new GradleException("Plugin dependency code must be specified")
			}
			if(!codes.add(dependency.code)) {
				throw new GradleException("Plugin descriptor declares duplicate dependency '${dependency.code}'")
			}
			if(dependency.code == code) {
				throw new GradleException("Plugin '${code}' cannot depend on itself")
			}
			GenerateMorpheusPluginDescriptor.validateVersionBound(dependency.code, 'minVersionInclusive', dependency.minVersionInclusive)
			GenerateMorpheusPluginDescriptor.validateVersionBound(dependency.code, 'maxVersionExclusive', dependency.maxVersionExclusive)
			if(dependency.minVersionInclusive && dependency.maxVersionExclusive &&
				GenerateMorpheusPluginDescriptor.compareVersions(dependency.minVersionInclusive, dependency.maxVersionExclusive) >= 0) {
				throw new GradleException("Plugin dependency '${dependency.code}' must have minVersionInclusive lower than maxVersionExclusive")
			}
		}
	}

	private static void validateVersionBound(String dependencyCode, String field, String value) {
		if(value && !VERSION_BOUND_PATTERN.matcher(value).matches()) {
			throw new GradleException("Plugin dependency '${dependencyCode}' ${field} must be a dotted numeric version")
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

	private String renderDescriptor() {
		def lines = [
			"schemaVersion: ${schemaVersion}",
			"pluginClass: ${quote(pluginClass)}",
			"code: ${quote(code)}",
			"version: ${quote(version)}"
		]
		if(dependencies) {
			lines << 'dependencies:'
			dependencies.each { dependency ->
				lines << "  - code: ${GenerateMorpheusPluginDescriptor.quote(dependency.code)}"
				if(dependency.minVersionInclusive || dependency.maxVersionExclusive) {
					lines << '    version:'
					if(dependency.minVersionInclusive) {
						lines << "      minInclusive: ${GenerateMorpheusPluginDescriptor.quote(dependency.minVersionInclusive)}"
					}
					if(dependency.maxVersionExclusive) {
						lines << "      maxExclusive: ${GenerateMorpheusPluginDescriptor.quote(dependency.maxVersionExclusive)}"
					}
				}
			}
		} else {
			lines << 'dependencies: []'
		}
		return lines.join('\n') + '\n'
	}

	private static String quote(String value) {
		return "'${value.replace("'", "''")}'"
	}
}
