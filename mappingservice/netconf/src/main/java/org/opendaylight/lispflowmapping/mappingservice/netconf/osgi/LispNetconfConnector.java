package org.opendaylight.lispflowmapping.mappingservice.netconf.osgi;

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
	
	private ObjectName bindingBrokerRegistry;
	private ObjectName domRegistry;
	private ObjectName eventExecutor;
	private ObjectName threadpool;
	private ObjectName clientDispatcher;

	public LispNetconfConnector() {
		platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

		takeNap(20000);
		
		configRegistryClient = new ConfigRegistryJMXClient(platformMBeanServer);
		factory = new NetconfConnectorModuleFactory();
		
		System.out.println("REGISTRY AND FACTORY CONSTRUCTED");

		try {
			createNetconfConnector("testNetconf");
			createNetconfConnector("testNetconf1");
		} catch (InstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	public LispNetconfConnector(ConfigRegistryJMXClient configRegistryClient) {
		this.configRegistryClient = configRegistryClient;
		try {
			createNetconfConnector("testNetconf");
			createNetconfConnector("testNetconf1");
		} catch (Exception e) {
			
		}
	}

	private void createNetconfConnector(String instanceName) throws InstanceAlreadyExistsException {
		
		System.out.println("OMG netconf connector CALLED!");
		
        ConfigTransactionJMXClient transaction = configRegistryClient.createTransaction();
        
        if (transaction == null) {
        	System.out.println("TRANSACTION NOT INITIALIZED!");
        }
        
        String module = factory.getImplementationName();
        ObjectName nameCreated = transaction.createModule(module, instanceName);
        NetconfConnectorModuleMXBean mxBean = transaction.newMXBeanProxy(nameCreated, NetconfConnectorModuleMXBean.class);
        
        Host host = new Host("127.0.0.1".toCharArray());
        mxBean.setAddress(host);
        mxBean.setPassword("netconf");
        mxBean.setPort(new PortNumber(830));
        mxBean.setUsername("netconf");
        mxBean.setTcpOnly(false);

        solveDependencies(transaction, mxBean);
        
        try {
        	transaction.commit();
        } catch (Exception e) {
        	
        }
	}
	
	private void solveDependencies(ConfigTransactionJMXClient transaction, NetconfConnectorModuleMXBean mxBean) {
		
    	bindingBrokerRegistry = findConfigBean(BindingBrokerImplModuleFactory.NAME, transaction);
    	if (bindingBrokerRegistry != null ) {
    		mxBean.setBindingRegistry(bindingBrokerRegistry);
    	} else {
    		System.out.println("NO BINDING BROKER INSTANCE");
    	}

        domRegistry = findConfigBean(DomBrokerImplModuleFactory.NAME, transaction);
        if (domRegistry != null) {
        	mxBean.setDomRegistry(domRegistry);
        } else {
        	System.out.println("NO DOM REGISTRY BROKER INSTANCE");
        }
        
        eventExecutor = findConfigBean(GlobalEventExecutorModuleFactory.NAME, transaction);
        if (eventExecutor != null) {
            mxBean.setEventExecutor(eventExecutor);
        } else {
        	System.out.println("NO EVENT EXECUTOR INSTANCE");
        }
        
        
        threadpool = findConfigBean(FlexibleThreadPoolModuleFactory.NAME, transaction);
        if (threadpool != null) {
            mxBean.setProcessingExecutor(threadpool);
        } else {
        	System.out.println("NO THREADPOOL INSTANCE");
        }
        
        clientDispatcher = findConfigBean(NetconfClientDispatcherModuleFactory.NAME, transaction);
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
	
	private ObjectName createBindingBrokerRegistry(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
		System.out.println("Building BINDING Broker!");
		ObjectName domRegistry = transaction.createModule(BindingBrokerImplModuleFactory.NAME, "binding-osgi-broker");
		return domRegistry;
	}
	
	private ObjectName createDomRegistry(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
		System.out.println("Building DOM REGISTRY!");
		ObjectName domRegistry = transaction.createModule(DomBrokerImplModuleFactory.NAME, "dom-broker");
		return domRegistry;
	}
	
	private ObjectName createNettyEventExecutor(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
        ObjectName eventExecutor = transaction.createModule(GlobalEventExecutorModuleFactory.NAME, "global-event-executor");
        return eventExecutor;
	}
	
//	private ObjectName createThreadpool(ConfigTransactionJMXClient transaction)  {
//		FixedThreadPoolModuleFactory tpfactory;
//		String name = "threadpool";
//		Integer numberOfThreads = 1;
//		String prefix = name;
//		ObjectName nameCreated = null;
//		
//        try {
//			nameCreated = transaction.createModule(FixedThreadPoolModuleFactory.NAME, name);
//		} catch (InstanceAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        FixedThreadPoolModuleMXBean mxBean = transaction.newMXBeanProxy(nameCreated, FixedThreadPoolModuleMXBean.class);
//        mxBean.setMaxThreadCount(numberOfThreads);
//
//        ObjectName threadFactoryON = null;
//		try {
//			threadFactoryON = transaction.createModule(NamingThreadFactoryModuleFactory.NAME, "lisp-thread-naming");
//		} catch (InstanceAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        NamingThreadFactoryModuleMXBean namingThreadFactoryModuleMXBean = transaction.newMXBeanProxy(threadFactoryON,
//                NamingThreadFactoryModuleMXBean.class);
//        namingThreadFactoryModuleMXBean.setNamePrefix(prefix);
//
//        mxBean.setThreadFactory(threadFactoryON);
//
//		return nameCreated;
//	}
	
}
