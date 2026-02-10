package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class DataNetworkFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    DataNetworkFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-datanetwork-form'
    }

    @Override
    String getName() {
        return 'Data Network Form'
    }

    @Override
    String getFormKey() {
        return 'network-settings'
    }

    @Override
    String getFormName() {
        return 'Data Network Settings'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
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
