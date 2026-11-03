package com.morpheusdata.core.providers

import com.morpheusdata.model.Cloud
import com.morpheusdata.model.VirtualImageLocation
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class CloudProviderVirtualImageDeleteSpec extends Specification {

	void "CloudProvider does not delete virtual images from the cloud by default"() {
		given:
		CloudProvider provider = Stub(CloudProvider) {
			canDeleteVirtualImages() >> { callRealMethod() }
		}

		expect:
		provider.canDeleteVirtualImages() == false
	}

	void "deleting a virtual image location is an error with a message by default"() {
		given:
		CloudProvider provider = Stub(CloudProvider) {
			deleteVirtualImageLocation(_, _) >> { callRealMethod() }
		}

		when:
		ServiceResponse response = provider.deleteVirtualImageLocation(new Cloud(), new VirtualImageLocation())

		then:
		response.success == false
		response.msg
	}
}
