package org.opendaylight.lispflowmapping.mappingservice.netconf;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.InstanceAlreadyExistsException;
//import javax.management.InstanceNotFoundException;

//import org.opendaylight.controller.config.yang.md.sal.dom.impl.DomBrokerImplModuleFactory;
//import org.opendaylight.controller.config.yang.md.sal.dom.BrokerServiceInterface;

import org.opendaylight.controller.config.yang.netty.eventexecutor.GlobalEventExecutorModuleFactory;
import org.opendaylight.controller.config.yang.threadpool.impl.NamingThreadFactoryModuleFactory;
import org.opendaylight.controller.config.yang.threadpool.impl.NamingThreadFactoryModuleMXBean;
import org.opendaylight.controller.config.yang.threadpool.impl.fixed.FixedThreadPoolModuleFactory;
import org.opendaylight.controller.config.yang.threadpool.impl.fixed.FixedThreadPoolModuleMXBean;
import org.opendaylight.controller.config.util.ConfigTransactionJMXClient;
import org.opendaylight.controller.config.util.ConfigRegistryJMXClient;
import org.opendaylight.controller.config.api.LookupRegistry;
import org.opendaylight.controller.config.yang.md.sal.binding.impl.BindingBrokerImplModuleFactory;
//import org.opendaylight.controller.config.api.ValidationException;
//import org.opendaylight.controller.config.api.ConflictingVersionException;
//import org.opendaylight.controller.config.yang.md.sal.connector.netconf.NetconfConnectorModule;
import org.opendaylight.controller.config.yang.md.sal.connector.netconf.NetconfConnectorModuleFactory;
import org.opendaylight.controller.config.yang.md.sal.connector.netconf.NetconfConnectorModuleMXBean;
import org.opendaylight.controller.config.yang.md.sal.dom.impl.DomBrokerImplModuleFactory;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Host;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Address;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.connector.netconf.rev131028.modules.module.configuration.sal.netconf.connector.EventExecutor;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.md.sal.connector.netconf.rev131028.modules.module.configuration.sal.netconf.connector.EventExecutorBuilder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;



public class LispDeviceNetconfConnector implements BundleActivator {
	public static BundleContext bc = null;
    final MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
    final ConfigRegistryJMXClient configRegistryClient = new ConfigRegistryJMXClient(platformMBeanServer);
	private NetconfConnectorModuleFactory factory;

	@Override
	public void start(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " starting...");
		LispDeviceNetconfConnector.bc = bc;
		
		factory = new NetconfConnectorModuleFactory();
		
		createNetconfConnector();
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " stopping...");
		LispDeviceNetconfConnector.bc = null;
	}
	
	private void createNetconfConnector() throws InstanceAlreadyExistsException {
        ConfigTransactionJMXClient transaction = configRegistryClient.createTransaction();
        
//        transaction.lookupConfigBean(moduleName, instanceName);
        
        String name = "testNetconf";
        ObjectName nameCreated = transaction.createModule(factory.getImplementationName(), name);
        NetconfConnectorModuleMXBean mxBean = transaction.newMXBeanProxy(nameCreated, NetconfConnectorModuleMXBean.class);
        
        mxBean.setAddress(new Host(new IpAddress(new Ipv4Address("127.0.0.1"))));
        mxBean.setPassword("netconf");
        mxBean.setPort(new PortNumber(830));
        mxBean.setUsername("netconf");
        
        // binding-broker-osgi-registry
        try {
        	ObjectName bindingBrokerRegistry = createBindingBrokerRegistry(transaction);
        	mxBean.setBindingRegistry(bindingBrokerRegistry);
        } catch (Exception e) {
        	
        }
        
        // Broker instance
        try {
        	ObjectName domRegistry = createDomRegistry(transaction);
            mxBean.setDomRegistry(domRegistry);
        } catch (Exception e) {
        	
        }
        
        // netty-event-executor
        try {
        	ObjectName eventExecutor = createNettyEventExecutor(transaction);
            mxBean.setEventExecutor(eventExecutor);
        } catch (Exception e) {
        	
        }
        
        // threadpool
        try {
	        ObjectName threadpool = createThreadpool(transaction);
	        if (threadpool != null)
	        	mxBean.setProcessingExecutor(threadpool);
        } catch (Exception e) {
        	
        }
        
        
        try {
        	transaction.commit();
        } catch (Exception e) {
        	
        }
	}
	
	private ObjectName createBindingBrokerRegistry(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
		ObjectName domRegistry = transaction.createModule(BindingBrokerImplModuleFactory.NAME, "binding-osgi-broker");
		return domRegistry;
	}
	
	private ObjectName createDomRegistry(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
		ObjectName domRegistry = transaction.createModule(DomBrokerImplModuleFactory.NAME, "dom-broker");
		return domRegistry;
	}
	
	private ObjectName createNettyEventExecutor(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
        ObjectName eventExecutor = transaction.createModule(GlobalEventExecutorModuleFactory.NAME, "global-event-executor");
        return eventExecutor;
	}
	
	private ObjectName createThreadpool(ConfigTransactionJMXClient transaction) throws InstanceAlreadyExistsException {
		FixedThreadPoolModuleFactory tpfactory;
		String name = "threadpool";
		Integer numberOfThreads = 1;
		String prefix = name;
		ObjectName nameCreated = null;
		
		tpfactory = new FixedThreadPoolModuleFactory();
        nameCreated = transaction.createModule(tpfactory.getImplementationName(), name);
        FixedThreadPoolModuleMXBean mxBean = transaction.newMXBeanProxy(nameCreated, FixedThreadPoolModuleMXBean.class);
        mxBean.setMaxThreadCount(numberOfThreads);

        ObjectName threadFactoryON = transaction.createModule(NamingThreadFactoryModuleFactory.NAME, "naming");
        NamingThreadFactoryModuleMXBean namingThreadFactoryModuleMXBean = transaction.newMXBeanProxy(threadFactoryON,
                NamingThreadFactoryModuleMXBean.class);
        namingThreadFactoryModuleMXBean.setNamePrefix(prefix);

        mxBean.setThreadFactory(threadFactoryON);

		return nameCreated;
	}
}
