package com.morpheusdata.model

import spock.lang.Specification

class ConfigurationWorkflowStepSpec extends Specification {

	void "dependsOn defaults to an empty list"() {
		when:
		def step = new ConfigurationWorkflowStep()

		then:
		step.getDependsOn() != null
		step.getDependsOn().isEmpty()
	}

	void "setDependsOn / getDependsOn roundtrip"() {
		given:
		def step = new ConfigurationWorkflowStep()
		def deps = ['step-a', 'step-b']

		when:
		step.setDependsOn(deps)

		then:
		step.getDependsOn() == ['step-a', 'step-b']
	}

	void "setDependsOn marks the field dirty"() {
		given:
		def step = new ConfigurationWorkflowStep()
		def deps = ['step-x']

		when:
		step.setDependsOn(deps)

		then:
		step.isDirty('dependsOn')
		step.getDirtyPropertyValues()['dependsOn'] == deps
	}

	void "setDependsOn with empty list clears dependencies and marks dirty"() {
		given:
		def step = new ConfigurationWorkflowStep()
		step.setDependsOn(['step-a'])
		step.markClean()

		when:
		step.setDependsOn([])

		then:
		step.getDependsOn().isEmpty()
		step.isDirty('dependsOn')
	}

	void "setDependsOn with null replaces the list"() {
		given:
		def step = new ConfigurationWorkflowStep()
		step.setDependsOn(['step-a'])

		when:
		step.setDependsOn(null)

		then:
		step.getDependsOn() == null
	}
}
