package com.morpheusdata.core.providers

import com.morpheusdata.model.ComputeServer
import com.morpheusdata.request.AfterConvertToManagedRequest
import com.morpheusdata.request.BeforeConvertToManagedRequest
import com.morpheusdata.request.ValidateConvertToManagedRequest
import com.morpheusdata.response.AfterConvertToManagedResponse
import com.morpheusdata.response.BeforeConvertToManagedResponse
import com.morpheusdata.response.ServiceResponse
import com.morpheusdata.response.ValidateConvertToManagedResponse
import spock.lang.Specification

class ConvertToManagedFacetSpec extends Specification {

	void "default validateConvertToManaged returns success"() {
		given:
		def facet = new TestConvertToManagedFacet()
		def request = new ValidateConvertToManagedRequest()

		when:
		ServiceResponse<ValidateConvertToManagedResponse> response = facet.validateConvertToManaged(request)

		then:
		response.success
		response.data instanceof ValidateConvertToManagedResponse
	}

	void "default validateConvertToManaged with null request returns success"() {
		given:
		def facet = new TestConvertToManagedFacet()

		when:
		ServiceResponse<ValidateConvertToManagedResponse> response = facet.validateConvertToManaged(null)

		then:
		response.success
		response.data instanceof ValidateConvertToManagedResponse
	}

	void "default validateConvertToManaged with populated request returns success"() {
		given:
		def facet = new TestConvertToManagedFacet()
		def server = new ComputeServer(id: 42)
		def request = new ValidateConvertToManagedRequest(server: server, opts: [sshHost: '10.0.0.1'])

		when:
		ServiceResponse<ValidateConvertToManagedResponse> response = facet.validateConvertToManaged(request)

		then:
		response.success
		response.data instanceof ValidateConvertToManagedResponse
	}

	static class TestConvertToManagedFacet implements ProvisionProvider.ConvertToManagedFacet {
		@Override
		ServiceResponse<BeforeConvertToManagedResponse> beforeConvertToManaged(BeforeConvertToManagedRequest request) {
			return ServiceResponse.success(new BeforeConvertToManagedResponse())
		}

		@Override
		ServiceResponse<AfterConvertToManagedResponse> afterConvertToManaged(AfterConvertToManagedRequest request) {
			return ServiceResponse.success(new AfterConvertToManagedResponse())
		}
	}
}
