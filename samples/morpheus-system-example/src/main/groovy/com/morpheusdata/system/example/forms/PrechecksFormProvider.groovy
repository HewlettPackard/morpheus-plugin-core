package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class PrechecksFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    PrechecksFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-prechecks-form'
    }

    @Override
    String getName() {
        return 'Prechecks Form'
    }

    @Override
    String getFormKey() {
        return 'run-checks'
    }

    @Override
    String getFormName() {
        return 'Run System Prechecks'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
            new OptionType(
                code: 'confirmRun',
                name: 'Confirm Run',
                fieldName: 'confirmRun',
                fieldLabel: 'Run system validation checks',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                helpText: 'This will validate all configured components',
                required: true,
                displayOrder: 0
            )
        ]
    }

    @Override
    ServiceResponse validateForm(Map data, Map opts) {
        if (!data['confirmRun']) {
            return ServiceResponse.error('You must confirm to run prechecks')
        }
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitForm(Map data, Map opts) {
        // Simulate running prechecks
        return ServiceResponse.success([
            checksExecuted: true,
            allPassed: true,
            results: [
                [check: 'Network connectivity', passed: true],
                [check: 'Host availability', passed: true],
                [check: 'Storage access', passed: true],
                [check: 'Cluster health', passed: true]
            ]
        ])
    }
}
