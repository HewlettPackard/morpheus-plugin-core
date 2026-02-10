# Morpheus System Example Plugin

This example plugin demonstrates how to build a complete System Provider with configuration workflow workflow support in Morpheus.

## Overview

This plugin showcases:

- **System Provider**: Implementation of the `SystemProvider` interface
- **Orchestration Provider**: Complete multi-step workflow using `OrchestrationProvider`
- **Wizard Providers**: 7 wizard implementations for each configuration workflow step
- **Form Providers**: Individual forms for data collection at each step
- **Custom Input Types**: Example custom UI component (slider) with JavaScript registration

## Architecture

The plugin implements a three-tier form/wizard/configuration workflow system based on the screenshot:

### Orchestration Flow

1. **System** - Basic system configuration (name, group, country)
2. **Switches** - Network switch credentials
3. **Hosts** - Host server configuration
4. **Storage** - Storage array settings
5. **Data Network** - Network configuration
6. **Cluster** - Cluster settings
7. **Prechecks** - System validation checks

### Components

```
SystemExamplePlugin (Main Plugin)
└── ArcusSystemProvider (SystemProvider)
    ├── SystemType: arcus-system
    └── SystemTypeLayout: arcus-standard-layout
        └── Components:
            ├── arcus-switch
            ├── arcus-host
            ├── arcus-storage
            ├── arcus-network
            └── arcus-cluster

ArcusSystemConfigurationWorkflowProvider (OrchestrationProvider)
├── Step 1: System
│   └── SystemConfigWizardProvider
│       └── SystemBasicInfoFormProvider
├── Step 2: Switches
│   └── SwitchConfigWizardProvider
│       └── SwitchCredentialsFormProvider
├── Step 3: Hosts
│   └── HostConfigWizardProvider
│       └── HostListFormProvider
├── Step 4: Storage
│   └── StorageConfigWizardProvider
│       └── StorageArrayFormProvider
├── Step 5: Data Network
│   └── DataNetworkConfigWizardProvider
│       └── DataNetworkFormProvider
├── Step 6: Cluster
│   └── ClusterConfigWizardProvider
│       └── ClusterSettingsFormProvider
└── Step 7: Prechecks
    └── PrechecksWizardProvider
        └── PrechecksFormProvider
```

## State Management

The configuration workflow state is stored as a JSON string on the `System` model's `configuration workflowState` field:

```groovy
{
  "system": {
    "systemName": "mip-arcus-10",
    "group": "Production-HPE-Systems",
    "country": "United States"
  },
  "switches": {
    "username": "switch-admin"
  },
  "hosts": {
    "hostCount": 3
  },
  // ... other steps
  "lastCompletedStep": "switches",
  "lastUpdated": "2026-01-28T..."
}
```

## Key Features

### Progressive State Saving

Each step's configuration is saved individually via `saveStepConfiguration()`, then the complete state is persisted to the parent System object via `updateParentState()`.

### Conditional Steps

The `shouldShowStep()` method allows steps to be conditionally shown/hidden based on previous configuration.

### Review Steps

Some wizards (like Switches) show a review step before submission, while others (like System and Prechecks) skip directly to submission.

### Validation

Three levels of validation:

1. **Form-level**: Each FormProvider validates its own fields
2. **Wizard-level**: Each WizardProvider validates cross-form data
3. **Orchestration-level**: The OrchestrationProvider validates the complete workflow

## Building the Plugin

```bash
cd samples/morpheus-system-example
../../gradlew shadowJar
```

The built plugin JAR will be in `build/libs/morpheus-system-example-1.0.0.jar`

## Installing

1. Copy the JAR to your Morpheus appliance
2. Place it in `/var/opt/morpheus/plugins/`
3. Restart Morpheus: `morpheus-ctl restart`

## Usage

1. Navigate to Infrastructure > Systems
2. Click "Add System"
3. Select "Arcus Infrastructure System"
4. Choose "Arcus Standard Layout"
5. Follow the configuration workflow workflow through each step
6. Review the summary showing progress (2 of 7 complete, etc.)
7. Click "Setup" to submit the configuration workflow

## Extending

To add more steps to the configuration workflow:

1. Create a new FormProvider in `forms/`
2. Create a new WizardProvider in `wizards/` that uses your form
3. Add a new OrchestrationStep in `ArcusSystemConfigurationWorkflowProvider.getOrchestrationSteps()`
4. Update validation logic in `validateConfigurationWorkflow()`

## Files

- **Plugin Core**:
  - `SystemExamplePlugin.groovy` - Main plugin class
  - `ArcusSystemProvider.groovy` - System provider implementation

- **Orchestration**:
  - `configuration workflow/ArcusSystemConfigurationWorkflowProvider.groovy` - Orchestration coordinator

- **Wizards** (`wizards/`):
  - `SystemConfigWizardProvider.groovy`
  - `SwitchConfigWizardProvider.groovy`
  - `HostConfigWizardProvider.groovy`
  - `StorageConfigWizardProvider.groovy`
  - `DataNetworkConfigWizardProvider.groovy`
  - `ClusterConfigWizardProvider.groovy`
  - `PrechecksWizardProvider.groovy`

- **Forms** (`forms/`):
  - `SystemBasicInfoFormProvider.groovy`
  - `SwitchCredentialsFormProvider.groovy`
  - `HostListFormProvider.groovy`
  - `StorageArrayFormProvider.groovy`
  - `DataNetworkFormProvider.groovy`
  - `ClusterSettingsFormProvider.groovy` - **Demonstrates custom input types**
  - `PrechecksFormProvider.groovy`

- **Assets** (`src/main/resources/assets/`):
  - `custom-slider.js` - Custom slider UI component

## Custom Input Types

This plugin demonstrates how to create custom OptionType input types. See `ClusterSettingsFormProvider.groovy` for usage:

```groovy
// Define a custom input type
def CUSTOM_SLIDER = new OptionType.InputType("custom-slider")

// Use it in an OptionType
new OptionType(
    code: 'replicaCount',
    fieldName: 'replicaCount',
    fieldLabel: 'Number of Replicas',
    inputType: CUSTOM_SLIDER,  // Custom type
    config: [
        min: 1,
        max: 9,
        defaultValue: 3,
        unit: 'replicas'
    ]
)
```

The JavaScript component in `custom-slider.js` registers with Morpheus:

```javascript
window.Morpheus.components.registry.register("custom-slider", CustomSlider, {
	type: "optionType",
	name: "Custom Slider",
	// ... configuration
});
```

**Key Points**:

- The InputType string ("custom-slider") must match the JavaScript registration name exactly
- JavaScript assets are loaded from `src/main/resources/assets/`
- The component receives props: `value`, `onChange`, `config`, `disabled`, `error`
- Configuration options are passed via the `config` property

See [Custom Input Types Documentation](../../morpheus-plugin-docs/src/docs/CustomInputTypes.md) for full details.

## Notes

- This is a demonstration plugin with simplified implementations
- In production, you would implement actual system integration logic
- The prechecks step would run real validation checks
- The submission would trigger actual infrastructure provisioning
- Error handling and retry logic should be added for production use
