# Custom OptionType Input Types

This guide explains how to create custom input types for OptionType fields in Morpheus plugins.

## Overview

Morpheus allows plugins to define custom UI components for form inputs. This is accomplished by:

1. Creating a JavaScript component that implements your custom input
2. Registering the component with Morpheus
3. Using a custom `InputType` in your plugin's OptionType definitions

## Creating a Custom Input Type

### Step 1: Define the InputType in Your Plugin

In your Java/Groovy code, create a custom `InputType` instance:

```groovy
// Define a custom input type
OptionType.InputType CUSTOM_SLIDER = new OptionType.InputType("custom-slider")

// Use it in an OptionType
new OptionType(
    code: 'volumeLevel',
    name: 'Volume Level',
    fieldName: 'volumeLevel',
    fieldLabel: 'Volume Level',
    fieldContext: 'config',
    inputType: CUSTOM_SLIDER,  // Use your custom input type
    required: true,
    displayOrder: 0
)
```

### Step 2: Create the JavaScript Component

Create a JavaScript file in your plugin's `src/main/resources/assets/` directory:

**File: `src/main/resources/assets/custom-slider.js`**

```javascript
// Define your React component
const CustomSlider = ({ value, onChange, config, ...props }) => {
	const [sliderValue, setSliderValue] = React.useState(
		value || config.defaultValue || 50,
	);

	const handleChange = (e) => {
		const newValue = parseInt(e.target.value);
		setSliderValue(newValue);
		if (onChange) {
			onChange(newValue);
		}
	};

	return React.createElement(
		"div",
		{ className: "custom-slider-container" },
		React.createElement("input", {
			type: "range",
			min: config.min || 0,
			max: config.max || 100,
			value: sliderValue,
			onChange: handleChange,
			className: "custom-slider",
		}),
		React.createElement("span", { className: "slider-value" }, sliderValue),
	);
};

// Register the component with Morpheus
// The first parameter MUST match the InputType string value exactly
window.Morpheus.components.registry.register("custom-slider", CustomSlider, {
	type: "optionType",
	name: "Custom Slider",
	group: "custom",
	details: [
		{
			fieldLabel: "Minimum Value",
			fieldName: "config.min",
			type: "number",
			defaultValue: 0,
			displayOrder: 100,
		},
		{
			fieldLabel: "Maximum Value",
			fieldName: "config.max",
			type: "number",
			defaultValue: 100,
			displayOrder: 101,
		},
		{
			fieldLabel: "Default Value",
			fieldName: "config.defaultValue",
			type: "number",
			defaultValue: 50,
			helpBlock: "The initial value for this slider",
			displayOrder: 70,
		},
	],
	help: `### Custom Slider Input
This input type provides a slider control for numeric values.
Configure the min, max, and default values in the field settings.`,
});
```

### Step 3: Register the Asset in Your Plugin

In your main plugin class, ensure the JavaScript asset is registered:

```groovy
class MyPlugin extends Plugin {

    @Override
    void initialize() {
        this.setName("My Custom Plugin")

        // Register your providers
        this.registerProvider(new MyCustomProvider(this, morpheus))

        // The asset will be automatically loaded from src/main/resources/assets/
        // if your build process includes it in the plugin JAR
    }
}
```

## Registration API

The `window.Morpheus.components.registry.register()` function accepts three parameters:

### Parameter 1: Component Name (String)

**CRITICAL**: This string must match your `InputType` value exactly.

```javascript
// If your InputType is: new OptionType.InputType("my-widget")
// Then register as:
window.Morpheus.components.registry.register("my-widget", ...)
```

### Parameter 2: Component (React Component)

Your React component. It will receive these props:

- `value`: The current field value
- `onChange`: Callback function to update the value
- `config`: Configuration object from the OptionType
- `disabled`: Boolean indicating if the field is disabled
- `readOnly`: Boolean indicating if the field is read-only
- `error`: Error message string if validation failed
- `helpText`: Help text from the OptionType definition

### Parameter 3: Registration Options (Object)

```javascript
{
  type: "optionType",        // Always "optionType" for custom inputs
  name: "Display Name",       // Human-readable name
  group: "custom",            // Grouping for organization
  details: [                  // Configuration fields for the option type
    {
      fieldLabel: "Config Field Label",
      fieldName: "config.propertyName",
      type: "text",           // Standard input type
      defaultValue: "default",
      helpBlock: "Help text",
      displayOrder: 100
    }
  ],
  help: "Markdown documentation"  // Help text shown to users
}
```

## Example: Color Picker Component

Here's a complete example of a custom color picker:

