package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.model.StorageVolume
import spock.lang.Specification

class ProvisionProviderCanChangeVolumeDatastoreSpec extends Specification {

	void "canChangeVolumeDatastore defaults to false"() {
		given:
		// A spy of the interface runs the real default method
		ProvisionProvider provider = Spy(ProvisionProvider)

		expect:
		provider.canChangeVolumeDatastore(new ComputeServer(), new StorageVolume()) == false
		provider.canChangeVolumeDatastore(null, null) == false
	}

	void "a provider can opt in per volume by overriding the method"() {
		given:
		ProvisionProvider provider = Spy(ProvisionProvider) {
			canChangeVolumeDatastore(_ as ComputeServer, _ as StorageVolume) >> { ComputeServer s, StorageVolume v -> v.internalId == null }
		}

		expect:
		provider.canChangeVolumeDatastore(new ComputeServer(), new StorageVolume()) == true
		provider.canChangeVolumeDatastore(new ComputeServer(), new StorageVolume(internalId: '[ds] a.iso')) == false
	}
}
