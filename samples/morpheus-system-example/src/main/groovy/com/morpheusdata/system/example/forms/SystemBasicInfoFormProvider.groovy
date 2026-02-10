package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

/**
 * Form for basic system information
 */
class SystemBasicInfoFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    SystemBasicInfoFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-system-basic-info-form'
    }

    @Override
    String getName() {
        return 'System Basic Information Form'
    }

    @Override
    String getFormKey() {
        return 'system-basic-info'
    }

    @Override
    String getFormName() {
        return 'Basic Information'
    }

    @Override
    String getFormDescription() {
        return 'Enter basic system information'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
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
            ),
            new OptionType(
                code: 'country',
                name: 'Country',
                fieldName: 'country',
                fieldLabel: 'Country',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'countries',
                required: true,
                displayOrder: 2
            )
        ]
    }

    @Override
    ServiceResponse validateForm(Map data, Map opts) {
        def errors = []
        
        if (!data['systemName']) {
            errors << 'System name is required'
        }
        if (!data['group']) {
            errors << 'Group is required'
        }
        if (!data['country']) {
            errors << 'Country is required'
        }
        
        if (errors) {
            return ServiceResponse.error(errors.join(', '))
        }
        
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitForm(Map data, Map opts) {
        return ServiceResponse.success(data)
    }
}
