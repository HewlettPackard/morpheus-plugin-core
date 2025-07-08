package com.morpheusdata.core.providers;

import com.morpheusdata.model.Icon;
import com.morpheusdata.model.system.*;
import com.morpheusdata.model.system.System;
import com.morpheusdata.response.ServiceResponse;

import java.util.Collection;

/**
 * Provides a set of interface methods for integration with a vme manager {@link System}
 * @since 1.2.10
 */
public interface SystemProvider extends PluginProvider {
	/**
	 * Grabs the description for the SystemProvider
	 * @return String
	 */
	String getDescription();

	/**
	 * Returns the logo for display. SVGs are preferred.
	 * @return Icon representation of assets stored in the src/assets of the project.
	 */
	Icon getIcon();

	/**
	 * Provides a Collection of {@link SystemComponentType} which are supported for this system provider
	 */
	Collection<SystemComponentType> getSystemComponentTypes();

	/**
	 * Providers a collection of system types that are supported by this provider
	 */
	Collection<SystemType> getSystemTypes();

	/**
	 * Provides a collection of {@link SystemTypeLayout} which are supported by this system provider.  These layouts
	 * map the resources which are maintained, configured, updated by the provider implementation
	 */
	Collection<SystemTypeLayout> getSystemTypeLayouts();

	/**
	 * This phase is run prior to exeucting the system initialization workflows.  In this phase you can perform
	 * any form up pre initialization checks, additional input validations, etc.
	 * @param system
	 * @return
	 */
	default ServiceResponse prepareInitializeSystem(System system, SystemRequest systemRequest) { return ServiceResponse.success(); }

	/**
	 * This method is executed post initialization workflows have been executed. Not required to implement.
	 * @param system
	 * @return
	 */
	default ServiceResponse initializeSystem(System system, SystemRequest systemRequest) { return ServiceResponse.success(); }

	/**
	 * This phase is run prior to executing the system update workflows.  Perform any pre checks, validations, or
	 * resource auditing here.
	 * @param system
	 * @return
	 */
	default ServiceResponse prepareUpdateSystem(System system, SystemRequest systemRequest) { return ServiceResponse.success(); }

	/**
	 * This method is executed post update workflows for the given system.
	 * @param system
	 * @return
	 */
	default ServiceResponse updateSystem(System system, SystemRequest systemRequest) { return ServiceResponse.success(); }

	/**
	 * Perform any cleanup/state reset operations required on removal of a system
	 * @param system
	 * @return
	 */
	default ServiceResponse deleteSystem(System system) { return ServiceResponse.success(); }

	/**
	 * This method is bound to a periodic job that is executed by the VME Manager.
	 * @param system
	 * @return
	 */
	default ServiceResponse refreshSystem(System system) { return ServiceResponse.success(); }

	/**
	 * This method is called when adding an additional component to an existing system (adding a new host, storage array
	 * etc)
	 * @param system
	 * @param systemRequest
	 * @param componentType
	 * @return
	 */
	default ServiceResponse addSystemComponent(System system, SystemRequest systemRequest, SystemComponentType componentType) { return ServiceResponse.success(); }
}
