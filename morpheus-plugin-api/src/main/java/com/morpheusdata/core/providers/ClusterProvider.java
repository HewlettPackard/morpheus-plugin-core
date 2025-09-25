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

package com.morpheusdata.core.providers;

import com.morpheusdata.core.Plugin;
import com.morpheusdata.model.*;
import com.morpheusdata.response.ServiceResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Represents a definition for a {@link com.morpheusdata.model.ComputeServerGroupType} so that custom cluster types can
 * be created. This could be an EKS Cluster from Amazon or a GKE Cluster from Google, or even a KVM Cluster.
 * TODO: In Development
 * @author David Estes
 * @since 0.15.3
 */
public interface ClusterProvider extends PluginProvider {
	/**
	 * Grabs the description for the Cluster Type
	 * @return String
	 */
	String getDescription();

	/**
	 * Returns the Cluster Type logo for display when a user needs to view or add this cluster. SVGs are preferred.
	 * @since 0.13.0
	 * @return Icon representation of assets stored in the src/assets of the project.
	 */
	Icon getIcon();

	/**
	 * Returns the circular Cluster Type logo for display when a user needs to view or add this cluster. SVGs are preferred.
	 * @since 1.2.12
	 * @return Icon
	 */
	Icon getCircularIcon();

	/**
	 * Clusters are refreshed periodically by the Morpheus Environment. This includes things like caching of brownfield
	 * environments and resources such as Networks, Datastores, Resource Pools, etc.
	 * @param cluster the cluster details
	 */
	ServiceResponse refresh(ComputeServerGroup cluster);

	/**
	 * Called when a cluster is being deleted. This is a chance to clean up any external resources associated with the cluster
	 * @since 1.2.12
	 * @param cluster the cluster details
	 */
	ServiceResponse deleteCluster(ComputeServerGroup cluster);

	/**
	 * Called when a server is being deleted. This is a chance to clean up any external resources associated with the server
	 * @since 1.2.13
	 * @param server the server details
	 */
	ServiceResponse deleteServer(ComputeServer server);

	/**
	 * Clusters are refreshed periodically by the Morpheus Environment. This includes things like caching of brownfield
	 * environments and resources such as Networks, Datastores, Resource Pools, etc. This represents the long term sync method that happens
	 * daily instead of every 5-10 minute cycle
	 * @param cluster the cluster details
	 */
	ServiceResponse refreshDaily(ComputeServerGroup cluster);

	/**
	 * Validates the server group configuration for a given cluster type.
	 * @since 1.2.12
	 * @param clusterType the ComputeServerGroupType
	 * @param config the configuration map for the server group
	 * @return ServiceResponse containing validation results
	 */
	ServiceResponse validateServerGroupConfig(ComputeServerGroupType clusterType, Map config);

	/**
	 * Validates the server group for a given cluster.
	 * @since 1.2.12
	 * @param cluster the ComputeServerGroup
	 * @return ServiceResponse containing validation results
	 */
	ServiceResponse validateServerGroup(ComputeServerGroup cluster);


	/**
	 * Checks if the cluster can be refreshed by Morpheus. May return false if the cluster is in a bad state or has no credentials
	 * @since 1.2.12
	 * @param cluster the ComputeServerGroup to check
	 * @return true if the cluster is syncable, false otherwise
	 */
	default boolean isRefreshable(ComputeServerGroup cluster) {
		return true;
	}

	/**
	 * Provides a Collection of OptionType inputs that define the required input fields for defining a cloud integration
	 * @since 1.2.12
	 * @return Collection of OptionType
	 */
	Collection<OptionType> getOptionTypes();

	/**
	 * Grabs available provisioning providers related to the target Cloud Plugin. Some clouds have multiple provisioning
	 * providers or some clouds allow for service based providers on top like (Docker or Kubernetes).
	 * @since 1.2.12
	 * @return Collection of ProvisionProvider
	 */
	Collection<ProvisionProvider> getAvailableProvisionProviders();

	/**
	 * Grabs the singleton instance of the provisioning provider based on the code defined in its implementation.
	 * Typically Providers are singleton and instanced in the {@link Plugin} class
	 * @since 1.2.12
	 * @param providerCode String representation of the provider short code
	 * @return the ProvisionProvider requested
	 */
	ProvisionProvider getProvisionProvider(String providerCode);

	/**
	 * Provides a Collection of {@link NetworkType} related to this ClusterProvider
	 * @since 1.2.12
	 * @return Collection of NetworkType
	 */
	Collection<NetworkType> getNetworkTypes();

	/**
	 * A list of supported NetworkProviders that can be used for networking services on this cloud
	 * @since 1.2.12
	 * @return a list of codes for network providers from either this plugin or other plugins.
	 */
	default Collection<String> getSupportedNetworkProviderCodes() { return new ArrayList<>(); }

	/**
	 * Provides a Collection of ComputeTypes that can be controllers on this ClusterProvider
	 * @since 1.2.12
	 * @return Collection of ComputeType
	 */
	Collection<ComputeType> getControllerTypes();

