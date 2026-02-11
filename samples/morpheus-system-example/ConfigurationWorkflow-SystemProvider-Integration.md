# Configuration Workflow and System Provider Integration

## Overview

Configuration Workflow Providers work in conjunction with System Providers to enable complex, multi-step configuration processes for system setup and management in Morpheus. This document explains how these two provider types integrate to create powerful, guided configuration experiences.

## Architecture

### Key Components

1. **System Provider** - Manages system lifecycle operations (create, update, delete)
2. **Configuration Workflow Provider** - Defines multi-step configuration workflows
3. **Wizard Provider** - Handles individual workflow steps with form-based data collection
4. **InputTypeLibraryProvider** - Registers JavaScript libraries for custom input components
5. **System Model** - Stores system data including `configurationWorkflowState`
6. **SystemTypeLayout** - Links systems to their configuration workflows via direct `configurationWorkflow` object reference

### Relationship Diagram

```
SystemProvider
    └── SystemTypeLayout
            └── configurationWorkflow (object) → direct reference to ConfigurationWorkflow
                    └── ConfigurationWorkflow
                            └── ConfigurationWorkflowSteps
                                    └── wizard (object) → direct reference to Wizard
                                            └── Wizard
                                                    └── WizardSteps
                                                            └── OptionTypes
```

## How They Work Together

### 1. System Provider Registration

The System Provider registers itself and defines the system types and layouts it supports:

```groovy
class ArcusSystemProvider implements SystemProvider {

    @Override
    String getCode() {
        return 'arcus-system-provider'
    }

    @Override
    String getName() {
        return 'Arcus System Provider'
    }

    @Override
    Collection<SystemType> getSystemTypes() {
        SystemType systemType = new SystemType(
            code: 'arcus-system',
            name: 'Arcus Infrastructure System',
            description: 'Complete Arcus infrastructure management system'
        )
        return [systemType]
    }

    @Override
    Collection<SystemTypeLayout> getSystemTypeLayouts() {
        SystemType systemType = new SystemType(
            code: 'arcus-system',
            name: 'Arcus Infrastructure System'
        )

        // Get the ConfigurationWorkflow reference (e.g., from MorpheusContext)
        ConfigurationWorkflow workflow = morpheus.getConfigurationWorkflow()
            .find(new DataQuery().withFilter('code', 'arcus-system-configuration-workflow'))
            .blockingGet()

        SystemTypeLayout layout = new SystemTypeLayout(
            code: 'arcus-standard-layout',
            name: 'Arcus Standard Layout',
            description: 'Standard layout with configuration workflow',
            systemType: systemType,
            configurationWorkflow: workflow  // Direct object reference
        )

        // Add component types to the layout
        layout.components = [
            createComponentType('arcus-switch', 'Arcus Switch', 'Network switch', NetworkSwitch.class),
            createComponentType('arcus-host', 'Arcus Host', 'Host/server', ComputeServer.class),
            createComponentType('arcus-storage', 'Arcus Storage', 'Storage array', StorageServer.class),
            createComponentType('arcus-network', 'Arcus Data Network', 'Data network', Network.class),
            createComponentType('arcus-cluster', 'Arcus Cluster', 'Cluster', ComputeServerGroup.class)
        ]

        return [layout]
    }

    private SystemComponentType createComponentType(String code, String name, String description, Class modelType) {
        return new SystemComponentType(
            code: code,
            name: name,
            description: description,
            modelType: modelType  // REQUIRED: Links to Morpheus model class
        )
    }
}
```

### 2. Configuration Workflow Linking

The SystemTypeLayout uses a direct object reference to link to the ConfigurationWorkflow:

```java
// In SystemTypeLayout
@JsonSerialize(using = ModelAsIdOnlySerializer.class)
protected ConfigurationWorkflow configurationWorkflow; // Direct object reference

public ConfigurationWorkflow getConfigurationWorkflow() {
    return configurationWorkflow;
}

public void setConfigurationWorkflow(ConfigurationWorkflow configurationWorkflow) {
    this.configurationWorkflow = configurationWorkflow;
    markDirty("configurationWorkflow", configurationWorkflow);
}
```

### 3. Workflow Provider Discovery

When a system is created or configured, Morpheus:

