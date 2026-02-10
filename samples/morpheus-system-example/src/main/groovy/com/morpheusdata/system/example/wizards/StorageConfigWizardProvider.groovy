package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class StorageConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    StorageConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-storage-config-wizard'
    }

    @Override
    String getName() {
        return 'Storage Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'Storage Configuration'
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def storageStep = new WizardStep(
            code: 'storage-arrays',
            name: 'Storage Arrays',
            description: 'Configure storage arrays',
            displayOrder: 0
        )
        
        storageStep.optionTypes = [
            new OptionType(
                code: 'storageType',
                name: 'Storage Type',
                fieldName: 'storageType',
                fieldLabel: 'Storage Type',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'storageTypes',
                optionSourceType: 'arcus',
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'arrayCount',
                name: 'Number of Arrays',
                fieldName: 'arrayCount',
                fieldLabel: 'Number of Storage Arrays',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: true,
                displayOrder: 1
            )
        ]
        
        def capacityStep = new WizardStep(
            code: 'storage-capacity',
            name: 'Storage Capacity',
            description: 'Configure storage capacity and redundancy',
            displayOrder: 1
        )
        
        capacityStep.optionTypes = [
            new OptionType(
                code: 'totalCapacity',
                name: 'Total Capacity (TB)',
                fieldName: 'totalCapacity',
                fieldLabel: 'Total Capacity (TB)',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'redundancyLevel',
                name: 'Redundancy Level',
                fieldName: 'redundancyLevel',
                fieldLabel: 'RAID Level',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'raidLevels',
                required: true,
                displayOrder: 1
            )
        ]
        
        return [storageStep, capacityStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def arrays = wizardData['storage-arrays']
        if (!arrays) {
            formErrors.addError('storage-arrays', 'Storage array configuration is required')
        }
        
        def capacity = wizardData['storage-capacity']
        if (!capacity) {
            formErrors.addError('storage-capacity', 'Storage capacity configuration is required')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def result = [:]
        result.putAll(wizardData['storage-arrays'] ?: [:])
        result.putAll(wizardData['storage-capacity'] ?: [:])
        return ServiceResponse.success(result)
    }
}
