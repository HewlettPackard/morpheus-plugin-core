/**
 * Custom Slider Input Component for Morpheus
 *
 * This demonstrates how to create a custom OptionType input type
 * that integrates with the Morpheus UI component registry.
 */

(function () {
	"use strict";

	// Wait for Morpheus to be ready
	if (
		typeof window.Morpheus === "undefined" ||
		!window.Morpheus.components ||
		!window.Morpheus.components.registry
	) {
		console.error("Morpheus component registry not available");
		return;
	}

	// Define the CustomSlider React component
	const CustomSlider = function (props) {
		const { value, onChange, config, disabled, error, helpText } = props;

		const [sliderValue, setSliderValue] = React.useState(
			value !== undefined && value !== null ? value : config.defaultValue || 50,
		);

		const min = config.min || 0;
		const max = config.max || 100;
		const step = config.step || 1;
		const showValue = config.showValue !== false;
		const unit = config.unit || "";

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
				className: "custom-slider-container" + (error ? " has-error" : ""),
				style: { marginBottom: "10px" },
			},
			[
				React.createElement(
					"div",
					{
						key: "slider-wrapper",
						style: { display: "flex", alignItems: "center", gap: "10px" },
					},
					[
						React.createElement("input", {
							key: "slider",
							type: "range",
							min: min,
							max: max,
							step: step,
							value: sliderValue,
							onChange: handleChange,
							disabled: disabled,
							className: "custom-slider",
							style: { flex: 1 },
						}),
						showValue &&
							React.createElement(
								"span",
								{
									key: "value-display",
									className: "slider-value-display",
									style: {
										minWidth: "60px",
										textAlign: "right",
										fontWeight: "bold",
									},
								},
								sliderValue + (unit ? " " + unit : ""),
							),
					],
				),
				helpText &&
					React.createElement(
						"small",
						{
							key: "help",
							className: "help-block",
							style: { display: "block", marginTop: "5px" },
						},
						helpText,
					),
				error &&
					React.createElement(
						"small",
						{
							key: "error",
							className: "error-message",
							style: { display: "block", color: "#d9534f", marginTop: "5px" },
						},
						error,
					),
			],
		);
	};

	// Register the component with Morpheus
	// The first parameter MUST match the InputType string exactly
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
				helpBlock: "Minimum slider value",
				displayOrder: 100,
			},
			{
				fieldLabel: "Maximum Value",
				fieldName: "config.max",
				type: "number",
				defaultValue: 100,
				helpBlock: "Maximum slider value",
				displayOrder: 101,
			},
			{
				fieldLabel: "Step",
				fieldName: "config.step",
				type: "number",
				defaultValue: 1,
				helpBlock: "Increment value for each step",
				displayOrder: 102,
			},
			{
				fieldLabel: "Default Value",
				fieldName: "config.defaultValue",
				type: "number",
				defaultValue: 50,
				helpBlock: "The initial value for this slider",
				displayOrder: 70,
			},
			{
				fieldLabel: "Show Value",
				fieldName: "config.showValue",
				type: "checkbox",
				defaultValue: true,
				helpBlock: "Display the current value next to the slider",
				displayOrder: 103,
			},
			{
				fieldLabel: "Unit Label",
				fieldName: "config.unit",
				type: "text",
				helpBlock:
					"Optional unit to display after the value (e.g., %, GB, etc.)",
				displayOrder: 104,
			},
		],
		help: `### Custom Slider Input
  
This input type provides an interactive slider control for numeric values.

**Features:**
- Configurable min, max, and step values
- Optional value display
- Custom unit labels
- Smooth dragging interaction

**Use Cases:**
- Volume controls
- Percentage inputs
- Resource allocation (CPU, memory, storage)
- Quality settings

**Configuration:**
Configure the slider behavior using the field settings:
- Set min/max range for valid values
- Define step increment
- Choose whether to show the current value
- Add unit labels (%, GB, cores, etc.)

**Output:**
Returns an integer value within the configured range.`,
	});

	console.log("Custom Slider component registered successfully");
})();
