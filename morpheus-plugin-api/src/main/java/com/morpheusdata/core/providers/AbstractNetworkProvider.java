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

package com.morpheusdata.core.providers;

import com.morpheusdata.views.HandlebarsRenderer;
import com.morpheusdata.views.Renderer;

/**
 * Abstract base class for implementing a {@link NetworkProvider}.
 * This class provides a default implementation of the {@link #getRenderer()} method that creates and configures
 * a HandlebarsRenderer with common helpers for asset management, nonce generation, and internationalization.
 * <p>
 * Plugin developers should extend this class when implementing a NetworkProvider to inherit the default
 * rendering capabilities.
 * </p>
 *
 * @since 0.15.1
 * @author Bob Whiton, Dustin DeYoung
 * @see NetworkProvider
 */
public abstract class AbstractNetworkProvider implements NetworkProvider {
	private HandlebarsRenderer renderer;

	@Override
	public Renderer<?> getRenderer() {
		if(renderer == null) {
			renderer = new HandlebarsRenderer("renderer", getPlugin().getClassLoader());
			renderer.registerAssetHelper(getPlugin().getName());
			renderer.registerNonceHelper(getMorpheus().getWebRequest());
			renderer.registerI18nHelper(getPlugin(),getMorpheus());
		}
		return renderer;
	}

}
