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
 * The role a {@link StorageServerNode} plays within a scale-out storage system.
 * <p>
 * Scale-out storage systems run one or more service roles on each participating host. A node's
 * persona records which roles it carries, and drives the compute and memory reservation that
 * the storage provider requests for that host.
 *
 * @author Chinmay Keskar
 * @since 1.5.1
 * @see StorageServerNode
 */
public enum StorageServerNodePersona {
	/**
	 * The node runs both the controller role and the storage role, and contributes its local
	 * drives to the storage system. This is the default persona for a uniform cluster.
	 */
	symmetric,

	/**
	 * The node runs only the controller role. It serves I/O and metadata but contributes no
	 * local drives to the storage system.
	 */
	controller,

	/**
	 * The node runs only the storage role. It contributes its local drives to the storage system
	 * but does not serve I/O directly.
	 */
	storage
}
