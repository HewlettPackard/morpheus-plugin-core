package com.morpheusdata.model

import spock.lang.Specification
import spock.lang.Unroll

class FirmwareTypesSpec extends Specification {

	void "firmware models expose the documented component, inventory, baseline, and conformance fields"() {
		given:
		def component = new FirmwareComponent(
			name: 'NIC',
			type: 'NIC',
			installed: '1.0',
			baseline: '2.0',
			status: ConformanceStatus.NON_CONFORMANT
		)
		def inventory = new FirmwareInventory(
			serverModel: 'ProLiant',
			bmcType: 'iLO',
			bmcVersion: '3.10',
			biosVersion: 'U46 v2.90',
			components: [component]
		)
		def baseline = new FirmwareBaseline(
			id: 'spp-2026.06',
			name: 'SPP',
			version: '2026.06.0',
			source: 'uploaded',
			bundlePath: '/bundles/spp.iso',
			components: [component]
		)
		def result = new FirmwareConformanceResult(
			overallStatus: ConformanceStatus.NON_CONFORMANT,
			components: [component],
			conformantCount: 1,
			driftCount: 1
		)

		expect:
		inventory.serverModel == 'ProLiant'
		inventory.bmcType == 'iLO'
		inventory.bmcVersion == '3.10'
		inventory.biosVersion == 'U46 v2.90'
		inventory.components == [component]
		component.name == 'NIC'
		component.type == 'NIC'
		component.installed == '1.0'
		component.baseline == '2.0'
		component.status == ConformanceStatus.NON_CONFORMANT
		baseline.id == 'spp-2026.06'
		baseline.name == 'SPP'
		baseline.version == '2026.06.0'
		baseline.source == 'uploaded'
		baseline.bundlePath == '/bundles/spp.iso'
		baseline.components == [component]
		result.overallStatus == ConformanceStatus.NON_CONFORMANT
		result.components == [component]
		result.conformantCount == 1
		result.driftCount == 1
	}

	@Unroll
	void "#modelType.simpleName starts with its own empty mutable component list"() {
		given:
		def first = modelType.getDeclaredConstructor().newInstance()
		def second = modelType.getDeclaredConstructor().newInstance()
		def component = new FirmwareComponent(name: 'BIOS')

		expect:
		first.components != null
		first.components.empty
		second.components != null
		second.components.empty

		when:
		first.components.add(component)

		then:
		first.components == [component]
		second.components.empty

		where:
		modelType << [FirmwareInventory, FirmwareBaseline, FirmwareConformanceResult]
	}

	void "firmware staging exposes the shared staging result without lifecycle dependencies"() {
		given:
		StageResult result = new FirmwareStageResult(staged: true, statusMessage: 'Bundle staged')

		expect:
		result.staged
		result.statusMessage == 'Bundle staged'
		!new FirmwareStageResult().staged
	}
}
