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


package com.morpheusdata.model;

/**
 * The operational state of a {@link StorageServerNode} as reported by the storage system.
 * <p>
 * Typical transitions are {@code joining} to {@code online}, {@code online} to and from
 * {@code degraded}, {@code online} or {@code degraded} to {@code evacuating} ahead of removal or
 * maintenance, and finally {@code offline}.
 *
 * @author Chinmay Keskar
 * @since 1.5.1
 * @see StorageServerNode
 */
public enum StorageServerNodeStatus {
	/**
	 * The node is healthy and fully participating in the storage system.
	 */
	online,

	/**
	 * The node is participating but has a fault, such as a failed drive or a stopped service.
	 * {@link StorageServerNode#getStatusMessage()} carries the reason.
	 */
	degraded,

	/**
	 * The node is not participating in the storage system.
	 */
	offline,

	/**
	 * The node is being added to the storage system and is not yet serving data.
	 */
	joining,

	/**
	 * Data is being moved off the node ahead of maintenance or removal.
	 */
	evacuating
}
