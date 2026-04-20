package com.morpheusdata.model

import com.morpheusdata.model.license.ApplianceLicenseData
import com.morpheusdata.model.license.ApplianceLicenseInfo
import com.morpheusdata.model.license.ApplianceLicenseLimit
import com.morpheusdata.model.license.ApplianceLicenseUsage
import spock.lang.Specification

class ApplianceLicenseSpec extends Specification {

	void "ApplianceLicenseInfo initializes with empty lists"() {
		when:
		def info = new ApplianceLicenseInfo()

		then:
		info.installedLicenses != null
		info.installedLicenses.isEmpty()
		info.licenseLimits != null
		info.licenseLimits.isEmpty()
		info.license == null
		info.currentUsage == null
		info.limitReached == null
	}

	void "ApplianceLicenseData fields are set and retrieved correctly"() {
		given:
		def license = new ApplianceLicenseData()

		when:
		license.id = 1L
		license.keyId = 'abc12345'
		license.productTier = 'pro'
		license.limitType = 'standard'
		license.maxManagedServers = 100L
		license.maxSockets = 50L
		license.multiTenant = true
		license.whiteLabel = false
		license.features = ['dashboard': true, 'guidance': false]
		license.zoneTypes = ['vmware', 'amazon']
		license.taskTypes = ['script', 'ansible']

		then:
		license.id == 1L
		license.keyId == 'abc12345'
		license.productTier == 'pro'
		license.limitType == 'standard'
		license.maxManagedServers == 100L
		license.maxSockets == 50L
		license.multiTenant == true
		license.whiteLabel == false
		license.features == ['dashboard': true, 'guidance': false]
		license.zoneTypes == ['vmware', 'amazon']
		license.taskTypes == ['script', 'ansible']
	}

	void "ApplianceLicenseUsage fields are set and retrieved correctly"() {
		given:
		def usage = new ApplianceLicenseUsage()

		when:
		usage.managedServers = 42L
		usage.discoveredServers = 10L
		usage.sockets = 5L
		usage.workloads = 100L
		usage.memory = 1073741824L
		usage.storage = 107374182400L

		then:
		usage.managedServers == 42L
		usage.discoveredServers == 10L
		usage.sockets == 5L
		usage.workloads == 100L
		usage.memory == 1073741824L
		usage.storage == 107374182400L
	}

	void "ApplianceLicenseLimit fields are set and retrieved correctly"() {
		given:
		def limit = new ApplianceLicenseLimit()

		when:
		limit.code = 'maxManagedServers'
		limit.max = 100L
		limit.used = 85L
		limit.percentUsed = 0.85d
		limit.warning = true
		limit.limitReached = false
		limit.unit = null

		then:
		limit.code == 'maxManagedServers'
		limit.max == 100L
		limit.used == 85L
		limit.percentUsed == 0.85d
		limit.warning == true
		limit.limitReached == false
		limit.unit == null
	}

	void "ApplianceLicenseInfo is populated with license and usage"() {
		given:
		def info = new ApplianceLicenseInfo()
		def license = new ApplianceLicenseData(productTier: 'enterprise', limitType: 'standard', maxSockets: 200L)
		def usage = new ApplianceLicenseUsage(sockets: 50L)
		def limit = new ApplianceLicenseLimit(code: 'maxSockets', max: 200L, used: 50L, percentUsed: 0.25d, warning: false, limitReached: false)

		when:
		info.license = license
		info.currentUsage = usage
		info.licenseLimits << limit
		info.limitReached = false

		then:
		info.license.productTier == 'enterprise'
		info.currentUsage.sockets == 50L
		info.licenseLimits.size() == 1
		info.licenseLimits[0].code == 'maxSockets'
		info.limitReached == false
	}
}
