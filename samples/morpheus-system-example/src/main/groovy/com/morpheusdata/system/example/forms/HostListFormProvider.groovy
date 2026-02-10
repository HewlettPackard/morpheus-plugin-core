package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class HostListFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    HostListFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-host-list-form'
    }

    @Override
    String getName() {
        return 'Host List Form'
    }

    @Override
    String getFormKey() {
        return 'host-list'
    }

    @Override
    String getFormName() {
        return 'Host Configuration'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
            new OptionType(
                code: 'hostCount',
                name: 'Number of Hosts',
                fieldName: 'hostCount',
                fieldLabel: 'Number of Hosts',
                fieldContext: 'config',
                inputType: OptionType.InputType.NUMBER,
                required: true,
                displayOrder: 0
            )
        ]
    }

    @Override
    ServiceResponse validateForm(Map data, Map opts) {
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitForm(Map data, Map opts) {
        return ServiceResponse.success(data)
    }
}
