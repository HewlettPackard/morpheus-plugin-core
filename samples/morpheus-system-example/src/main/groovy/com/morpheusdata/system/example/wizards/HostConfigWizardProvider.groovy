package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class HostConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    HostConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-host-config-wizard'
    }

    @Override
    String getName() {
        return 'Host Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'Host Configuration'
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def hostStep = new WizardStep(
            code: 'host-list',
            name: 'Host List',
            description: 'Configure host servers'
        )
        
        hostStep.optionTypes = [
            new OptionType(
                code: 'hostCount',
                name: 'Number of Hosts',
                fieldName: 'hostCount',
                fieldLabel: 'Number of Hosts',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'hostPrefix',
                name: 'Host Name Prefix',
                fieldName: 'hostPrefix',
                fieldLabel: 'Host Name Prefix',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: false,
                displayOrder: 1
            )
        ]
        
        def credentialsStep = new WizardStep(
            code: 'host-credentials',
            name: 'Host Credentials',
            description: 'IPMI/iLO credentials for host management'
        )
        
        credentialsStep.optionTypes = [
            new OptionType(
                code: 'ipmiUsername',
                name: 'IPMI Username',
                fieldName: 'ipmiUsername',
                fieldLabel: 'IPMI Username',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'ipmiPassword',
                name: 'IPMI Password',
                fieldName: 'ipmiPassword',
                fieldLabel: 'IPMI Password',
                fieldContext: 'config',
                inputType: OptionType.InputType.PASSWORD,
                required: true,
                displayOrder: 1
            )
        ]
        
        def networkStep = new WizardStep(
            code: 'host-network',
            name: 'Network Configuration',
            description: 'Host network settings'
        )
        
        networkStep.optionTypes = [
            new OptionType(
                code: 'networkSubnet',
                name: 'Management Subnet',
                fieldName: 'networkSubnet',
                fieldLabel: 'Management Subnet (CIDR)',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
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
        
        return [hostStep, credentialsStep, networkStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def hostList = wizardData['host-list']
        if (!hostList || !hostList['hostCount']) {
            formErrors.addError('hostCount', 'Host count is required')
        }
        
        def credentials = wizardData['host-credentials']
        if (!credentials) {
            formErrors.addError('host-credentials', 'Host credentials are required')
        }
        
        def network = wizardData['host-network']
        if (!network) {
            formErrors.addError('host-network', 'Network configuration is required')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def result = [:]
        result.putAll(wizardData['host-list'] ?: [:])
        result.putAll(wizardData['host-credentials'] ?: [:])
        result.putAll(wizardData['host-network'] ?: [:])
        return ServiceResponse.success(result)
    }
}
