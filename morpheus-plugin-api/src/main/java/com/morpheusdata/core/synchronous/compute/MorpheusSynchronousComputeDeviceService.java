package com.morpheusdata.core.synchronous.compute;

import com.morpheusdata.core.MorpheusSynchronousDataService;
import com.morpheusdata.model.ComputeDevice;
import com.morpheusdata.model.ComputeDeviceType;

/**
 * Context methods for {@link ComputeDevice} in Morpheus.
 * @since 1.2.11
 * @author Mike Carlin
 */
public interface MorpheusSynchronousComputeDeviceService extends MorpheusSynchronousDataService<ComputeDevice, ComputeDevice> {

	/**
	 * Returns the service used for performing updates or queries on {@link ComputeDeviceType} related assets within Morpheus.
	 * @since 1.2.11
	 * @return An instance of the MorpheusSynchronousComputeDeviceTypeService Context
	 */
	MorpheusSynchronousComputeDeviceTypeService getType();
}
