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

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Resolves required plugins into a safe load order without loading plugin classes.
 */
public final class PluginLoadResolver {

	private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+(?:\\.\\d+)*");

	private PluginLoadResolver() {
	}

	public static PluginLoadPlan resolve(Collection<PluginDescriptor> candidates) {
		return resolve(candidates, Collections.emptyList());
	}

	public static PluginLoadPlan resolve(Collection<PluginDescriptor> candidates, Collection<PluginDescriptor> availablePlugins) {
		List<PluginDescriptor> candidateList = candidates == null ? Collections.emptyList() :
			candidates.stream().filter(java.util.Objects::nonNull).collect(Collectors.toList());
		Map<PluginDescriptor, String> failures = new LinkedHashMap<>();
		Map<String, List<PluginDescriptor>> candidatesByCode = new LinkedHashMap<>();
		Set<PluginDescriptor> duplicateCandidates = Collections.newSetFromMap(new IdentityHashMap<>());

		for (PluginDescriptor candidate : candidateList) {
			if (candidate.getCode() != null) {
				candidatesByCode.computeIfAbsent(candidate.getCode(), ignored -> new ArrayList<>()).add(candidate);
			}
		}

		Map<String, PluginDescriptor> uniqueCandidatesByCode = new LinkedHashMap<>();
		for (Map.Entry<String, List<PluginDescriptor>> entry : candidatesByCode.entrySet()) {
			if (entry.getValue().size() > 1) {
				for (PluginDescriptor duplicate : entry.getValue()) {
					duplicateCandidates.add(duplicate);
					failures.put(duplicate, "Multiple plugin files declare code '" + entry.getKey() + "'");
				}
			} else {
				uniqueCandidatesByCode.put(entry.getKey(), entry.getValue().get(0));
			}
		}
		List<PluginDescriptor> uniqueCandidates = candidateList.stream()
			.filter(candidate -> !duplicateCandidates.contains(candidate))
			.collect(Collectors.toList());

		Map<String, PluginDescriptor> availableByCode = new LinkedHashMap<>();
		if (availablePlugins != null) {
			for (PluginDescriptor available : availablePlugins) {
				if (available != null && available.getCode() != null) {
					availableByCode.putIfAbsent(available.getCode(), available);
				}
			}
		}

		for (PluginDescriptor candidate : uniqueCandidates) {
			for (RequiredPlugin requiredPlugin : candidate.getRequiredPlugins()) {
				PluginDescriptor requiredDescriptor = uniqueCandidatesByCode.get(requiredPlugin.getCode());
				if (requiredDescriptor == null) {
					requiredDescriptor = availableByCode.get(requiredPlugin.getCode());
				}
				if (requiredDescriptor == null) {
					failures.put(candidate, "Plugin '" + candidate.getCode() + "' requires plugin '" +
						requiredPlugin.getCode() + "', but it is not available");
					break;
				}
				String versionFailure = validateVersion(candidate, requiredPlugin, requiredDescriptor);
				if (versionFailure != null) {
					failures.put(candidate, versionFailure);
					break;
				}
			}
		}

		propagateFailures(uniqueCandidates, uniqueCandidatesByCode, failures);
		detectCycles(uniqueCandidates, uniqueCandidatesByCode, failures);
		propagateFailures(uniqueCandidates, uniqueCandidatesByCode, failures);

		List<PluginDescriptor> loadOrder = topologicalSort(uniqueCandidates, uniqueCandidatesByCode, failures);
		return new PluginLoadPlan(loadOrder, failures);
	}

	private static String validateVersion(PluginDescriptor candidate, RequiredPlugin requiredPlugin, PluginDescriptor requiredDescriptor) {
		if (requiredPlugin.getMinVersion() == null && requiredPlugin.getMaxVersion() == null) {
			return null;
		}
		String availableVersion = requiredDescriptor.getVersion();
		if (availableVersion == null || !VERSION_PATTERN.matcher(availableVersion).matches()) {
			return "Plugin '" + candidate.getCode() + "' requires plugin '" + requiredPlugin.getCode() +
				"', but its version '" + availableVersion + "' is not a dotted numeric version";
		}
		if (requiredPlugin.getMinVersion() != null &&
			compareVersions(availableVersion, requiredPlugin.getMinVersion()) < 0) {
			return incompatibleVersionMessage(candidate, requiredPlugin, availableVersion);
		}
		if (requiredPlugin.getMaxVersion() != null &&
			compareVersions(availableVersion, requiredPlugin.getMaxVersion()) >= 0) {
			return incompatibleVersionMessage(candidate, requiredPlugin, availableVersion);
		}
		return null;
	}

	private static String incompatibleVersionMessage(PluginDescriptor candidate, RequiredPlugin requiredPlugin, String availableVersion) {
		List<String> bounds = new ArrayList<>();
		if (requiredPlugin.getMinVersion() != null) {
			bounds.add(">= " + requiredPlugin.getMinVersion());
		}
		if (requiredPlugin.getMaxVersion() != null) {
			bounds.add("< " + requiredPlugin.getMaxVersion());
		}
		return "Plugin '" + candidate.getCode() + "' requires plugin '" + requiredPlugin.getCode() +
			"' version " + String.join(" and ", bounds) + ", but version " + availableVersion + " is available";
	}

