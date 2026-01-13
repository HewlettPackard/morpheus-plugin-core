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

package com.morpheusdata.request;

import com.morpheusdata.model.ComputeServer;

import java.util.List;
import java.util.Map;


/**
 * Request object for building a resize changelist.
 *
 * @param <T> the type of existing items being compared
 * @since 1.3.0
 */
public class BuildResizeChangelistRequest<T> {
	/**
	 * The compute server being resized.
	 */
	protected ComputeServer server;
	/**
	 * The desired configuration as a list of maps.
	 */
	protected List<Map<String, Object>> desired;
	/**
	 * The existing items of type T.
	 */
	protected List<T> existing;
	/**
	 * Whether to use soft matching for comparison.
	 * <p>
	 * A match is considered soft when we can't rely on database IDs to make a comparison. In that
	 * case, it's necessary to use other attributes to determine whether the two items are the "same". This
	 * comes into effect when applying a desired configuration across multiple containers in an instance.
	 */
	protected boolean softMatch;

	public ComputeServer getServer() {
		return server;
	}

	public void setServer(ComputeServer server) {
		this.server = server;
	}

	public List<T> getExisting() {
		return existing;
	}

	public void setExisting(List<T> existing) {
		this.existing = existing;
	}

	public List<Map<String, Object>> getDesired() {
		return desired;
	}

	public void setDesired(List<Map<String, Object>> desired) {
		this.desired = desired;
	}

	public boolean isSoftMatch() {
		return softMatch;
	}

	public void setSoftMatch(boolean softMatch) {
		this.softMatch = softMatch;
	}
}
