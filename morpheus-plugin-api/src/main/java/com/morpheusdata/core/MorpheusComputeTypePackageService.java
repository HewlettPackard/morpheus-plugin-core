package com.morpheusdata.core;

import com.morpheusdata.model.ComputeTypePackage;

/**
 * Context methods for managing compute type packages
 * @author aclement
 * @since 1.2.6
 */
public interface MorpheusComputeTypePackageService extends MorpheusDataService<ComputeTypePackage, ComputeTypePackage> {

	MorpheusClusterPackageService getClusterPackage();

}
