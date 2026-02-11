package com.morpheusdata.system.example.forms

import com.morpheusdata.core.providers.FormProvider
import com.morpheusdata.model.OptionType
import com.morpheusdata.response.ServiceResponse

class ClusterSettingsFormProvider extends com.morpheusdata.system.example.BaseProvider implements FormProvider {

    ClusterSettingsFormProvider(com.morpheusdata.core.Plugin plugin, com.morpheusdata.core.MorpheusContext morpheusContext) {
        super(plugin, morpheusContext)
    }

    @Override
    String getCode() {
        return 'arcus-cluster-settings-form'
    }

    @Override
    String getName() {
        return 'Cluster Settings Form'
    }

    @Override
    String getFormKey() {
        return 'cluster-settings'
    }

    @Override
    String getFormName() {
        return 'Cluster Settings'
    }

    @Override
    Collection<OptionType> getFormFields() {
        // Define custom input type for the slider
        def CUSTOM_SLIDER = new OptionType.InputType("custom-slider")
        
        return [
            new OptionType(
                code: 'clusterName',
                name: 'Cluster Name',
                fieldName: 'clusterName',
                fieldLabel: 'Cluster Name',
                fieldContext: 'config',
                inputType: OptionType.InputType.TEXT,
                required: true,
                displayOrder: 0
            ),
            new OptionType(
                code: 'highAvailability',
                name: 'High Availability',
                fieldName: 'highAvailability',
                fieldLabel: 'Enable High Availability',
                fieldContext: 'config',
                inputType: OptionType.InputType.CHECKBOX,
                defaultValue: true,
                displayOrder: 1
            ),
            new OptionType(
                code: 'replicaCount',
                name: 'Replica Count',
                fieldName: 'replicaCount',
                fieldLabel: 'Number of Replicas',
                fieldContext: 'config',
                inputType: CUSTOM_SLIDER,  // Use custom slider input type
                defaultValue: '3',
                helpText: 'Number of cluster replicas for high availability',
                config: groovy.json.JsonOutput.toJson([
                    min: 1,
                    max: 9,
                    step: 1,
                    defaultValue: 3,
                    showValue: true,
                    unit: 'replicas'
                ]),
                required: true,
                displayOrder: 2
            ),
            new OptionType(
                code: 'cpuAllocation',
                name: 'CPU Allocation',
                fieldName: 'cpuAllocation',
                fieldLabel: 'CPU Allocation Per Node',
                fieldContext: 'config',
                inputType: CUSTOM_SLIDER,  // Another custom slider
                defaultValue: '4',
                helpText: 'Number of CPU cores allocated to each cluster node',
                config: groovy.json.JsonOutput.toJson([
                    min: 1,
                    max: 32,
                    step: 1,
                    defaultValue: 4,
                    showValue: true,
                    unit: 'cores'
                ]),
                required: true,
                displayOrder: 3
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