	private static void propagateFailures(
		List<PluginDescriptor> candidates,
		Map<String, PluginDescriptor> candidatesByCode,
		Map<PluginDescriptor, String> failures
	) {
		boolean changed;
		do {
			changed = false;
			for (PluginDescriptor candidate : candidates) {
				if (failures.containsKey(candidate)) {
					continue;
				}
				for (RequiredPlugin requiredPlugin : candidate.getRequiredPlugins()) {
					PluginDescriptor requiredDescriptor = candidatesByCode.get(requiredPlugin.getCode());
					if (requiredDescriptor != null && failures.containsKey(requiredDescriptor)) {
						failures.put(candidate, "Plugin '" + candidate.getCode() + "' requires plugin '" +
							requiredPlugin.getCode() + "', which could not be loaded: " + failures.get(requiredDescriptor));
						changed = true;
						break;
					}
				}
			}
		} while (changed);
	}

	private static void detectCycles(
		List<PluginDescriptor> candidates,
		Map<String, PluginDescriptor> candidatesByCode,
		Map<PluginDescriptor, String> failures
	) {
		Map<PluginDescriptor, Integer> states = new HashMap<>();
		List<PluginDescriptor> stack = new ArrayList<>();
		for (PluginDescriptor candidate : candidates) {
			if (!failures.containsKey(candidate) && states.getOrDefault(candidate, 0) == 0) {
				detectCycles(candidate, candidatesByCode, failures, states, stack);
			}
		}
	}

	private static void detectCycles(
		PluginDescriptor candidate,
		Map<String, PluginDescriptor> candidatesByCode,
		Map<PluginDescriptor, String> failures,
		Map<PluginDescriptor, Integer> states,
		List<PluginDescriptor> stack
	) {
		states.put(candidate, 1);
		stack.add(candidate);
		for (RequiredPlugin requiredPlugin : candidate.getRequiredPlugins()) {
			PluginDescriptor requiredDescriptor = candidatesByCode.get(requiredPlugin.getCode());
			if (requiredDescriptor == null || failures.containsKey(requiredDescriptor)) {
				continue;
			}
			int state = states.getOrDefault(requiredDescriptor, 0);
			if (state == 0) {
				detectCycles(requiredDescriptor, candidatesByCode, failures, states, stack);
			} else if (state == 1) {
				int cycleStart = stack.indexOf(requiredDescriptor);
				List<PluginDescriptor> cycle = new ArrayList<>(stack.subList(cycleStart, stack.size()));
				List<String> cycleCodes = cycle.stream().map(PluginDescriptor::getCode).collect(Collectors.toList());
				cycleCodes.add(requiredDescriptor.getCode());
				String message = "Required plugin cycle detected: " + String.join(" -> ", cycleCodes);
				for (PluginDescriptor cyclePlugin : cycle) {
					failures.put(cyclePlugin, message);
				}
			}
		}
		stack.remove(stack.size() - 1);
		states.put(candidate, 2);
	}

	private static List<PluginDescriptor> topologicalSort(
		List<PluginDescriptor> candidates,
		Map<String, PluginDescriptor> candidatesByCode,
		Map<PluginDescriptor, String> failures
	) {
		Map<PluginDescriptor, Integer> requiredCounts = new LinkedHashMap<>();
		Map<PluginDescriptor, Set<PluginDescriptor>> dependents = new LinkedHashMap<>();
		for (PluginDescriptor candidate : candidates) {
			if (!failures.containsKey(candidate)) {
				requiredCounts.put(candidate, 0);
				dependents.put(candidate, new LinkedHashSet<>());
			}
		}
		for (PluginDescriptor candidate : requiredCounts.keySet()) {
			for (RequiredPlugin requiredPlugin : candidate.getRequiredPlugins()) {
				PluginDescriptor requiredDescriptor = candidatesByCode.get(requiredPlugin.getCode());
				if (requiredDescriptor != null && requiredCounts.containsKey(requiredDescriptor)) {
					requiredCounts.put(candidate, requiredCounts.get(candidate) + 1);
					dependents.get(requiredDescriptor).add(candidate);
				}
			}
		}

		Deque<PluginDescriptor> ready = new ArrayDeque<>();
		for (Map.Entry<PluginDescriptor, Integer> entry : requiredCounts.entrySet()) {
			if (entry.getValue() == 0) {
				ready.add(entry.getKey());
			}
		}

		List<PluginDescriptor> loadOrder = new ArrayList<>();
		while (!ready.isEmpty()) {
			PluginDescriptor requiredDescriptor = ready.removeFirst();
			loadOrder.add(requiredDescriptor);
			for (PluginDescriptor dependent : dependents.get(requiredDescriptor)) {
				int remaining = requiredCounts.get(dependent) - 1;
				requiredCounts.put(dependent, remaining);
				if (remaining == 0) {
					ready.addLast(dependent);
				}
			}
		}
		return loadOrder;
	}

	private static int compareVersions(String left, String right) {
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
}
