package com.morpheusdata.core;

import com.morpheusdata.model.ComputeTypePackage;

/**
 * Context methods for managing compute type packages
 * @author aclement
 * @since 1.2.6
 */
public interface MorpheusSynchronousComputeTypePackageService extends MorpheusSynchronousDataService<ComputeTypePackage, ComputeTypePackage> {

	MorpheusSynchronousClusterPackageService getClusterPackage();

}
