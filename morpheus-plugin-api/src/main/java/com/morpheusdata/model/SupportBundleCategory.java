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
 * Fixed set of UI grouping categories for support bundle content types.
 *
 * <p>Plugin authors must choose one of these values when implementing
 * {@link com.morpheusdata.core.providers.SupportBundleContentsProvider#getCategory()}.
 * This prevents arbitrary category strings from appearing in the Morpheus UI.
 *
 * @since 1.4.0
 * @author Mike Carlin
 */
public enum SupportBundleCategory {

    /** Appliance-level content (logs, version info, health snapshots, etc.). */
    APPLIANCE("Appliance"),

    /** Cloud integration content. */
    CLOUDS("Clouds"),

    /** Bare-metal / compute server content. */
    SERVERS("Servers"),

    /** Cluster content. */
    CLUSTERS("Clusters"),

    /** Network integration content. */
    NETWORKING("Networking"),

    /** Storage integration content. */
    STORAGE("Storage"),

    /** System integration content. */
    SYSTEMS("Systems");

    /** Human-readable display label (used in the Morpheus UI picker and API responses). */
    public final String label;

    SupportBundleCategory(String label) {
        this.label = label;
    }

    /**
     * Returns the display label for this category (e.g. {@code "Clouds"}).
     *
     * @return the display label; never null
     */
    @Override
    public String toString() {
        return label;
    }
}
