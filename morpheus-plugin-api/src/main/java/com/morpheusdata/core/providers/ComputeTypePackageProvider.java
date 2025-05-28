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

import com.morpheusdata.model.*;
import com.morpheusdata.request.PackageDeleteRequest;
import com.morpheusdata.request.PackageInstallRequest;
import com.morpheusdata.request.PackageUpgradeRequest;
import com.morpheusdata.response.ServiceResponse;
import com.morpheusdata.views.HTMLResponse;


import java.util.List;

/*
 * Provides a standard set of methods for using creating and managing ComputeTypePackage objects
 */
public interface ComputeTypePackageProvider extends PluginProvider {

	/**
	 * Returns the circular logo for display when adding a package with this provider. SVGs are preferred.
	 * @return Icon
	 */
	Icon getCircularIcon();

	/**
	 * Provide custom configuration options when creating a new package through this provider
	 * @return a List of OptionType
	 */
	List<OptionType> getOptionTypes();


	String getDescription();

	String getType();

	String getPackageType();

	String getProviderType();

	/**
	 * The version of the package that this provider can install
	 * @return the version
	 */
	String getPackageVersion();

	/**
	 * Called when a package needs to be installed
	 * @param computeServerGroupPackage instance of the package that is being installed.
	 * @param serverGroup serverGroup/cluster that package will be applied to.
	 * @return ServiceResponse
	 * @deprecated Use {@link #installPackage(ComputeServerGroup, ComputeServerGroupPackage, PackageInstallRequest)} instead.
	 * 				Morpheus now calls the new variant {@link #installPackage(ComputeServerGroup, ComputeServerGroupPackage, PackageInstallRequest)}, and
	 * 				this method will only be invoked if called explicitly by the new method. Otherwise, it is ignored.
	 */
	@Deprecated(since = "1.2.8")
	default ServiceResponse<ComputeServerGroupPackage> installPackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage) {
		return ServiceResponse.error("not implemented");
	}

	/**
	 * Called when a package needs to be installed
	 * @param computeServerGroupPackage instance of the package that is being installed.
	 * @param serverGroup serverGroup/cluster that package will be applied to.
	 * @return ServiceResponse
	 */
	default ServiceResponse<ComputeServerGroupPackage> installPackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage, PackageInstallRequest packageInstallRequest) {
		return installPackage(serverGroup, computeServerGroupPackage);
	}

	/**
	 * Called when a package needs to be upgraded
	 * @param computeServerGroupPackage instance of the package that is being upgraded.
	 * @param serverGroup serverGroup/cluster that package will be applied to.
	 * @return ServiceResponse
	 * @deprecated Use {@link #upgradePackage(ComputeServerGroup, ComputeServerGroupPackage, PackageUpgradeRequest)} instead.
	 * 				Morpheus now calls the new {@link #upgradePackage(ComputeServerGroup, ComputeServerGroupPackage, PackageUpgradeRequest)}, and
	 * 				this method will only be invoked if called explicitly by the new method. Otherwise, it is ignored.
	 */
	@Deprecated(since = "1.2.8")
	default ServiceResponse<ComputeServerGroupPackage> upgradePackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage, String newVersion) {
		return ServiceResponse.error("not implemented");
	}

	/**
	 * Called when a package needs to be upgraded
	 * @param computeServerGroupPackage instance of the package that is being upgraded.
	 * @param serverGroup serverGroup/cluster that package will be applied to.
	 * @return ServiceResponse
	 */
	default ServiceResponse<ComputeServerGroupPackage> upgradePackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage, PackageUpgradeRequest packageUpgradeRequest) {
		return upgradePackage(serverGroup, computeServerGroupPackage, packageUpgradeRequest.newVersion);
	}

	/**
	 * Called when deleting a package
	 * @param computeServerGroupPackage instance of addonPackage that is being removed
	 * @param serverGroup serverGroup/cluster that package will be removed from
	 * @return ServiceResponse
	 * @deprecated Use {@link #deletePackage(ComputeServerGroup, ComputeServerGroupPackage, PackageDeleteRequest)} instead.
	 * 				Morpheus now calls the new {@link #deletePackage(ComputeServerGroup, ComputeServerGroupPackage, PackageDeleteRequest)}, and
	 * 				this method will only be invoked if called explicitly by the new method. Otherwise, it is ignored.
	 */
	@Deprecated(since = "1.2.8")
	default ServiceResponse deletePackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage) {
		return ServiceResponse.error("not implemented");
	}


	/**
	 * Called when deleting a package
	 * @param computeServerGroupPackage instance of addonPackage that is being removed
	 * @param serverGroup serverGroup/cluster that package will be removed from
	 * @return ServiceResponse
	 */
	default ServiceResponse deletePackage(ComputeServerGroup serverGroup, ComputeServerGroupPackage computeServerGroupPackage, PackageDeleteRequest packageDeleteRequest) {
		return deletePackage(serverGroup, computeServerGroupPackage);
	}

	/**
	 * Customized form provided to the rendering engine. Overrides the package form in the Appliance UI.
	 * @param computeServerGroupPackage details of the ComputeServerGroupPackage being rendered
	 * @return result of rendering a template
	 */
	default HTMLResponse renderFormTemplate(ComputeServerGroupPackage computeServerGroupPackage) {
		return null;
	}
}
