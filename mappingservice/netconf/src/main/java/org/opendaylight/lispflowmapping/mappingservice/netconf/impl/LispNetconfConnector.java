package org.opendaylight.lispflowmapping.mappingservice.netconf.impl;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.opendaylight.controller.config.util.ConfigRegistryJMXClient;
import org.opendaylight.controller.config.util.ConfigTransactionJMXClient;
import org.opendaylight.controller.config.yang.md.sal.binding.impl.BindingBrokerImplModuleFactory;
import org.opendaylight.controller.config.yang.md.sal.connector.netconf.NetconfConnectorModuleFactory;
import org.opendaylight.controller.config.yang.md.sal.connector.netconf.NetconfConnectorModuleMXBean;
import org.opendaylight.controller.config.yang.md.sal.dom.impl.DomBrokerImplModuleFactory;
import org.opendaylight.controller.config.yang.netty.eventexecutor.GlobalEventExecutorModuleFactory;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Host;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.controller.config.yang.threadpool.impl.flexible.FlexibleThreadPoolModuleFactory;
import org.opendaylight.controller.config.yang.config.netconf.client.dispatcher.NetconfClientDispatcherModuleFactory;

public class LispNetconfConnector {
    private ConfigRegistryJMXClient configRegistryClient;
    
    private MBeanServer platformMBeanServer;
	private NetconfConnectorModuleFactory factory;
	
	public LispNetconfConnector() {
		platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

		takeNap(20000);
		
		configRegistryClient = new ConfigRegistryJMXClient(platformMBeanServer);
		factory = new NetconfConnectorModuleFactory();
		
		System.out.println("REGISTRY AND FACTORY CONSTRUCTED");

	}
	
	private void takeNap(Integer time) {
		try {
			System.out.println("GOING TO SLEEP " + time);
			Thread.sleep(time);
			System.out.println("FINISHED SLEEPING THE " + time);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createNetconfConnector(String address, Integer port, String username, String password) throws InstanceAlreadyExistsException {
		
        ConfigTransactionJMXClient transaction = configRegistryClient.createTransaction();
        
        if (transaction == null) {
        	System.out.println("TRANSACTION NOT INITIALIZED!");
        }
        
        String instanceName = address;
        String module = factory.getImplementationName();
        ObjectName nameCreated = transaction.createModule(module, instanceName);
        NetconfConnectorModuleMXBean mxBean = transaction.newMXBeanProxy(nameCreated, NetconfConnectorModuleMXBean.class);
        
        mxBean.setAddress(new Host(address.toCharArray()));
        mxBean.setPassword(password);
        mxBean.setPort(new PortNumber(port));
        mxBean.setUsername(username);
        mxBean.setTcpOnly(false);

        solveDependencies(transaction, mxBean);
        
        try {
        	transaction.commit();
        } catch (Exception e) {
        	
        }
	}
	
	private void solveDependencies(ConfigTransactionJMXClient transaction, NetconfConnectorModuleMXBean mxBean) {
		
    	ObjectName bindingBrokerRegistry = findConfigBean(BindingBrokerImplModuleFactory.NAME, transaction);
    	if (bindingBrokerRegistry != null ) {
    		mxBean.setBindingRegistry(bindingBrokerRegistry);
    	} else {
    		System.out.println("NO BINDING BROKER INSTANCE");
    	}

        ObjectName domRegistry = findConfigBean(DomBrokerImplModuleFactory.NAME, transaction);
        if (domRegistry != null) {
        	mxBean.setDomRegistry(domRegistry);
        } else {
        	System.out.println("NO DOM REGISTRY BROKER INSTANCE");
        }
        
        ObjectName eventExecutor = findConfigBean(GlobalEventExecutorModuleFactory.NAME, transaction);
        if (eventExecutor != null) {
            mxBean.setEventExecutor(eventExecutor);
        } else {
        	System.out.println("NO EVENT EXECUTOR INSTANCE");
        }
        
        
        ObjectName threadpool = findConfigBean(FlexibleThreadPoolModuleFactory.NAME, transaction);
        if (threadpool != null) {
            mxBean.setProcessingExecutor(threadpool);
        } else {
        	System.out.println("NO THREADPOOL INSTANCE");
        }
        
        ObjectName clientDispatcher = findConfigBean(NetconfClientDispatcherModuleFactory.NAME, transaction);
        if (threadpool != null) {
            mxBean.setClientDispatcher(clientDispatcher);
        } else {
        	System.out.println("NO CLIENT DISPATCHER INSTANCE");
        }
        
	}
	
	private ObjectName findConfigBean(String name, ConfigTransactionJMXClient transaction) {
    	Set<ObjectName> set = transaction.lookupConfigBeans(name);
    	System.out.println("Found " + set.size() + " " + name + " items!");
    	if (set.size() > 0) {
    		return set.iterator().next();
    	} else {
    		return null;
    	}
	}
	
	
}