**Java/Groovy:**

```groovy
OptionType.InputType COLOR_PICKER = new OptionType.InputType("color-picker")

new OptionType(
    code: 'brandColor',
    name: 'Brand Color',
    fieldName: 'brandColor',
    fieldLabel: 'Brand Color',
    fieldContext: 'config',
    inputType: COLOR_PICKER,
    defaultValue: '#0066cc',
    required: false,
    displayOrder: 0
)
```

**JavaScript (color-picker.js):**

```javascript
const ColorPicker = ({ value, onChange, config, disabled, error }) => {
	const [color, setColor] = React.useState(
		value || config.defaultColor || "#000000",
	);

	const handleChange = (e) => {
		const newColor = e.target.value;
		setColor(newColor);
		if (onChange) {
			onChange(newColor);
		}
	};

	return React.createElement(
		"div",
		{ className: "color-picker-container" },
		React.createElement("input", {
			type: "color",
			value: color,
			onChange: handleChange,
			disabled: disabled,
			className: error ? "error" : "",
		}),
		React.createElement("input", {
			type: "text",
			value: color,
			onChange: handleChange,
			disabled: disabled,
			placeholder: "#000000",
			className: "color-hex-input",
		}),
	);
};

window.Morpheus.components.registry.register("color-picker", ColorPicker, {
	type: "optionType",
	name: "Color Picker",
	group: "advanced",
	details: [
		{
			fieldLabel: "Default Color",
			fieldName: "config.defaultColor",
			type: "text",
			defaultValue: "#000000",
			helpBlock: "Default color in hex format",
			displayOrder: 70,
		},
	],
	help: `### Color Picker
Provides a color picker input with both visual selector and hex input.
Returns color value in hex format (#RRGGBB).`,
});
```

## Asset Loading

### Build Configuration

Ensure your `build.gradle` includes the assets directory:

```gradle
shadowJar {
    // Include JavaScript assets
    from('src/main/resources/assets') {
        into 'assets'
    }
}
```

### Asset Structure

```
your-plugin/
└── src/
    └── main/
        └── resources/
            └── assets/
                ├── custom-slider.js
                ├── color-picker.js
                └── my-widget.js
```

## Best Practices

1. **Match Names Exactly**: The registration name must match the InputType string exactly (case-sensitive)

2. **Namespace Your Types**: Use prefixes to avoid conflicts:

   ```groovy
   new OptionType.InputType("myplugin-custom-input")
   ```

3. **Handle All Props**: Your component should handle `disabled`, `readOnly`, and `error` props

4. **Validate Input**: Implement proper validation in your component

5. **Provide Help Text**: Always include helpful documentation in the registration options

6. **Use Config Object**: Store component-specific configuration in `config` rather than top-level OptionType fields

7. **Return Serializable Values**: The `onChange` callback should receive values that can be JSON serialized

## Debugging

If your custom input type doesn't appear:

1. **Check the browser console** for JavaScript errors
2. **Verify the registration name** matches your InputType value exactly
3. **Ensure the asset is included** in your plugin JAR
4. **Check that `window.Morpheus.components.registry` exists** before registering
5. **Use browser dev tools** to verify your JavaScript file loaded

## TypeScript Support

If using TypeScript, you can type your component:

```typescript
interface CustomInputProps {
	value: any;
	onChange: (value: any) => void;
	config: Record<string, any>;
	disabled?: boolean;
	readOnly?: boolean;
	error?: string;
	helpText?: string;
}

const CustomInput: React.FC<CustomInputProps> = ({
	value,
	onChange,
	config,
	...props
}) => {
	// Component implementation
};

declare global {
	interface Window {
		Morpheus: {
			components: {
				registry: {
					register: (
						name: string,
						component: React.ComponentType,
						options: any,
					) => void;
				};
			};
		};
	}
}

window.Morpheus.components.registry.register("custom-input", CustomInput, {
	// registration options
});
```

## Standard Input Types Reference

For comparison, here are the standard input types:

- `text` - Single line text input
- `password` - Masked text input
- `number` - Numeric input
- `textarea` - Multi-line text input
- `select` - Dropdown select
- `multiSelect` - Multiple selection dropdown
- `checkbox` - Checkbox input
- `radio` - Radio button group
- `credential` - Credential selector
- `typeahead` - Autocomplete text input
- `multiTypeahead` - Multiple value autocomplete
- `code-editor` - Code editor with syntax highlighting
- `hidden` - Hidden field
- `byteSize` - Byte size input (MB/GB conversion)

Custom input types work alongside these standard types.