1. Looks up the SystemTypeLayout
2. Retrieves the `configurationWorkflow` object reference
3. Uses the workflow's code to find the registered ConfigurationWorkflowProvider (matched by `getCode()` method)
4. Initiates the multi-step workflow

The direct object reference provides type safety while still enabling loose coupling between models and providers at the provider layer.

### 4. Dynamic Wizard Loading from Provider

ConfigurationWorkflowStep supports dynamic wizard loading through the WizardProvider:

```java
// In ConfigurationWorkflowStep - smart getter that uses provider if available
public Wizard getWizard() {
    // If we have a provider, get the wizard from it dynamically
    if (wizardProvider != null) {
        return wizardProvider.getWizard();
    }
    // Otherwise return the stored wizard reference
    return wizard;
}
```

The WizardProvider's `getWizard()` method builds a Wizard object dynamically:

```java
// In WizardProvider interface - default implementation
default Wizard getWizard() {
    Wizard wizard = new Wizard();
    wizard.setCode(getCode());
    wizard.setName(getWizardName());
    wizard.setDescription(getWizardDescription());
    wizard.setSteps(getWizardSteps());
    wizard.setActive(true);
    return wizard;
}
```

**Benefits of this approach:**

- **Dynamic Data**: Wizard configuration is always current from the provider
- **Flexibility**: Provider can customize wizard based on context
- **Fallback**: If provider isn't available (e.g., database load), uses stored reference
- **Clean Separation**: The provider controls wizard structure, not the stored data

**Usage in workflow:**

```groovy
ConfigurationWorkflowStep step = new ConfigurationWorkflowStep(
    code: 'system',
    name: 'System Configuration'
)

// Set the wizard provider (transient, runtime only)
step.setWizardProvider(myWizardProvider)

// Get wizard dynamically from provider
Wizard wizard = step.getWizard()  // Calls wizardProvider.getWizard()
```

### 5. Configuration Workflow Execution

The ConfigurationWorkflowProvider orchestrates the entire configuration process:

```groovy
class ArcusSystemConfigurationWorkflowProvider implements ConfigurationWorkflowProvider {

    @Override
    String getCode() {
        return 'arcus-system-configuration-workflow'
    }

    @Override
    String getWorkflowName() {
        return 'Arcus System Configuration'
    }

    @Override
    String getWorkflowDescription() {
        return 'Complete configuration workflow for configuring an Arcus infrastructure system'
    }

    @Override
    List<ConfigurationWorkflowStep> getWorkflowSteps() {
        // Each step references its wizard using a direct object reference
        // Get wizard references (e.g., from MorpheusContext or registry)
        Wizard systemWizard = getWizardByCode('arcus-system-config-wizard')
        Wizard switchWizard = getWizardByCode('arcus-switch-config-wizard')

        return [
            new ConfigurationWorkflowStep(
                code: 'system',
                name: 'System',
                description: 'Configure basic system settings',
                wizard: systemWizard,  // Direct object reference
                displayOrder: 0,
                isRequired: true
            ),
            new ConfigurationWorkflowStep(
                code: 'switches',
                name: 'Switches',
                description: 'Configure network switches',
                wizard: switchWizard,  // Direct object reference
                displayOrder: 1,
                isRequired: true
            )
            // ... more steps
        ]
    }
}
```

### 5. State Management

The System model stores the workflow state as JSON:

```java
// In System model
protected String configurationWorkflowState;

public String getConfigurationWorkflowState() {
    return configurationWorkflowState;
}

public void setConfigurationWorkflowState(String configurationWorkflowState) {
    markDirty("configurationWorkflowState", configurationWorkflowState);
    this.configurationWorkflowState = configurationWorkflowState;
}
```

The ConfigurationWorkflowProvider manages this state:

```groovy
@Override
ServiceResponse updateParentState(Object parentObject, Map configurationWorkflowState, Map opts) {
    if (parentObject instanceof System) {
        System system = (System) parentObject
        def jsonState = groovy.json.JsonOutput.toJson(configurationWorkflowState)
        system.setConfigurationWorkflowState(jsonState)
        return ServiceResponse.success()
    }
    return ServiceResponse.error("Invalid parent object type")
}
```

## Workflow Lifecycle

### Phase 1: Initialization

