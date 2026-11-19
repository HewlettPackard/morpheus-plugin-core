/*
 *  Copyright 2026 HPE Development Company, L.P.
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

/** A firmware baseline — typically an SPP version or vendor firmware bundle. */
public class FirmwareBaseline {
	protected String id;
	protected String name;           // "SPP 2026.06.0"
	protected String version;        // "2026.06.0"
	protected String source;         // "connected" | "uploaded"
	protected String bundlePath;     // Local path for disconnected
	protected List<FirmwareComponent> components = new ArrayList<>();

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getVersion() { return version; }
	public void setVersion(String version) { this.version = version; }
	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }
	public String getBundlePath() { return bundlePath; }
	public void setBundlePath(String bundlePath) { this.bundlePath = bundlePath; }
	public List<FirmwareComponent> getComponents() { return components; }
	public void setComponents(List<FirmwareComponent> components) { this.components = components; }
}
