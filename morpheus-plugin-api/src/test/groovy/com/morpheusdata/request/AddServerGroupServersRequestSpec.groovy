package com.morpheusdata.request

import com.morpheusdata.model.ComputeServerType
import com.morpheusdata.model.ServicePlan
import com.morpheusdata.model.StorageVolume
import com.morpheusdata.model.ComputeServerInterface
import spock.lang.Specification

class AddServerGroupServersRequestSpec extends Specification {

	def "defaults are set correctly"() {
		when:
		def req = new AddServerGroupServersRequest()

		then:
		req.nodeCount == 1
		req.licenseCheck == true
		req.serverType == null
		req.serverName == null
		req.plan == null
		req.volumes == null
		req.networkInterfaces == null
		req.securityGroupIds == null
	}

	def "fields can be populated"() {
		given:
		def serverType = new ComputeServerType(id: 10L, code: 'worker')
		def plan = new ServicePlan(id: 5L, name: 'Small')
		def vol = new StorageVolume(id: 1L)
		def nic = new ComputeServerInterface(id: 1L)

		when:
		def req = new AddServerGroupServersRequest(
			serverType: serverType,
			serverName: 'worker-3',
			hostname: 'worker-3',
			plan: plan,
			config: [resourcePoolId: 42L],
			zoneId: 100L,
			siteId: 1L,
			nodeCount: 3,
			volumes: [vol],
			networkInterfaces: [nic],
			securityGroupIds: [200L],
			licenseCheck: false
		)

		then:
		req.serverType.code == 'worker'
		req.serverName == 'worker-3'
		req.plan.id == 5L
		req.config.resourcePoolId == 42L
		req.zoneId == 100L
		req.siteId == 1L
		req.nodeCount == 3
		req.volumes.size() == 1
		req.networkInterfaces.size() == 1
		req.securityGroupIds == [200L]
		req.licenseCheck == false
	}

	def "licenseCheck can be explicitly disabled"() {
		when:
		def req = new AddServerGroupServersRequest(licenseCheck: false)

		then:
		req.licenseCheck == false
	}
}