1. User creates a new System via System Provider
2. System Provider creates System instance with reference to SystemTypeLayout
3. SystemTypeLayout references ConfigurationWorkflow
4. UI detects ConfigurationWorkflow and loads the ConfigurationWorkflowProvider

### Phase 2: Step-by-Step Configuration

For each ConfigurationWorkflowStep:

1. **Load Wizard**: The wizard can be retrieved in two ways:
   - **From WizardProvider** (preferred): If a `WizardProvider` is set on the step (via the transient `wizardProvider` field), calling `step.getWizard()` dynamically retrieves the wizard from `wizardProvider.getWizard()`. This ensures the wizard data is always current from the provider.
   - **From stored reference**: If no provider is set, the stored `wizard` object reference is used (useful when loading from database).

   The WizardProvider is looked up using the wizard's code (matched by WizardProvider's `getCode()` method).

2. **Render Forms**: WizardSteps define OptionTypes to display
3. **Collect Data**: User fills out forms in each wizard step
4. **Validate**: WizardProvider's `validateWizardStep()` method validates input
5. **Save State**: ConfigurationWorkflowProvider's `saveStepConfiguration()` stores data
6. **Update Parent**: `updateParentState()` persists state to System model

```groovy
@Override
ServiceResponse saveStepConfiguration(String stepCode, Map stepData, Map currentState, Map opts) {
    // Merge step data into current state
    if (!currentState) {
        currentState = [:]
    }
    currentState[stepCode] = stepData
    currentState['lastCompletedStep'] = stepCode
    currentState['lastUpdated'] = new Date()

    return ServiceResponse.success(currentState)
}
```

#### Wizard Step Validation

Individual wizard steps are validated by the WizardProvider using the `validateWizardStep()` method:

```groovy
@Override
ServiceResponse validateWizardStep(String stepCode, Map stepData, Map wizardData, Map opts) {
    def errors = []

    // Validate specific step data
    if (stepCode == 'system') {
        if (!stepData.systemName) {
            errors << "System name is required"
        }
        if (!stepData.ipAddress) {
            errors << "IP address is required"
        } else if (!isValidIP(stepData.ipAddress)) {
            errors << "Invalid IP address format"
        }
    }

    // Validate against previous wizard data
    if (stepCode == 'network' && wizardData['system']) {
        def systemType = wizardData['system'].systemType
        if (systemType == 'distributed' && !stepData.networkCIDR) {
            errors << "Network CIDR is required for distributed systems"
        }
    }

    if (errors) {
        return ServiceResponse.error(errors.join(', '))
    }

    return ServiceResponse.success()
}
```

### Phase 3: Final Validation

Before final submission, the workflow provider performs cross-step validation:

```groovy
@Override
ServiceResponse validateConfigurationWorkflow(Map configurationWorkflowState, Object parentObject, Map opts) {
    def errors = []

    // Validate required steps are completed
    def requiredSteps = ['system', 'switches', 'hosts', 'storage', 'data-network', 'cluster']
    requiredSteps.each { step ->
        if (!configurationWorkflowState[step]) {
            errors << "Step '${step}' must be completed before submission"
        }
    }

    // Validate system configuration
    if (configurationWorkflowState['system']) {
        def systemConfig = configurationWorkflowState['system']
        if (!systemConfig['systemName']) {
            errors << 'System name is required'
        }
    }

    if (errors) {
        return ServiceResponse.error(errors.join(', '))
    }

    return ServiceResponse.success()
}
```

### Phase 4: Submission and Execution

Final submission triggers the actual system configuration:

```groovy
@Override
ServiceResponse submitOrchestration(Map configurationWorkflowState, Object parentObject, Map opts) {
    if (!(parentObject instanceof System)) {
        return ServiceResponse.error('Invalid parent object type')
    }

    System system = (System) parentObject

    // Update configurationWorkflowState with submission info
    configurationWorkflowState['status'] = 'submitted'
    configurationWorkflowState['submittedDate'] = new Date()
    configurationWorkflowState['completed'] = true

    // Update the system's configurationWorkflowState
    def jsonState = groovy.json.JsonOutput.toJson(configurationWorkflowState)
    system.setConfigurationWorkflowState(jsonState)

    // In a real implementation, this would trigger the actual setup process
    // For example: executeSystemSetup(system, configurationWorkflowState)

    return ServiceResponse.success([
        message: 'Arcus system configuration submitted successfully',
        systemId: system.getId()
    ])
}
```

