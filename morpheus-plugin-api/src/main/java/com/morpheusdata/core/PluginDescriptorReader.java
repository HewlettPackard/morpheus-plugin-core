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

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

final class PluginDescriptorReader {

	private static final String LEGACY_YAML_PATH = "plugin.yml";
	private static final String LEGACY_PROPERTIES_PATH = "META-INF/morpheus.properties";
	private static final Pattern VERSION_BOUND_PATTERN = Pattern.compile("\\d+(?:\\.\\d+)*");

	private PluginDescriptorReader() {
	}

	static PluginDescriptor read(File jarFile) throws IOException, PluginDescriptorException {
		if (jarFile == null || !jarFile.isFile()) {
			throw new PluginDescriptorException("Plugin JAR does not exist: " + jarFile);
		}

		try (JarFile jar = new JarFile(jarFile)) {
			JarEntry canonicalEntry = jar.getJarEntry(PluginDescriptor.DESCRIPTOR_PATH);
			if (canonicalEntry != null) {
				Map<String, Object> descriptor = readYaml(jar, canonicalEntry, PluginDescriptor.DESCRIPTOR_PATH);
				return buildDescriptor(descriptor, PluginDescriptor.DESCRIPTOR_PATH, true);
			}

			Manifest manifest = jar.getManifest();
			Map<String, Object> manifestValues = readManifest(manifest);
			JarEntry yamlEntry = jar.getJarEntry(LEGACY_YAML_PATH);
			if (yamlEntry != null) {
				Map<String, Object> descriptor = readYaml(jar, yamlEntry, LEGACY_YAML_PATH);
				requireLegacyPluginClass(descriptor, LEGACY_YAML_PATH, "field");
				fillMissingLegacyValues(descriptor, manifestValues);
				return buildDescriptor(descriptor, LEGACY_YAML_PATH, false);
			}

			JarEntry propertiesEntry = jar.getJarEntry(LEGACY_PROPERTIES_PATH);
			if (propertiesEntry != null) {
				Map<String, Object> descriptor = readProperties(jar, propertiesEntry);
				requireLegacyPluginClass(descriptor, LEGACY_PROPERTIES_PATH, "property");
				fillMissingLegacyValues(descriptor, manifestValues);
				return buildDescriptor(descriptor, LEGACY_PROPERTIES_PATH, false);
			}

			if (manifest != null) {
				if (isBlank(stringValue(manifestValues.get("pluginClass")))) {
					throw new PluginDescriptorException("META-INF/MANIFEST.MF is missing required Plugin-Class or pluginClass attribute");
				}
				return buildDescriptor(manifestValues, "META-INF/MANIFEST.MF", false);
			}

			throw new PluginDescriptorException("No plugin descriptor found (" + PluginDescriptor.DESCRIPTOR_PATH +
				", plugin.yml, META-INF/morpheus.properties, or META-INF/MANIFEST.MF)");
		}
	}

	private static Map<String, Object> readYaml(JarFile jar, JarEntry entry, String source) throws IOException, PluginDescriptorException {
		LoaderOptions loaderOptions = new LoaderOptions();
		loaderOptions.setAllowDuplicateKeys(false);
		Yaml yaml = new Yaml(new SafeConstructor(loaderOptions));
		try (InputStream input = jar.getInputStream(entry)) {
			Object value = yaml.load(input);
			if (value == null) {
				throw new PluginDescriptorException(source + " is empty");
			}
			if (!(value instanceof Map)) {
				throw new PluginDescriptorException(source + " must contain a YAML object");
			}
			Map<String, Object> descriptor = new HashMap<>();
			((Map<?, ?>) value).forEach((key, mapValue) -> descriptor.put(String.valueOf(key), mapValue));
			return descriptor;
		} catch (PluginDescriptorException e) {
			throw e;
		} catch (RuntimeException e) {
			throw new PluginDescriptorException("Unable to parse " + source + ": " + e.getMessage(), e);
		}
	}

	private static Map<String, Object> readProperties(JarFile jar, JarEntry entry) throws IOException {
		Properties properties = new Properties();
		try (InputStream input = jar.getInputStream(entry)) {
			properties.load(input);
		}
		Map<String, Object> descriptor = new HashMap<>();
		descriptor.put("pluginClass", properties.getProperty("pluginClass"));
		descriptor.put("code", properties.getProperty("code"));
		descriptor.put("version", firstNonBlank(properties.getProperty("pluginVersion"), properties.getProperty("version")));
		return descriptor;
	}

	private static Map<String, Object> readManifest(Manifest manifest) {
		Map<String, Object> descriptor = new HashMap<>();
		if (manifest == null) {
			return descriptor;
		}
		Attributes attributes = manifest.getMainAttributes();
		descriptor.put("pluginClass", getAttributeIgnoreCase(attributes, "Plugin-Class", "pluginClass"));
		descriptor.put("code", getAttributeIgnoreCase(attributes, "Morpheus-Code"));
		descriptor.put("version", getAttributeIgnoreCase(attributes, "Plugin-Version"));
		return descriptor;
	}

