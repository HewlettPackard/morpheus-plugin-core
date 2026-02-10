package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class ClusterConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    ClusterConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-cluster-config-wizard'
    }

    @Override
    String getName() {
        return 'Cluster Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'Cluster Configuration'
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def CUSTOM_SLIDER = new OptionType.InputType("custom-slider")
        
        def clusterStep = new WizardStep(
            code: 'cluster-settings',
            name: 'Cluster Settings',
            description: 'Basic cluster configuration',
            displayOrder: 0
        )
        
        clusterStep.optionTypes = [
            new OptionType(
                code: 'clusterName',
                name: 'Cluster Name',
                fieldName: 'clusterName',
                fieldLabel: 'Cluster Name',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'highAvailability',
                name: 'High Availability',
                fieldName: 'highAvailability',
                fieldLabel: 'Enable High Availability',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                defaultValue: true,
                displayOrder: 1
            )
        ]
        
        def resourcesStep = new WizardStep(
            code: 'cluster-resources',
            name: 'Resource Allocation',
            description: 'Configure cluster resources',
            displayOrder: 1
        )
        
        resourcesStep.optionTypes = [
            new OptionType(
                code: 'replicaCount',
                name: 'Replica Count',
                fieldName: 'replicaCount',
                fieldLabel: 'Number of Replicas',
                fieldContext: 'config',
                inputType: CUSTOM_SLIDER,
                defaultValue: '3',
                helpText: 'Number of cluster replicas for high availability',
                config: [
                    min: 1,
                    max: 9,
                    step: 1,
                    defaultValue: 3,
                    showValue: true,
                    unit: 'replicas'
                ],
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'cpuAllocation',
                name: 'CPU Allocation',
                fieldName: 'cpuAllocation',
                fieldLabel: 'CPU Allocation Per Node',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                defaultValue: '4',
                helpText: 'Number of CPU cores per cluster node',
                required: true,
                displayOrder: 1
            ),
            new OptionType(
                code: 'memoryAllocation',
                name: 'Memory Allocation',
                fieldName: 'memoryAllocation',
                fieldLabel: 'Memory Per Node (GB)',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                defaultValue: '32',
                required: true,
                displayOrder: 2
            )
        ]
        
        return [clusterStep, resourcesStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def cluster = wizardData['cluster-settings']
        if (!cluster || !cluster['clusterName']) {
            formErrors.addError('clusterName', 'Cluster name is required')
        }
        
        def resources = wizardData['cluster-resources']
        if (!resources) {
            formErrors.addError('cluster-resources', 'Resource allocation is required')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def result = [:]
        result.putAll(wizardData['cluster-settings'] ?: [:])
        result.putAll(wizardData['cluster-resources'] ?: [:])
        return ServiceResponse.success(result)
    }
}
