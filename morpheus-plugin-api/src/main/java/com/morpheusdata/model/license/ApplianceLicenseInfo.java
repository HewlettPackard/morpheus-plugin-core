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

package com.morpheusdata.model.license;

import java.util.ArrayList;
import java.util.List;

/**
 * Root model returned by the Appliance License DataService. Contains the current merged license config,
 * the list of individually installed licenses, current usage counts, per-metric limit checks, and a
 * convenience flag indicating whether any limit has been reached.
 */
public class ApplianceLicenseInfo {
	public ApplianceLicenseData license;
	public List<ApplianceLicenseData> installedLicenses = new ArrayList<>();
	public ApplianceLicenseUsage currentUsage;
	public List<ApplianceLicenseLimit> licenseLimits = new ArrayList<>();
	public Boolean limitReached;
}
