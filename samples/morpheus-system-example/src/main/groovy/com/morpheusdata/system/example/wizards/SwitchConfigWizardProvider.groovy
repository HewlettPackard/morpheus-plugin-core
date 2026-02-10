package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

/**
 * Wizard for configuring network switches
 */
class SwitchConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    SwitchConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-switch-config-wizard'
    }

    @Override
    String getName() {
        return 'Switch Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'Switch Configuration'
    }

    @Override
    String getWizardDescription() {
        return 'Configure network switches for the system'
    }

    @Override
    boolean showReviewStep() {
        return true
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def credentialsStep = new WizardStep(
            code: 'switch-credentials',
            name: 'Switch Credentials',
            description: 'Username and password for switch access',
            displayOrder: 0
        )
        
        credentialsStep.optionTypes = [
            new OptionType(
                code: 'username',
                name: 'Username',
                fieldName: 'username',
                fieldLabel: 'Switch Username',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'password',
                name: 'Password',
                fieldName: 'password',
                fieldLabel: 'Switch Password',
                fieldContext: 'config',
                inputType: OptionType.InputType.PASSWORD,
                required: false,
                displayOrder: 1
            )
        ]
        
        def networkStep = new WizardStep(
            code: 'switch-network',
            name: 'Network Configuration',
            description: 'Management IP and VLAN settings',
            displayOrder: 1
        )
        
        networkStep.optionTypes = [
            new OptionType(
                code: 'managementIP',
                name: 'Management IP',
                fieldName: 'managementIP',
                fieldLabel: 'Management IP Address',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'managementVLAN',
                name: 'Management VLAN',
                fieldName: 'managementVLAN',
                fieldLabel: 'Management VLAN ID',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: false,
                displayOrder: 1
            )
        ]
        
        return [credentialsStep, networkStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def credentials = wizardData['switch-credentials']
        if (!credentials) {
            formErrors.addError('switch-credentials', 'Switch credentials are required')
        } else {
            if (!credentials['username']) {
                formErrors.addError('username', 'Username is required')
            }
        }
        
        def network = wizardData['switch-network']
        if (!network) {
            formErrors.addError('switch-network', 'Network configuration is required')
        } else {
            if (!network['managementIP']) {
                formErrors.addError('managementIP', 'Management IP is required')
            }
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def result = [:]
        result.putAll(wizardData['switch-credentials'] ?: [:])
        result.putAll(wizardData['switch-network'] ?: [:])
        return ServiceResponse.success(result)
    }
}
