package org.opendaylight.lispflowmapping.mappingservice.netconf.impl;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.opendaylight.controller.config.api.ConflictingVersionException;
import org.opendaylight.controller.config.api.ValidationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LispNetconfConnector {
    private ConfigRegistryJMXClient configRegistryClient;
    
	private static final Logger LOG = LoggerFactory.getLogger(LispNetconfConnector.class);

    private MBeanServer platformMBeanServer;
	
	public LispNetconfConnector() {
		
		// Obtain the platform's MBeanServer (should've been previously created)
		// and create a ConfigRegistry JMX client via which modules can be created
		// and destroyed
		platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
		configRegistryClient = new ConfigRegistryJMXClient(platformMBeanServer);
		
	}
	
	// Build a sal-netconf-connector to specified device. Module instantiation and dependency resolution
	// are done via a JMX ConfigTransactionClient 
	public void createNetconfConnector(String instanceName, Host host, Integer port, String username, String password) throws InstanceAlreadyExistsException {
		
        ConfigTransactionJMXClient transaction = configRegistryClient.createTransaction();
        
        if (transaction == null) {
        	LOG.error("Could not create transaction with ConfigRegistry! Cannot build NETCONF connector!");
        	return;
        }
        
        // create sal-netconf-connector module and via an mxBean configure all 
        // yang defined parameters
        ObjectName connName = transaction.createModule(NetconfConnectorModuleFactory.NAME, instanceName);
        NetconfConnectorModuleMXBean mxBean = transaction.newMXBeanProxy(connName, NetconfConnectorModuleMXBean.class);
        
        mxBean.setAddress(host);
        mxBean.setPassword(password);
        mxBean.setPort(new PortNumber(port));
        mxBean.setUsername(username);
        mxBean.setTcpOnly(false);

        if (solveDependencies(transaction, mxBean) != true) {
        	LOG.error("Failed to solve dependencies! Aborting!");
        	return;
        }
        
        try {
        	transaction.commit();	                
        } catch (Exception e) {
            LOG.error("Transaction failed ", e.getStackTrace().toString());
        }
	}
	
	public void removeNetconfConnector(String instanceName) throws InstanceNotFoundException {
        ConfigTransactionJMXClient transaction = configRegistryClient.createTransaction();
        transaction.destroyModule(NetconfConnectorModuleFactory.NAME, instanceName);
        
        try {
        	transaction.commit();
        } catch (ValidationException e1) {
            LOG.error("Failed to validate NetconfConnectorModule transaction:", e1.getStackTrace().toString() );
        } catch (ConflictingVersionException e) {
        	LOG.error("NetconfConnectorModule conflicting version:", e.getStackTrace().toString() );
        } 
	}
	
	// Lookup sal-netconf-connector dependencies using a ConfigTransactionJMXClient and configure them
	// for the module we are about to instantiate. 
	private boolean solveDependencies(ConfigTransactionJMXClient transaction, NetconfConnectorModuleMXBean mxBean) {
		
    	ObjectName bindingBrokerRegistry = findConfigBean(BindingBrokerImplModuleFactory.NAME, transaction);
    	if (bindingBrokerRegistry != null ) {
    		mxBean.setBindingRegistry(bindingBrokerRegistry);
    	} else {
    		LOG.debug("No BindingBroker instance found");
    		return false;
    	}

        ObjectName domRegistry = findConfigBean(DomBrokerImplModuleFactory.NAME, transaction);
        if (domRegistry != null) {
        	mxBean.setDomRegistry(domRegistry);
        } else {
        	LOG.debug("No DomRegistryBroker instance found");
        	return false;
        }
        
        ObjectName eventExecutor = findConfigBean(GlobalEventExecutorModuleFactory.NAME, transaction);
        if (eventExecutor != null) {
            mxBean.setEventExecutor(eventExecutor);
        } else {
        	LOG.debug("No EventExecutor instance found");
        	return false;
        }
        
        ObjectName threadpool = findConfigBean(FlexibleThreadPoolModuleFactory.NAME, transaction);
        if (threadpool != null) {
            mxBean.setProcessingExecutor(threadpool);
        } else {
        	LOG.debug("No ThreadPool instance found");
        	return false;
        }
        
        ObjectName clientDispatcher = findConfigBean(NetconfClientDispatcherModuleFactory.NAME, transaction);
        if (clientDispatcher != null) {
            mxBean.setClientDispatcher(clientDispatcher);
        } else {
        	LOG.debug("No ClientDispatcher instance found");
        	return false;
        }
        
        return true;
        
	}
	
	private ObjectName findConfigBean(String name, ConfigTransactionJMXClient transaction) {
    	Set<ObjectName> set = transaction.lookupConfigBeans(name);
    	if (set.size() > 0) {
    		return set.iterator().next();
    	} else {
    		return null;
    	}
	}
	
	
}
