package org.opendaylight.lispflowmapping.netconf.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Host;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.rev140706.BuildConnectorInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.rev140706.BuildConnectorInputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;

public class LispDeviceNetconfConnectorTest {

	@Test
	public void testMakeConnector() throws Exception{
		LispNetconfConnector mockedConnector = getLispNetconfConnector();
		LispDeviceNetconfConnector testedLispDeviceNetconfConnector = new LispDeviceNetconfConnector(Executors.newFixedThreadPool(1), mockedConnector);
		BuildConnectorInput conInput = connectorInput("n1", "1.1.1.1", 830, "user", "pass");
		Future<RpcResult<Void>> res = testedLispDeviceNetconfConnector.buildConnector(conInput);
		assertEquals(res.get().isSuccessful(), true);
	}

	@Test
	public void testMakeConnectorFail() throws Exception{
		LispNetconfConnector mockedConnector = getLispNetconfConnector();
		LispDeviceNetconfConnector testedLispDeviceNetconfConnector = new LispDeviceNetconfConnector(Executors.newFixedThreadPool(1), mockedConnector);

		assertEquals(testedLispDeviceNetconfConnector.buildConnector(connectorInput(null, "1.1.1.1", 830, "user", "pass")).get().isSuccessful(), false);
		assertEquals(testedLispDeviceNetconfConnector.buildConnector(connectorInput("n1", null, 830, "user", "pass")).get().isSuccessful(), false);
		assertEquals(testedLispDeviceNetconfConnector.buildConnector(connectorInput("n1", "1.1.1.1", null, "user", "pass")).get().isSuccessful(), false);
		assertEquals(testedLispDeviceNetconfConnector.buildConnector(connectorInput("n1", "1.1.1.1", 830, null, "pass")).get().isSuccessful(), false);
		assertEquals(testedLispDeviceNetconfConnector.buildConnector(connectorInput("n1", "1.1.1.1", 830, "user", null)).get().isSuccessful(), false);

	}

	private BuildConnectorInput connectorInput(String instance, String address, Integer port, String user, String pass) {
		BuildConnectorInputBuilder input = new BuildConnectorInputBuilder();
		input.setInstance(instance);
		input.setAddress(address == null ? null : new Host(new IpAddress(new Ipv4Address(address))));
		input.setPort(port == null ? null : new PortNumber(port));
		input.setUsername(user);
		input.setPassword(pass);
		return input.build();
	}

	private LispNetconfConnector getLispNetconfConnector() throws Exception{
		LispNetconfConnector conn =  mock(LispNetconfConnector.class);
		doNothing().when(conn).createNetconfConnector(any(String.class), any(Host.class), any(Integer.class), any(String.class), any(String.class));
		return(conn);
	}
}
