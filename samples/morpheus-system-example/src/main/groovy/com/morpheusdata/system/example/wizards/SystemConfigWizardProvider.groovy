package com.morpheusdata.system.example.wizards

import com.morpheusdata.core.providers.WizardProvider
import com.morpheusdata.model.FormErrors
import com.morpheusdata.model.WizardStep
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

/**
 * Wizard for configuring basic system settings
 */
class SystemConfigWizardProvider extends com.morpheusdata.system.example.BaseProvider implements WizardProvider {

    SystemConfigWizardProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-system-config-wizard'
    }

    @Override
    String getName() {
        return 'System Configuration Wizard'
    }

    @Override
    String getWizardName() {
        return 'System Configuration'
    }

    @Override
    String getWizardDescription() {
        return 'Configure basic system settings including name, location, and contact information'
    }

    @Override
    boolean showReviewStep() {
        return true
    }

    @Override
    List<WizardStep> getWizardSteps() {
        def basicInfoStep = new WizardStep(
            code: 'basic-info',
            name: 'Basic Information',
            description: 'System name and location',
            displayOrder: 0
        )
        
        basicInfoStep.optionTypes = [
            new OptionType(
                code: 'systemName',
                name: 'System Name',
                fieldName: 'systemName',
                fieldLabel: 'System Name',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'group',
                name: 'Group',
                fieldName: 'group',
                fieldLabel: 'Group',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'groups',
                required: true,
                displayOrder: 1
            )
        ]
        
        def locationStep = new WizardStep(
            code: 'location',
            name: 'Location',
            description: 'System location details',
            displayOrder: 1
        )
        
        locationStep.optionTypes = [
            new OptionType(
                code: 'country',
                name: 'Country',
                fieldName: 'country',
                fieldLabel: 'Country',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'countries',
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'datacenter',
                name: 'Datacenter',
                fieldName: 'datacenter',
                fieldLabel: 'Datacenter',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: false,
                displayOrder: 1
            ),
            new OptionType(
                code: 'rack',
                name: 'Rack',
                fieldName: 'rack',
                fieldLabel: 'Rack Location',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: false,
                displayOrder: 2
            )
        ]
        
        def contactStep = new WizardStep(
            code: 'contact',
            name: 'Contact Information',
            description: 'System administrator contact details',
            displayOrder: 2
        )
        
        contactStep.optionTypes = [
            new OptionType(
                code: 'adminName',
                name: 'Administrator Name',
                fieldName: 'adminName',
                fieldLabel: 'Administrator Name',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'adminEmail',
                name: 'Administrator Email',
                fieldName: 'adminEmail',
                fieldLabel: 'Administrator Email',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 1
            ),
            new OptionType(
                code: 'adminPhone',
                name: 'Administrator Phone',
                fieldName: 'adminPhone',
                fieldLabel: 'Administrator Phone',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: false,
                displayOrder: 2
            )
        ]
        
        return [basicInfoStep, locationStep, contactStep]
    }

    @Override
    ServiceResponse validateWizardStep(String stepCode, Map stepData, Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        if (stepCode == 'basic-info') {
            if (!stepData['systemName']) {
                formErrors.addError('systemName', 'System name is required')
            } else if (stepData['systemName'].length() < 3) {
                formErrors.addError('systemName', 'System name must be at least 3 characters')
            }
            if (!stepData['group']) {
                formErrors.addError('group', 'Group is required')
            }
        } else if (stepCode == 'location') {
            if (!stepData['country']) {
                formErrors.addError('country', 'Country is required')
            }
        } else if (stepCode == 'contact') {
            if (!stepData['adminName']) {
                formErrors.addError('adminName', 'Administrator name is required')
            }
            if (!stepData['adminEmail']) {
                formErrors.addError('adminEmail', 'Administrator email is required')
            } else if (!isValidEmail(stepData['adminEmail'])) {
                formErrors.addError('adminEmail', 'Invalid email format')
            }
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse validateWizard(Map wizardData, Map opts) {
        FormErrors formErrors = new FormErrors()
        
        // Final cross-step validation
        def basicInfo = wizardData['basic-info']
        def location = wizardData['location']
        def contact = wizardData['contact']
        
        if (!basicInfo) {
            formErrors.addError('basic-info', 'Basic information is required')
        }
        if (!location) {
            formErrors.addError('location', 'Location information is required')
        }
        if (!contact) {
            formErrors.addError('contact', 'Contact information is required')
        }
        
        if (formErrors.hasErrors()) {
            return ServiceResponse.error('Validation failed', null, formErrors.getErrors())
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitWizard(Map wizardData, Map opts) {
        // Combine all wizard data
        def result = [:]
        result.putAll(wizardData['basic-info'] ?: [:])
        result.putAll(wizardData['location'] ?: [:])
        result.putAll(wizardData['contact'] ?: [:])
        
        return ServiceResponse.success(result)
    }
    
    private boolean isValidEmail(String email) {
        return email ==~ /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/
    }
}
