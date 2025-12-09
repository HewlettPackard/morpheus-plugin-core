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

package com.morpheusdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the summary information for a Cloud/Zone to be displayed in the Cloud Summary tab.
 * This includes standard info items, custom zone summary renderer, and custom costing summary renderer.
 *
 * @author Saad Mansoor
 * @since 1.2.15
 */
public class CloudSummary {
	/**
	 * A list of info items to be displayed in the standard zone summary section.
	 * These items will be rendered as label-value pairs in the info section.
	 * Plugins can use this to add custom fields to the standard summary display.
	 */
	protected List<CloudSummaryInfoItem> infoItems = new ArrayList<>();

	/**
	 * The name of the custom template/renderer to use for rendering a custom zone summary section.
	 * This allows plugins to render custom HTML/content in the zone summary tab.
	 * The renderer should be provided via a {@link com.morpheusdata.core.providers.CloudSummaryProvider}.
	 */
	protected String zoneSummaryRenderer;

	/**
	 * The name of the custom template/renderer to use for rendering a custom costing summary section.
	 * This allows plugins to render custom costing information in the zone summary tab.
	 * The renderer should be provided via a {@link com.morpheusdata.core.providers.CloudCostingSummaryProvider}.
	 */
	protected String costingSummaryRenderer;

	/**
	 * Optional data that can be passed to the zone summary renderer.
	 * This allows plugins to pass custom data to their renderer.
	 */
	protected Object zoneSummaryData;

	/**
	 * Optional data that can be passed to the costing summary renderer.
	 * This allows plugins to pass custom data to their renderer.
	 */
	protected Object costingSummaryData;

	public List<CloudSummaryInfoItem> getInfoItems() {
		return infoItems;
	}

	public void setInfoItems(List<CloudSummaryInfoItem> infoItems) {
		this.infoItems = infoItems;
	}

	public String getZoneSummaryRenderer() {
		return zoneSummaryRenderer;
	}

	public void setZoneSummaryRenderer(String zoneSummaryRenderer) {
		this.zoneSummaryRenderer = zoneSummaryRenderer;
	}

	public String getCostingSummaryRenderer() {
		return costingSummaryRenderer;
	}

	public void setCostingSummaryRenderer(String costingSummaryRenderer) {
		this.costingSummaryRenderer = costingSummaryRenderer;
	}

	public Object getZoneSummaryData() {
		return zoneSummaryData;
	}

	public void setZoneSummaryData(Object zoneSummaryData) {
		this.zoneSummaryData = zoneSummaryData;
	}

	public Object getCostingSummaryData() {
		return costingSummaryData;
	}

	public void setCostingSummaryData(Object costingSummaryData) {
		this.costingSummaryData = costingSummaryData;
	}
}

