package com.morpheusdata.system.example

import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.core.providers.SystemProvider
import com.morpheusdata.model.Icon
import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.ComputeServerGroup
import com.morpheusdata.model.Network
import com.morpheusdata.model.NetworkSwitch
import com.morpheusdata.model.StorageServer
import com.morpheusdata.model.system.*
import com.morpheusdata.model.system.System
import com.morpheusdata.response.ServiceResponse
import com.morpheusdata.system.example.workflow.ArcusSystemConfigurationWorkflowProvider

/**
 * System Provider for Arcus systems
 */
class ArcusSystemProvider implements SystemProvider {

    Plugin plugin
    MorpheusContext morpheusContext

    ArcusSystemProvider(Plugin plugin, MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
    }

    @Override
    MorpheusContext getMorpheus() {
        return morpheusContext
    }

    @Override
    String getCode() {
        return 'arcus-system-provider'
    }

    @Override
    String getName() {
        return 'Arcus System Provider'
    }

    @Override
    String getDescription() {
        return 'Provider for managing Arcus infrastructure systems with configuration workflow'
    }

    @Override
    Icon getIcon() {
        return new Icon(path: "system-icon.svg", darkPath: "system-icon-dark.svg")
    }

    @Override
    Collection<SystemComponentType> getSystemComponentTypes() {
        return [
            createComponentType('arcus-switch', 'Arcus Switch', 'Network switch component', NetworkSwitch.class),
            createComponentType('arcus-host', 'Arcus Host', 'Host/server component', ComputeServer.class),
            createComponentType('arcus-storage', 'Arcus Storage', 'Storage array component', StorageServer.class),
            createComponentType('arcus-network', 'Arcus Data Network', 'Data network component', Network.class),
            createComponentType('arcus-cluster', 'Arcus Cluster', 'Cluster management component', ComputeServerGroup.class)
        ]
    }

    @Override
    Collection<SystemType> getSystemTypes() {
        SystemType systemType = new SystemType(
            code: 'arcus-system',
            name: 'Arcus Infrastructure System',
            description: 'Complete Arcus infrastructure management system'
        )
        return [systemType]
    }

    @Override
    Collection<SystemTypeLayout> getSystemTypeLayouts() {
        // Get the system type
        SystemType systemType = new SystemType(
            code: 'arcus-system',
            name: 'Arcus Infrastructure System',
            description: 'Complete Arcus infrastructure management system'
        )
        
        SystemTypeLayout layout = new SystemTypeLayout(
            code: 'arcus-standard-layout',	
            name: 'Arcus Standard Layout',
            description: 'Standard layout for Arcus infrastructure systems with configuration workflow',
            version: '1.0',
            systemType: systemType,
            configurationWorkflowCode: 'arcus-system-configuration-workflow'
        )
        
        // Add component types to the layout
        layout.components = [
            createComponentType('arcus-switch', 'Arcus Switch', 'Network switch component', NetworkSwitch.class),
            createComponentType('arcus-host', 'Arcus Host', 'Host/server component', ComputeServer.class),
            createComponentType('arcus-storage', 'Arcus Storage', 'Storage array component', StorageServer.class),
            createComponentType('arcus-network', 'Arcus Data Network', 'Data network component', Network.class),
            createComponentType('arcus-cluster', 'Arcus Cluster', 'Cluster management component', ComputeServerGroup.class)
        ]

        return [layout]
    }

    @Override
    ServiceResponse prepareInitializeSystem(System system, SystemRequest systemRequest) {
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse initializeSystem(System system, SystemRequest systemRequest) {
        return ServiceResponse.success(system)
    }

    @Override
    ServiceResponse refreshSystem(System system) {
        return ServiceResponse.success()
    }

    private SystemComponentType createComponentType(String code, String name, String description, Class modelType) {
        SystemComponentType componentType = new SystemComponentType(
            code: code,
            name: name,
            description: description,
            modelType: modelType
        )
        return componentType
    }
}