## System Provider Integration Points

### 1. System Initialization

The System Provider handles system lifecycle with these key methods:

```groovy
@Override
ServiceResponse prepareInitializeSystem(System system, SystemRequest systemRequest) {
    // Prepare for system initialization (validation, resource allocation)
    return ServiceResponse.success()
}

@Override
ServiceResponse initializeSystem(System system, SystemRequest systemRequest) {
    // Initialize the system instance
    // Set initial workflow state if needed
    return ServiceResponse.success(system)
}

@Override
ServiceResponse refreshSystem(System system) {
    // Refresh system data from external sources
    return ServiceResponse.success()
}
```

### 2. Configuration Workflow State Management

The System model stores workflow state as JSON:

```groovy
@Override
ServiceResponse updateParentState(Object parentObject, Map configurationWorkflowState, Map opts) {
    if (!(parentObject instanceof System)) {
        return ServiceResponse.error('Invalid parent object type')
    }

    System system = (System) parentObject

    // Convert configurationWorkflowState to JSON string
    def jsonState = groovy.json.JsonOutput.toJson(configurationWorkflowState)
    system.setConfigurationWorkflowState(jsonState)

    return ServiceResponse.success()
}

@Override
Map getConfigurationWorkflowState(Object parentObject, Map opts) {
    if (!(parentObject instanceof System)) {
        return [:]
    }

    System system = (System) parentObject
    def stateJson = system.getConfigurationWorkflowState()

    if (stateJson) {
        def slurper = new groovy.json.JsonSlurper()
        return slurper.parseText(stateJson)
    }

    return [:]
}
```

## Custom Input Types and DataSet Providers

### Registering JavaScript Libraries

Configuration workflows and wizards can use custom input types by registering JavaScript libraries through the `InputTypeLibraryProvider`.

#### Creating an InputTypeLibraryProvider

```groovy
class ArcusInputTypeLibraryProvider implements InputTypeLibraryProvider {

    @Override
    String getCode() {
        return 'arcus-input-types'
    }

    @Override
    String getName() {
        return 'Arcus Custom Input Types'
    }

    @Override
    String getLibraryScriptPath(Map<String, Object> opts) {
        return '/custom-slider.js'
    }
}
```

### Creating DataSet Providers for Option Lists

**IMPORTANT**: OptionTypes do NOT support inline `optionList` arrays. You must create a `DatasetProvider` and reference it using `optionSource` and `optionSourceType`.

#### Creating a DatasetProvider

```groovy
class StorageTypeDatasetProvider extends AbstractDatasetProvider<Map, String> {

    StorageTypeDatasetProvider(Plugin plugin, MorpheusContext morpheusContext) {
        this.plugin = plugin
        this.morpheusContext = morpheusContext
        this.datasetInfo = new DatasetInfo(
            'arcus',                           // namespace (used for optionSourceType)
            'storageTypes',                    // key (used for optionSource)
            'Storage Types',                   // name
            'Available storage types for Arcus systems'  // description
        )
    }

    @Override
    Class<Map> getItemType() {
        return Map.class
    }

    @Override
    Observable<Map> list(DatasetQuery query) {
        def items = [
            [name: 'SAN', value: 'san'],
            [name: 'NAS', value: 'nas'],
            [name: 'Local', value: 'local']
        ]
        return Observable.fromIterable(items)
    }

    @Override
    Observable<Map> listOptions(DatasetQuery query) {
        return list(query)
    }

    @Override
    Map fetchItem(Object value) {
        def items = [
            [name: 'SAN', value: 'san'],
            [name: 'NAS', value: 'nas'],
            [name: 'Local', value: 'local']
        ]
        return items.find { it.value == value?.toString() }
    }

    @Override
    String itemName(Map item) {
        return item?.name
    }

    @Override
    String itemValue(Map item) {
        return item?.value
    }
}
```

#### Registering the DatasetProvider

Register the DatasetProvider in your plugin's `initialize()` method:

