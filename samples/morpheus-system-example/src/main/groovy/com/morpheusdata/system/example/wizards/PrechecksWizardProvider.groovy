package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class PrechecksWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    PrechecksWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-prechecks-wizard'
    }

    @Override
    String getName() {
        return 'Prechecks Wizard'
    }

    @Override
    String getWizardName() {
        return 'System Prechecks'
    }

    @Override
    boolean showReviewStep() {
        return false // Prechecks don't need a review
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def checksStep = new WizardStep(
            code: 'check-selection',
            name: 'Check Selection',
            description: 'Select validation checks to run',
            displayOrder: 0
        )
        
        checksStep.optionTypes = [
            new OptionType(
                code: 'networkChecks',
                name: 'Network Checks',
                fieldName: 'networkChecks',
                fieldLabel: 'Run Network Connectivity Checks',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                defaultValue: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'storageChecks',
                name: 'Storage Checks',
                fieldName: 'storageChecks',
                fieldLabel: 'Run Storage Validation Checks',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                defaultValue: true,
                displayOrder: 1
            ),
            new OptionType(
                code: 'hostChecks',
                name: 'Host Checks',
                fieldName: 'hostChecks',
                fieldLabel: 'Run Host Readiness Checks',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                defaultValue: true,
                displayOrder: 2
            )
        ]
        
        def executionStep = new WizardStep(
            code: 'run-checks',
            name: 'Run Checks',
            description: 'Execute system validation',
            displayOrder: 1
        )
        
        executionStep.optionTypes = [
            new OptionType(
                code: 'confirmRun',
                name: 'Confirm Run',
                fieldName: 'confirmRun',
                fieldLabel: 'I understand that validation checks will be executed',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                helpText: 'This will validate all selected components',
                required: true,
                displayOrder: 0
            )
        ]
        
        return [checksStep, executionStep]
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        def execution = wizardData['run-checks']
        if (!execution || !execution['confirmRun']) {
            formErrors.addError('confirmRun', 'You must confirm to run the checks')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        def selection = wizardData['check-selection'] ?: [:]
        
        // In a real implementation, this would run actual prechecks based on selection
        return ServiceResponse.success([
            checksRun: true,
            networkChecks: selection['networkChecks'] ?: false,
            storageChecks: selection['storageChecks'] ?: false,
            hostChecks: selection['hostChecks'] ?: false,
            passed: true,
            message: 'All selected prechecks passed successfully'
        ])
    }
}
