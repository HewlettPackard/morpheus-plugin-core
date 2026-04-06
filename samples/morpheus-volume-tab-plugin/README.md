# Sample Volume Tab Plugin

This sample demonstrates how to implement a `VolumeTabProvider` using the Morpheus plugin API.

## What it includes

- `SampleVolumeTabPlugin` Java plugin entry point
- `SampleVolumeTabProvider` Java tab provider implementation
- `renderer/hbs/volumeTab.hbs` example Handlebars template

## Build

First uncomment the line in `settings.gradle` to include the sample plugin project

```gradle
From the repository root:

```bash
./gradlew :samples:morpheus-volume-tab-plugin:shadowJar
```

The built plugin jar will be created under:

```text
samples/morpheus-volume-tab-plugin/build/libs/
```

## Key files

- `src/main/java/com/morpheusdata/sample/volumetab/SampleVolumeTabPlugin.java`
- `src/main/java/com/morpheusdata/sample/volumetab/SampleVolumeTabProvider.java`
- `src/main/resources/renderer/hbs/volumeTab.hbs`

## Notes

This sample is intended for in-repo development and depends on `project(':morpheus-plugin-api')` rather than a published API artifact.


