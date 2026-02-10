package com.morpheusdata.system.example.workflow

import com.morpheusdata.core.providers.ConfigurationWorkflowProvider
import com.morpheusdata.model.ConfigurationWorkflowStep
import com.morpheusdata.response.ServiceResponse

/**
 * Configuration workflow provider for Arcus system setup
 * Manages the complete configuration workflow from system setup through prechecks
 */
class ArcusSystemConfigurationWorkflowProvider extends com.morpheusdata.system.example.BaseProvider implements ConfigurationWorkflowProvider {

    ArcusSystemConfigurationWorkflowProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-system-configuration-workflow'
    }

    @Override
    String getName() {
        return 'Arcus System Configuration Workflow'
    }

    @Override
    String getWorkflowName() {
        return 'Arcus System Configuration'
    }

    @Override
    String getWorkflowDescription() {
        return 'Complete configuration workflow workflow for configuring an Arcus infrastructure system'
    }

    @Override
    List<ConfigurationWorkflowStep> getWorkflowSteps() {
        // Note: Wizard providers are referenced by code using the wizardCode field
        // The framework will look up the appropriate wizard provider based on the wizardCode
        return [
            new ConfigurationWorkflowStep(
                code: 'system',
                name: 'System',
                description: 'Configure basic system settings',
                displayOrder: 0,
                wizardCode: 'arcus-system-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'switches',
                name: 'Switches',
                description: 'Configure network switches',
                displayOrder: 1,
                wizardCode: 'arcus-switch-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'hosts',
                name: 'Hosts',
                description: 'Configure host servers',
                displayOrder: 2,
                wizardCode: 'arcus-host-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'storage',
                name: 'Storage',
                description: 'Configure storage arrays',
                displayOrder: 3,
                wizardCode: 'arcus-storage-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'data-network',
                name: 'Data Network',
                description: 'Configure data network settings',
                displayOrder: 4,
                wizardCode: 'arcus-datanetwork-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'cluster',
                name: 'Cluster',
                description: 'Configure cluster settings',
                displayOrder: 5,
                wizardCode: 'arcus-cluster-config-wizard'
            ),
            new ConfigurationWorkflowStep(
                code: 'prechecks',
                name: 'Prechecks',
                description: 'Run system validation prechecks',
                displayOrder: 6,
                wizardCode: 'arcus-prechecks-wizard'
            )
        ]
    }

    @Override
    ServiceResponse saveStepConfiguration(String stepCode, Map stepData, Map currentState, Map opts) {
        // Merge the step data into the current state
        if (!currentState) {
            currentState = [:]
        }
        currentState[stepCode] = stepData
        currentState['lastCompletedStep'] = stepCode
        currentState['lastUpdated'] = new Date()
        
        return ServiceResponse.success(currentState)
    }

    @Override
    ServiceResponse updateParentState(Object parentObject, Map configurationWorkflowState, Map opts) {
        // Update the System model's configurationWorkflowState field
        if (parentObject instanceof com.morpheusdata.model.system.System) {
            def system = parentObject as com.morpheusdata.model.system.System
            
            // Convert configurationWorkflowState to JSON string
            def jsonState = groovy.json.JsonOutput.toJson(configurationWorkflowState)
            system.setConfigurationWorkflowState(jsonState)
            
            return ServiceResponse.success()
        }
        
        return ServiceResponse.error('Invalid parent object type')
    }

    @Override
    ServiceResponse validateConfigurationWorkflow(Map configurationWorkflowState, Object parentObject, Map opts) {
        def errors = []
        
        // Validate that all required steps are completed
        def requiredSteps = ['system', 'switches', 'hosts', 'storage', 'data-network', 'cluster']
        requiredSteps.each { step ->
            if (!configurationWorkflowState[step]) {
                errors << "Step '${step}' must be completed before submission"
            }
        }
        
        // Validate system configuration
        if (configurationWorkflowState['system']) {
            def systemConfig = configurationWorkflowState['system']
            if (!systemConfig['systemName']) {
                errors << 'System name is required'
            }
        }
        
        if (errors) {
            return ServiceResponse.error(errors.join(', '))
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitOrchestration(Map configurationWorkflowState, Object parentObject, Map opts) {
        // This would typically call another long-running method to execute the setup
        // For now, we'll just mark it as submitted
        
        if (parentObject instanceof com.morpheusdata.model.system.System) {
            def system = parentObject as com.morpheusdata.model.system.System
            
            // Update configurationWorkflowState with submission info
            configurationWorkflowState['status'] = 'submitted'
            configurationWorkflowState['submittedDate'] = new Date()
            configurationWorkflowState['completed'] = true
            
            // Update the system's configurationWorkflowState
            def jsonState = groovy.json.JsonOutput.toJson(configurationWorkflowState)
            system.setConfigurationWorkflowState(jsonState)
            
            // In a real implementation, this would trigger the actual setup process
            // For example: executeSystemSetup(system, configurationWorkflowState)
            
            return ServiceResponse.success([
                message: 'Arcus system configuration submitted successfully',
                systemId: system.getId()
            ])
        }
        
        return ServiceResponse.error('Invalid parent object type')
    }

    @Override
    Map getConfigurationWorkflowState(Object parentObject, Map opts) {
        if (parentObject instanceof com.morpheusdata.model.system.System) {
            def system = parentObject as com.morpheusdata.model.system.System
            def stateJson = system.getConfigurationWorkflowState()
            
            if (stateJson) {
                def slurper = new groovy.json.JsonSlurper()
                return slurper.parseText(stateJson)
            }
        }
        
        return [:]
    }

    @Override
    boolean shouldShowStep(ConfigurationWorkflowStep step, Map configurationWorkflowState, Map opts) {
        // All steps are always shown for this configurationWorkflow
        // Could add conditional logic here, for example:
        // - Skip cluster step if less than 2 hosts configured
        // - Skip prechecks until all other steps are completed
        return true
    }
}
