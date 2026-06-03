package com.morpheusdata.response

import spock.lang.Specification

class ValidateConvertToManagedResponseSpec extends Specification {

	void "can be instantiated"() {
		when:
		def response = new ValidateConvertToManagedResponse()

		then:
		response != null
	}

	void "can be wrapped in a successful ServiceResponse"() {
		given:
		def response = new ValidateConvertToManagedResponse()

		when:
		ServiceResponse<ValidateConvertToManagedResponse> serviceResponse = ServiceResponse.success(response)

		then:
		serviceResponse.success
		serviceResponse.data.is(response)
	}

	void "can be wrapped in an error ServiceResponse"() {
		when:
		ServiceResponse<ValidateConvertToManagedResponse> serviceResponse = ServiceResponse.error("Validation failed")

		then:
		!serviceResponse.success
		serviceResponse.error == "Validation failed"
	}
}