	/**
	 * Provides a Collection of ComputeTypes that can be workers on this ClusterProvider
	 * @since 1.2.12
	 * @return Collection of ComputeType
	 */
	Collection<ComputeType> getWorkerTypes();

	/**
	 * Provides a Collection of {@link DatastoreType} related to this ClusterProvider
	 * @since 1.2.12
	 * @return Collection of DatastoreType
	 */
	Collection<DatastoreType> getDatastoreTypes();


	/**
	 * Provides the default namespace (should be a {@link CloudPool}) for this cluster
	 * @since 1.2.12
	 * @return the default namespace for the cluster
	 */
	CloudPool getDefaultNamespace(ComputeServerGroup cluster);

	/**
	 * Indicates whether this cluster type supports cluster storage
	 * @since 1.2.12
	 * @return true if cluster storage is supported, false otherwise
	 */
	default Boolean hasClusterStorage() {
		return true;
	}

	/**
	 * Indicates whether this cluster type supports datastore storage
	 * @since 1.2.12
	 * @return true if datastore storage is supported, false otherwise
	 */
	default Boolean hasDatastoreStorage() {
		return false;
	}
	/**
	 * Indicates whether this cluster type supports a default data disk
	 * @since 1.2.12
	 * @return true if a default data disk is supported, false otherwise
	 */
	default Boolean hasDefaultDataDisk() {
		return true;
	}
	/**
	 * Indicates whether this cluster type supports master nodes
	 * @since 1.2.12
	 * @return true if master nodes are supported, false otherwise
	 */
	default Boolean hasMasters() {
		return false;
	}
	/**
	 * Indicates whether this cluster type supports worker nodes
	 * @since 1.2.12
	 * @return true if worker nodes are supported, false otherwise
	 */
	default Boolean hasWorkers() {
		return false;
	}
	/**
	 * Indicates whether this cluster type supports affinity groups
	 * @since 1.2.12
	 * @return true if affinity groups are supported, false otherwise
	 */
	default Boolean hasAffinityGroups() {
		return false;
	}
	/**
	 * Indicates whether this cluster type should use kubectl locally
	 * @since 1.2.12
	 * @return true if local kubectl should be used, false otherwise
	 */
	default Boolean useLocalKubeCtl() {
		return false;
	}
	/**
	 * Indicates whether this cluster type supports cloud scaling (e.g. auto-scaling like EKS or GKE)
	 * @since 1.2.12
	 * @return true if cloud scaling is supported, false otherwise
	 */
	default Boolean supportsCloudScaling() {
		return false;
	}

	/**
	 * Indicates whether this cluster type can delete virtual images associated with the cluster.
	 * @since 1.2.12
	 * @return true if virtual images are supported, false otherwise
	 */
	default Boolean canDeleteVirtualImages() {
		return false;
	}

	/**
	 * Indicates whether this cluster type supports custom layouts
	 * @since 1.2.12
	 * @return true if custom layouts are supported, false otherwise
	 */
	default Boolean supportsCustomLayouts() {
		return true;
	}
	/**
	 * Indicates whether this cluster type supports pricing
	 * @since 1.2.12
	 * @return true if pricing is supported, false otherwise
	 */
	default Boolean hasPricing() {
		return true;
	}

	/**
	 * By default, the cluster will use the plugin deploy service, if you'd like to use the built-in kubernetes deploy service, set this to "kubernetesDeployTargetService"
	 * @since 1.2.12
	 * @return String name of the deploy target service
	 */
	default String getDeployTargetService() {
		return null;
	}

	/**
	 * Returns the provider type for this cluster provider, such as "kubernetes", "docker", "kvm", etc.
	 * This is used in various places to manage default behaviors and configurations for the cluster type.
	 * @since 1.2.12
	 * @return String representation of the provider type
	 */
	String getProviderType();

	/**
	 * Converts a discovered cluster to a managed cluster
	 * @since 1.2.12
	 * @param cluster the ComputeServerGroup to convert
	 * @return ServiceResponse indicating success or failure
	 */
	ServiceResponse convertClusterToManaged(ComputeServerGroup cluster);

	/**
	 * Hook for finalizing any configuration on a linux compute server before it is saved, defaults to no-op
	 * @since 1.2.12
	 * @param cluster the ComputeServerGroup the server belongs to
	 * @param server the ComputeServer to finalize
	 * @param opts additional options
	 * @return ServiceResponse indicating success or failure
	 */
	default ServiceResponse finalizeLinuxComputeServer(ComputeServerGroup cluster, ComputeServer server,  Map opts) {
		return ServiceResponse.success();
	}

	/**
	 * If the cloud supports IaC based provisioning, return the mapping provider here and implement the {@link com.morpheusdata.core.providers.ProvisionProvider.IacResourceFacet} in your ProvisionProvider
	 * @since 1.2.13
	 * @return an instance of a cloud specific IacResourceMappingProvider.
	 */
	default IacResourceMappingProvider getIacResourceMappingProvider() { return null; }

	/**
	 * Grabs available backup providers related to the target cluster plugin.
	 * @since 1.2.13
	 * @return Collection of BackupProvider
	 */
	default Collection<BackupProvider> getAvailableBackupProviders() {return new ArrayList<BackupProvider>();}


}