```groovy
@Override
void initialize() {
    this.setName("System Example Plugin")

    // Register input type library FIRST
    this.registerProvider(new ArcusInputTypeLibraryProvider(this, morpheus))

    // Register dataset providers
    this.registerProvider(new StorageTypeDatasetProvider(this, morpheus))

    // Then register other providers
    this.registerProvider(new ArcusSystemProvider(this, morpheus))
    this.registerProvider(new ArcusSystemConfigurationWorkflowProvider(this, morpheus))
    // ... wizard providers
}
```

#### Using DatasetProviders in OptionTypes

Reference the dataset using `optionSource` (key) and `optionSourceType` (namespace):

```groovy
new OptionType(
    code: 'storageType',
    name: 'Storage Type',
    fieldName: 'storageType',
    fieldLabel: 'Storage Type',
    fieldContext: 'config',
    inputType: OptionType.InputType.SELECT,
    optionSource: 'storageTypes',        // References DatasetProvider key
    optionSourceType: 'arcus',           // References DatasetProvider namespace
    required: true,
    displayOrder: 0
)
```

**DO NOT use inline `optionList` arrays** - they are not supported in the OptionType model. Always create a DatasetProvider.

#### JavaScript File Location

Place your JavaScript file in the plugin's assets directory:

```
src/
  main/
    resources/
      assets/
        custom-slider.js  // Your custom input type library
```

The file will be served at: `/assets/plugin/{plugin-name}/custom-slider.js`

#### Example Custom Input Type

```javascript
(function () {
	"use strict";

	// Define the CustomSlider React component
	const CustomSlider = function (props) {
		const { value, onChange, config, disabled, error } = props;

		const [sliderValue, setSliderValue] = React.useState(
			value !== undefined ? value : config.defaultValue || 50,
		);

		const handleChange = function (e) {
			const newValue = parseInt(e.target.value);
			setSliderValue(newValue);
			if (onChange) {
				onChange(newValue);
			}
		};

		return React.createElement(
			"div",
			{
				className: "custom-slider-container",
			},
			[
				React.createElement("input", {
					type: "range",
					min: config.min || 0,
					max: config.max || 100,
					step: config.step || 1,
					value: sliderValue,
					onChange: handleChange,
					disabled: disabled,
				}),
				React.createElement("span", {}, sliderValue),
			],
		);
	};

	// Register the component
	if (window.Morpheus?.components?.registry) {
		window.Morpheus.components.registry.register("custom-slider", CustomSlider);
	}
})();
```

#### Using Custom Input Types in OptionTypes

Once registered, use the custom input type in your OptionTypes:

```groovy
new OptionType(
    code: 'cpuAllocation',
    name: 'CPU Allocation',
    fieldName: 'cpuAllocation',
    fieldContext: 'config',
    fieldComponent: 'custom-slider',  // Reference your custom component
    defaultValue: 4,
    config: JsonOutput.toJson([
        min: 1,
        max: 16,
        step: 1,
        showValue: true,
        unit: 'cores'
    ])
)
```

**Note**: For dropdown/select lists, use DatasetProvider with `optionSource`/`optionSourceType` instead of inline lists.

## Advanced Features

### Conditional Steps

Show/hide steps based on previous configuration:

```groovy
@Override
boolean shouldShowStep(ConfigurationWorkflowStep step, Map configurationWorkflowState, Map opts) {
    // Only show storage config if system type requires it
    if (step.code == 'storage') {
        def systemConfig = configurationWorkflowState['system']
        return systemConfig?.includeStorage == true
    }
    return true
}
```

### State Recovery

Resume interrupted workflows:

```groovy
@Override
Map getConfigurationWorkflowState(Object parentObject, Map opts) {
    System system = (System) parentObject
    def stateJson = system.getConfigurationWorkflowState()

    if (stateJson) {
        return new groovy.json.JsonSlurper().parseText(stateJson)
    }

    // Return default initial state
    return [
        status: 'new',
        completedSteps: []
    ]
}
```

### Post-Submission Actions

Trigger follow-up actions after workflow completion:

```groovy
@Override
void afterSubmit(Map configurationWorkflowState, ServiceResponse submitResponse, Object parentObject, Map opts) {
    System system = (System) parentObject

    // Send notification
    notificationService.notify([
        type: 'system.configuration.complete',
        systemId: system.id,
        systemName: system.name
    ])

    // Schedule initial health check
    scheduleHealthCheck(system)

    // Create monitoring alerts
    setupMonitoring(system, configurationWorkflowState)
}
```

