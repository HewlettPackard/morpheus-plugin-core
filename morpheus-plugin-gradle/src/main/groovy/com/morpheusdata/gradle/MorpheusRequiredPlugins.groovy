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

class MorpheusRequiredPlugins {

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

	private static void configure(Object target, Closure configuration) {
		Closure configured = configuration.rehydrate(target, configuration.owner, configuration.thisObject)
		configured.resolveStrategy = Closure.DELEGATE_FIRST
		configured.call()
	}
}
