package com.morpheusdata.core.providers

import com.morpheusdata.model.NetworkServer
import com.morpheusdata.model.NetworkSwitch
import com.morpheusdata.model.NetworkSwitchPort
import com.morpheusdata.model.NetworkSwitchPortState
import com.morpheusdata.model.NetworkSwitchPortType
import com.morpheusdata.model.NetworkSwitchWithPorts
import com.morpheusdata.response.ServiceResponse
import spock.lang.Specification

class SwitchesProviderSpec extends Specification {

	void "NetworkProvider does not support switches by default"() {
		given:
		NetworkProvider provider = Stub(NetworkProvider) {
			getSwitchesProvider() >> { callRealMethod() }
			hasSwitches() >> { callRealMethod() }
		}

		expect:
		provider.getSwitchesProvider() == null
		!provider.hasSwitches()
	}

	void "SwitchesProvider is nested under NetworkProvider via getSwitchesProvider"() {
		given:
		SwitchesProvider switches = Stub(SwitchesProvider)
		NetworkProvider provider = Stub(NetworkProvider) {
			hasSwitches() >> true
			getSwitchesProvider() >> switches
		}

		expect:
		provider.hasSwitches()
		provider.getSwitchesProvider().is(switches)
	}

	void "switches are listed with port metadata and port operations return updated ports"() {
		given:
		def port = new NetworkSwitchPort(
			externalId: 'eth1', name: 'Ethernet1', macAddress: '00:11:22:33:44:55',
			portType: NetworkSwitchPortType.TRUNK, portState: NetworkSwitchPortState.UP,
			taggedVlans: [10, 20], untaggedVlans: [1]
		)
		def sw = new NetworkSwitch(name: 'leaf1', externalId: 'sw-1')
		def server = new NetworkServer()
		SwitchesProvider provider = Stub(SwitchesProvider) {
			listSwitches(server) >> ServiceResponse.success([new NetworkSwitchWithPorts(sw, [port])])
			setPortDown(server, sw, port) >> { ServiceResponse.success(new NetworkSwitchPort(portState: NetworkSwitchPortState.DOWN)) }
			setPortType(server, sw, port, NetworkSwitchPortType.ACCESS) >> ServiceResponse.success(new NetworkSwitchPort(portType: NetworkSwitchPortType.ACCESS))
			addPortVlan(server, sw, port, 30, true) >> ServiceResponse.success(new NetworkSwitchPort(taggedVlans: [10, 20, 30]))
			removePortVlan(server, sw, port, 10) >> ServiceResponse.success(new NetworkSwitchPort(taggedVlans: [20]))
		}

		when:
		def listed = provider.listSwitches(server).data.first()

		then:
		listed.networkSwitch.is(sw)
		listed.ports.size() == 1
		with(listed.ports.first()) {
			macAddress == '00:11:22:33:44:55'
			portType == NetworkSwitchPortType.TRUNK
			portState == NetworkSwitchPortState.UP
			taggedVlans == [10, 20]
			untaggedVlans == [1]
		}
		provider.setPortDown(server, sw, port).data.portState == NetworkSwitchPortState.DOWN
		provider.setPortType(server, sw, port, NetworkSwitchPortType.ACCESS).data.portType == NetworkSwitchPortType.ACCESS
		provider.addPortVlan(server, sw, port, 30, true).data.taggedVlans == [10, 20, 30]
		provider.removePortVlan(server, sw, port, 10).data.taggedVlans == [20]
	}

	void "port type enum covers access, hybrid and trunk"() {
		expect:
		NetworkSwitchPortType.values()*.name().toSet() == ['ACCESS', 'HYBRID', 'TRUNK'].toSet()
	}
}
