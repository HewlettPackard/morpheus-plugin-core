package com.morpheusdata.model

import spock.lang.Specification

class StorageVolumeSpec extends Specification {

	void "virtualImageId is null and clean on a new volume"() {
		when:
		def volume = new StorageVolume()

		then:
		volume.virtualImageId == null
		!volume.isDirty('virtualImageId')
	}

	void "setting virtualImageId returns it and marks it dirty"() {
		given:
		def volume = new StorageVolume()

		when:
		volume.virtualImageId = 42L

		then:
		volume.virtualImageId == 42L
		volume.isDirty('virtualImageId')
		volume.dirtyPropertyValues['virtualImageId'] == 42L
	}

	void "clearing virtualImageId marks it dirty with a null value"() {
		given:
		def volume = new StorageVolume(virtualImageId: 42L)
		volume.markClean()

		when:
		volume.virtualImageId = null

		then:
		volume.virtualImageId == null
		volume.isDirty('virtualImageId')
		volume.dirtyPropertyValues.containsKey('virtualImageId')
		volume.dirtyPropertyValues['virtualImageId'] == null
	}

	void "virtualImageId binds through the map constructor"() {
		when:
		def volume = new StorageVolume(virtualImageId: 7L)

		then:
		volume.virtualImageId == 7L
	}
}
