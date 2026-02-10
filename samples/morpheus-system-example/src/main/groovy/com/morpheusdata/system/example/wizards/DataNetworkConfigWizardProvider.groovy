package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class DataNetworkConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    DataNetworkConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-datanetwork-config-wizard'
    }

    @Override
    String getName() {
        return 'Data Network Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'Data Network Configuration'
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def networkStep = new WizardStep(
            code: 'network-settings',
            name: 'Network Settings',
            description: 'Configure data network',
            displayOrder: 0
        )
        
        networkStep.optionTypes = [
            new OptionType(
                code: 'networkCIDR',
                name: 'Network CIDR',
                fieldName: 'networkCIDR',
                fieldLabel: 'Network CIDR',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                placeHolder: '10.0.0.0/24',
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'gateway',
                name: 'Gateway',
                fieldName: 'gateway',
                fieldLabel: 'Default Gateway',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 1
            )
        ]
        
        def vlanStep = new WizardStep(
            code: 'vlan-settings',
            name: 'VLAN Configuration',
            description: 'Configure VLANs for data network',
            displayOrder: 1
        )
        
        vlanStep.optionTypes = [
            new OptionType(
                code: 'dataVLAN',
                name: 'Data VLAN',
                fieldName: 'dataVLAN',
                fieldLabel: 'Data VLAN ID',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'storageVLAN',
                name: 'Storage VLAN',
                fieldName: 'storageVLAN',
                fieldLabel: 'Storage VLAN ID',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: false,
                displayOrder: 1
            )
        ]
        
        return [networkStep, vlanStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def network = wizardData['network-settings']
        if (!network) {
            formErrors.addError('network-settings', 'Network settings are required')
        }
        
        def vlan = wizardData['vlan-settings']
        if (!vlan) {
            formErrors.addError('vlan-settings', 'VLAN configuration is required')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def result = [:]
        result.putAll(wizardData['network-settings'] ?: [:])
        result.putAll(wizardData['vlan-settings'] ?: [:])
        return ServiceResponse.success(result)
    }
}
