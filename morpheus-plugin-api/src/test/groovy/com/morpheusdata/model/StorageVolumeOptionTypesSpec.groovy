package com.morpheusdata.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class StorageVolumeOptionTypesSpec extends Specification {

	void "volume option collections track first assignment replacement empty and null as new values"() {
		given:
		def first = [new OptionType(code: 'disk-cache')]
		def replacement = [new OptionType(code: 'disk-policy')]
		def empty = new ArrayList<OptionType>()

		expect:
		model."$property" == null
		!model.isDirty(property)

		when:
		model."$property" = first

		then:
		model."$property".is(first)
		model.dirtyPropertyValues == [(property): first]
		model.dirtyPropertyValues[property].is(first)

		when:
		model.markClean()
		model."$property" = replacement

		then:
		model."$property".is(replacement)
		model.dirtyPropertyValues == [(property): replacement]
		model.dirtyPropertyValues[property].is(replacement)

		when:
		model.markClean()
		model."$property" = empty

		then:
		model."$property".is(empty)
		model.dirtyPropertyValues == [(property): []]
		model.dirtyPropertyValues[property].is(empty)

		when:
		model.markClean()
		model."$property" = null

		then:
		model."$property" == null
		model.isDirty(property)
		model.dirtyPropertyValues.containsKey(property)
		model.dirtyPropertyValues == [(property): null]

		where:
		model                   | property
		new ProvisionType()     | 'storageVolumeOptionTypes'
		new StorageVolumeType() | 'optionTypes'
		new StorageVolumeType() | 'provisionOptionTypes'
	}

	void "standalone and provisioning options remain independent in the model dirty values and JSON"() {
		given:
		def standalone = [new OptionType(code: 'standalone-datastore', fieldContext: 'config', fieldName: 'datastore')]
		def provisioning = [new OptionType(code: 'disk-cache', fieldContext: 'config', fieldName: 'cache.enabled')]
		def type = new StorageVolumeType(optionTypes: standalone, provisionOptionTypes: provisioning)
		def mapper = new ObjectMapper()

		when:
		def wire = mapper.readValue(mapper.writeValueAsString(type), Map)

		then:
		type.optionTypes.is(standalone)
		type.provisionOptionTypes.is(provisioning)
		type.dirtyPropertyValues.optionTypes.is(standalone)
		type.dirtyPropertyValues.provisionOptionTypes.is(provisioning)
		wire.optionTypes*.code == ['standalone-datastore']
		wire.provisionOptionTypes*.code == ['disk-cache']

		when:
		type.markClean()
		type.provisionOptionTypes = []

		then:
		type.optionTypes.is(standalone)
		type.provisionOptionTypes.empty
		type.dirtyPropertyValues == [provisionOptionTypes: []]

		when:
		type.markClean()
		type.provisionOptionTypes = null

		then:
		type.optionTypes.is(standalone)
		type.provisionOptionTypes == null
		type.dirtyPropertyValues == [provisionOptionTypes: null]

		when:
		type.provisionOptionTypes = provisioning
		type.markClean()
		type.optionTypes = null

		then:
		type.optionTypes == null
		type.provisionOptionTypes.is(provisioning)
		type.dirtyPropertyValues == [optionTypes: null]
	}

	void "legacy standalone options do not implicitly supply provisioning options"() {
		given:
		def type = new StorageVolumeType(optionTypes: [
			new OptionType(code: 'standalone-config', fieldContext: 'config', fieldName: 'cache.enabled')
		])

		expect:
		type.provisionOptionTypes == null
		!type.isDirty('provisionOptionTypes')
	}
}
