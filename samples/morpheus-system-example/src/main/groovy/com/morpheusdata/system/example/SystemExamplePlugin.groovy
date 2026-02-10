package com.morpheusdata.system.example

import com.morpheusdata.core.Plugin
import com.morpheusdata.model.Icon
import com.morpheusdata.system.example.datasets.StorageTypeDatasetProvider
import com.morpheusdata.system.example.workflow.ArcusSystemConfigurationWorkflowProvider
import com.morpheusdata.system.example.wizards.*

/**
 * Example System Provider Plugin demonstrating configuration workflow workflow
 * Based on the mip-arcus-10 system configuration screenshot
 */
class SystemExamplePlugin extends Plugin {

    @Override
    String getCode() {
        return 'morpheus-system-example-plugin'
    }

    @Override
    void initialize() {
        this.setName("System Example Plugin")
        
        // Register input type library for custom components
        this.registerProvider(new ArcusInputTypeLibraryProvider(this, morpheus))
        
        // Register dataset providers
        this.registerProvider(new StorageTypeDatasetProvider(this, morpheus))
        
        // Register system provider
        this.registerProvider(new ArcusSystemProvider(this, morpheus))
        
        // Register configuration workflow provider
        this.registerProvider(new ArcusSystemConfigurationWorkflowProvider(this, morpheus))
        
        // Register wizard providers
        this.registerProvider(new SystemConfigWizardProvider(this, morpheus))
        this.registerProvider(new SwitchConfigWizardProvider(this, morpheus))
        this.registerProvider(new HostConfigWizardProvider(this, morpheus))
        this.registerProvider(new StorageConfigWizardProvider(this, morpheus))
        this.registerProvider(new DataNetworkConfigWizardProvider(this, morpheus))
        this.registerProvider(new ClusterConfigWizardProvider(this, morpheus))
        this.registerProvider(new PrechecksWizardProvider(this, morpheus))
    }

    @Override
    void onDestroy() {
        // Plugin cleanup if needed
    }
}
