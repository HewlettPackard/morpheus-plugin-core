package com.morpheusdata.core.providers

import com.fasterxml.jackson.databind.ObjectMapper
import com.morpheusdata.core.MorpheusContext
import com.morpheusdata.core.Plugin
import com.morpheusdata.model.OptionType
import com.morpheusdata.model.ProvisionType
import com.morpheusdata.model.StorageVolumeType
import spock.lang.Specification

class ProvisionProviderStorageVolumeOptionsSpec extends Specification {

	void "modern and legacy providers inherit the actual default with independent collections"() {
		given:
		def method = provider.class.getMethod('getStorageVolumeOptionTypes')

		when:
		def first = provider.getStorageVolumeOptionTypes()
		def second = provider.getStorageVolumeOptionTypes()
		def other = new BasicProvider().getStorageVolumeOptionTypes()

		then:
		method.default
		method.declaringClass == ProvisionProvider
		first.empty
		second.empty
		other.empty
		!first.is(second)
		!first.is(other)
		first.class == provider.getOptionTypes().class
		first.class == provider.getNodeOptionTypes().class

		when:
		first.add(new OptionType(code: 'disk-cache'))

		then:
		first.size() == 1
		second.empty
		other.empty
		provider.getStorageVolumeOptionTypes().empty
		provider.getOptionTypes().empty
		provider.getNodeOptionTypes().empty

		where:
		provider << [new BasicProvider(), new LegacyProvider()]
	}

	void "provider override metadata survives model assignment and JSON serialization without filtering"() {
		given:
		def metadata = [
			code: 'disk-cache', name: 'Disk cache', fieldLabel: 'Cache enabled',
			fieldContext: 'config', fieldName: 'cache.enabled',
			fieldSetContext: 'config', fieldGetContext: 'config',
			fieldSetName: 'storedCache.enabled', fieldGetName: 'storedCache.enabled',
			enabled: false, editable: false, ignoreField: true, required: true,
			inputType: OptionType.InputType.CHECKBOX, defaultValue: 'false'
		]
		def option = new OptionType(metadata)
		def provider = new OptionsProvider(options: [option])
		def mapper = new ObjectMapper()

		when:
		def model = new ProvisionType(storageVolumeOptionTypes: provider.getStorageVolumeOptionTypes())
		def storageType = new StorageVolumeType(provisionOptionTypes: [option])
		def wire = mapper.readValue(mapper.writeValueAsString(model), Map)
		def storageTypeWire = mapper.readValue(mapper.writeValueAsString(storageType), Map)
		def wireMetadata = metadata + [inputType: [value: 'checkbox']]

		then:
		model.storageVolumeOptionTypes.is(provider.options)
		model.dirtyPropertyValues.storageVolumeOptionTypes.is(provider.options)
		model.storageVolumeOptionTypes.first().is(option)
		metadata.every { key, value -> model.storageVolumeOptionTypes.first()."$key" == value }
		wireMetadata.every { key, value -> wire.storageVolumeOptionTypes.first()[key] == value }
		wireMetadata.every { key, value -> storageTypeWire.provisionOptionTypes.first()[key] == value }
		storageTypeWire.optionTypes == null
		provider.getOptionTypes().empty
		provider.getNodeOptionTypes().empty
	}

	static class BasicProvider implements ProvisionProvider {
		MorpheusContext getMorpheus() { null }
		Plugin getPlugin() { null }
		String getCode() { 'volume-options-test' }
		String getName() { 'Volume options test' }
	}

	static class LegacyProvider implements com.morpheusdata.core.ProvisionProvider {
		MorpheusContext getMorpheus() { null }
		Plugin getPlugin() { null }
		String getCode() { 'legacy-volume-options-test' }
		String getName() { 'Legacy volume options test' }
	}

	static class OptionsProvider extends BasicProvider {
		Collection<OptionType> options

		@Override
		Collection<OptionType> getStorageVolumeOptionTypes() { options }
	}
}
