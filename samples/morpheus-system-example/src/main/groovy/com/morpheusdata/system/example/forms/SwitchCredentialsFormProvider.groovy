package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class SwitchCredentialsFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    SwitchCredentialsFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-switch-credentials-form'
    }

    @Override
    String getName() {
        return 'Switch Credentials Form'
    }

    @Override
    String getFormKey() {
        return 'switch-credentials'
    }

    @Override
    String getFormName() {
        return 'Switch Credentials'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
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
    }

    @Override
    ServiceResponse validateForm(Map data, Map opts) {
        if (!data['username']) {
            return ServiceResponse.error('Username is required')
        }
        return ServiceResponse.success()
    }

    @Override
    ServiceResponse submitForm(Map data, Map opts) {
        return ServiceResponse.success(data)
    }
}
