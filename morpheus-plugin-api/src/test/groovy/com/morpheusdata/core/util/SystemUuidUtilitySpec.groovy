package com.morpheusdata.core.util

import spock.lang.Specification

class SystemUuidUtilitySpec extends Specification {

	void "normalize accepts valid formats"() {
		expect:
		SystemUuidUtility.normalize(input) == '4c4c4544-0042-3510-8051-b4c04f4e4d32'
		SystemUuidUtility.isValid(input)

		where:
		input << [
			'4C4C4544-0042-3510-8051-B4C04F4E4D32',
			'4c4c4544-0042-3510-8051-b4c04f4e4d32',
			'4C4C4544004235108051B4C04F4E4D32',
			'{4C4C4544-0042-3510-8051-B4C04F4E4D32}',
			'{4c4c4544004235108051b4c04f4e4d32}',
			"  4C4C4544-0042-3510-8051-B4C04F4E4D32\n"
		]
	}

	void "normalize rejects invalid values"() {
		expect:
		SystemUuidUtility.normalize(input) == null
		!SystemUuidUtility.isValid(input)

		where:
		input << [
			null,
			'',
			'   ',
			'00000000-0000-0000-0000-000000000000',
			'00000000000000000000000000000000',
			'FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF',
			'ffffffffffffffffffffffffffffffff',
			'4c4c4544004235108051b4c04f4e4d3',
			'4c4c4544004235108051b4c04f4e4d321',
			'4g4c4544-0042-3510-8051-b4c04f4e4d32',
			'4c4c-4544-0042-3510-8051b4c04f4e4d32',
			'{{4c4c4544-0042-3510-8051-b4c04f4e4d32}}',
			'{4c4c4544-0042-3510-8051-b4c04f4e4d32',
			'1-2-3-4-5'
		]
	}
}