## Best Practices

### 1. State Structure

Organize workflow state by step code for easy access:

```json
{
	"status": "in-progress",
	"createdDate": "2026-02-03T10:30:00Z",
	"completedSteps": ["system", "network"],
	"system": {
		"systemName": "arcus-prod-01",
		"group": "production",
		"country": "US"
	},
	"network": {
		"networkCIDR": "10.0.0.0/24",
		"gateway": "10.0.0.1"
	}
}
```

### 2. Error Handling

Provide clear, actionable error messages:

```groovy
ServiceResponse validateConfigurationWorkflow(Map configurationWorkflowState, Object parentObject, Map opts) {
    def errors = []

    if (!configurationWorkflowState['system']?.systemName) {
        errors << [
            field: 'system.systemName',
            message: 'System name is required',
            step: 'system'
        ]
    }

    if (errors) {
        return ServiceResponse.error('Validation failed', errors)
    }

    return ServiceResponse.success()
}
```

### 3. Idempotency

Ensure workflow operations can be safely retried:

```groovy
ServiceResponse submitConfigurationWorkflow(Map configurationWorkflowState, Object parentObject, Map opts) {
    // Check if already submitted
    if (configurationWorkflowState['status'] == 'completed') {
        return ServiceResponse.success([
            message: 'System already configured',
            alreadyCompleted: true
        ])
    }

    // Proceed with submission
    // ...
}
```

### 4. Backward Compatibility

Handle state from older workflow versions:

```groovy
Map migrateWorkflowState(Map oldState) {
    def version = oldState['version'] ?: '1.0'

    if (version == '1.0' && currentVersion == '2.0') {
        // Migrate v1 structure to v2
        oldState['network'] = oldState['networkSettings']
        oldState.remove('networkSettings')
        oldState['version'] = '2.0'
    }

    return oldState
}
```

## Testing

### Unit Testing Configuration Workflow Provider

```groovy
class ArcusSystemConfigurationWorkflowProviderSpec extends Specification {

    def "should return correct workflow steps"() {
        given:
        def provider = new ArcusSystemConfigurationWorkflowProvider()

        when:
        def steps = provider.getWorkflowSteps()

        then:
        steps.size() == 7
        steps[0].code == 'system'
        steps[0].wizard.code == 'arcus-system-wizard'  // Direct object reference to Wizard
    }

    def "should validate complete workflow state"() {
        given:
        def provider = new ArcusSystemConfigurationWorkflowProvider()
        def state = [
            system: [systemName: 'test-system'],
            network: [networkCIDR: '10.0.0.0/24']
        ]
        def system = new System()

        when:
        def result = provider.validateConfigurationWorkflow(state, system, [:])

        then:
        result.success
    }
}
```

## Complete Example

See the [morpheus-system-example](./README.md) plugin for a complete working implementation demonstrating:

- System Provider with ConfigurationWorkflow integration
- 7-step configuration workflow
- State management and validation
- InputTypeLibraryProvider for custom JavaScript components
- Wizard providers with custom input types (custom slider)
- Cross-step validation
- Final submission and execution

## Summary

Configuration Workflow Providers extend System Providers by:

1. **Defining structured, multi-step configuration processes** via `getWorkflowSteps()`
2. **Managing state across workflow steps** via `saveStepConfiguration()` and `updateParentState()`
3. **Enabling complex validation logic** via `validateConfigurationWorkflow()`
4. **Coordinating with System Provider lifecycle methods** via `submitOrchestration()`
5. **Providing guided user experience for system setup** through wizard-based forms
6. **Using direct object references with type safety** - `configurationWorkflow` directly references ConfigurationWorkflow object, `wizard` directly references Wizard object. ConfigurationWorkflowStep can dynamically load wizards from WizardProvider using `wizardProvider.getWizard()` for fresh data, or use stored references when providers are unavailable. The providers remain loosely coupled and are discovered via the model's code field.
7. **Requiring DatasetProviders for option lists** - OptionTypes do NOT support inline `optionList`, must use `optionSource`/`optionSourceType`

This architecture enables plugin developers to create sophisticated, user-friendly configuration experiences while maintaining clean separation of concerns between system management and configuration workflows. The direct object reference pattern provides type safety, while the dynamic wizard loading from providers ensures current data and flexibility.
