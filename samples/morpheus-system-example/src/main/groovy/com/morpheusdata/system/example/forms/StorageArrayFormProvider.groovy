package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class StorageArrayFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    StorageArrayFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-storage-array-form'
    }

    @Override
    String getName() {
        return 'Storage Array Form'
    }

    @Override
    String getFormKey() {
        return 'storage-arrays'
    }

    @Override
    String getFormName() {
        return 'Storage Arrays'
    }

    @Override
    Collection<OptionType> getFormFields() {
        return [
            new OptionType(
                code: 'storageType',
                name: 'Storage Type',
                fieldName: 'storageType',
                fieldLabel: 'Storage Type',
                fieldContext: 'config',
                inputType: OptionType.InputType.SELECT,
                optionSource: 'arcus.storageTypes',
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