	private static String getAttributeIgnoreCase(Attributes attributes, String... names) {
		for (String name : names) {
			String value = attributes.getValue(name);
			if (!isBlank(value)) {
				return value;
			}
		}
		for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
			for (String name : names) {
				if (entry.getKey().toString().equalsIgnoreCase(name)) {
					return stringValue(entry.getValue());
				}
			}
		}
		return null;
	}

	private static void fillMissingLegacyValues(Map<String, Object> descriptor, Map<String, Object> manifestValues) {
		if (isBlank(stringValue(descriptor.get("code")))) {
			descriptor.put("code", manifestValues.get("code"));
		}
		if (isBlank(stringValue(descriptor.get("version")))) {
			descriptor.put("version", manifestValues.get("version"));
		}
	}

	private static void requireLegacyPluginClass(Map<String, Object> descriptor, String source, String fieldType) throws PluginDescriptorException {
		if (!descriptor.containsKey("pluginClass")) {
			throw new PluginDescriptorException(source + " is missing required 'pluginClass' " + fieldType);
		}
		if (isBlank(stringValue(descriptor.get("pluginClass")))) {
			throw new PluginDescriptorException(source + " 'pluginClass' " + fieldType + " is empty");
		}
	}

	private static PluginDescriptor buildDescriptor(Map<String, Object> values, String source, boolean strict) throws PluginDescriptorException {
		int schemaVersion = parseSchemaVersion(values.get("schemaVersion"), source, strict);
		String pluginClass = requiredString(values.get("pluginClass"), "pluginClass", source);
		String code = strict ? requiredString(values.get("code"), "code", source) : optionalString(values.get("code"));
		String version = strict ? requiredString(values.get("version"), "version", source) : optionalString(values.get("version"));
		List<PluginDependency> dependencies = parseDependencies(values.get("dependencies"), code, source);
		return new PluginDescriptor(schemaVersion, pluginClass, code, version, dependencies);
	}

	private static int parseSchemaVersion(Object value, String source, boolean strict) throws PluginDescriptorException {
		if (value == null) {
			if (strict) {
				throw new PluginDescriptorException(source + " is missing required 'schemaVersion' field");
			}
			return PluginDescriptor.CURRENT_SCHEMA_VERSION;
		}
		try {
			int schemaVersion = Integer.parseInt(String.valueOf(value));
			if (schemaVersion != PluginDescriptor.CURRENT_SCHEMA_VERSION) {
				throw new PluginDescriptorException("Unsupported plugin descriptor schemaVersion " + schemaVersion +
					" in " + source + "; supported version is " + PluginDescriptor.CURRENT_SCHEMA_VERSION);
			}
			return schemaVersion;
		} catch (NumberFormatException e) {
			throw new PluginDescriptorException(source + " 'schemaVersion' must be an integer");
		}
	}

	private static List<PluginDependency> parseDependencies(Object value, String pluginCode, String source) throws PluginDescriptorException {
		List<PluginDependency> dependencies = new ArrayList<>();
		if (value == null) {
			return dependencies;
		}
		if (!(value instanceof Iterable)) {
			throw new PluginDescriptorException(source + " 'dependencies' must be a list");
		}

		Set<String> dependencyCodes = new HashSet<>();
		int index = 0;
		for (Object item : (Iterable<?>) value) {
			if (!(item instanceof Map)) {
				throw new PluginDescriptorException(source + " dependency at index " + index + " must be an object");
			}
			Map<?, ?> dependencyValues = (Map<?, ?>) item;
			String code = requiredString(dependencyValues.get("code"), "dependencies[" + index + "].code", source);
			if (!dependencyCodes.add(code)) {
				throw new PluginDescriptorException(source + " declares duplicate dependency '" + code + "'");
			}
			if (!isBlank(pluginCode) && pluginCode.equals(code)) {
				throw new PluginDescriptorException("Plugin '" + pluginCode + "' cannot depend on itself");
			}

			String minVersion = null;
			String maxVersion = null;
			Object versionValue = dependencyValues.get("version");
			if (versionValue != null) {
				if (!(versionValue instanceof Map)) {
					throw new PluginDescriptorException(source + " dependency '" + code + "' version must be an object");
				}
				Map<?, ?> versionValues = (Map<?, ?>) versionValue;
				if (versionValues.containsKey("minInclusive") || versionValues.containsKey("maxExclusive")) {
					throw new PluginDescriptorException(source + " dependency '" + code +
						"' must use version bounds 'min' and 'max'");
				}
				minVersion = optionalString(versionValues.get("min"));
				maxVersion = optionalString(versionValues.get("max"));
				validateVersionBound(minVersion, "min", code, source);
				validateVersionBound(maxVersion, "max", code, source);
				if (minVersion != null && maxVersion != null &&
					compareVersionBounds(minVersion, maxVersion) >= 0) {
					throw new PluginDescriptorException(source + " dependency '" + code +
						"' must have min lower than max");
				}
			}
			dependencies.add(new PluginDependency(code, minVersion, maxVersion));
			index++;
		}
		return dependencies;
	}

	private static void validateVersionBound(String version, String field, String code, String source) throws PluginDescriptorException {
		if (version != null && !VERSION_BOUND_PATTERN.matcher(version).matches()) {
			throw new PluginDescriptorException(source + " dependency '" + code + "' " + field +
				" must be a dotted numeric version");
		}
	}

	private static int compareVersionBounds(String left, String right) {
		String[] leftParts = left.split("\\.");
		String[] rightParts = right.split("\\.");
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

	private static String requiredString(Object value, String field, String source) throws PluginDescriptorException {
		String string = optionalString(value);
		if (string == null) {
			throw new PluginDescriptorException(source + " is missing required '" + field + "' field");
		}
		return string;
	}

	private static String optionalString(Object value) {
		String string = stringValue(value);
		return isBlank(string) ? null : string.trim();
	}

	private static String stringValue(Object value) {
		return value == null ? null : String.valueOf(value);
	}

	private static String firstNonBlank(String first, String second) {
		return !isBlank(first) ? first : second;
	}

	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
