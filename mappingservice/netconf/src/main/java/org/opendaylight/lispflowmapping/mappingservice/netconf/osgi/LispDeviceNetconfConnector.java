package org.opendaylight.lispflowmapping.mappingservice.netconf.osgi;

import java.lang.management.ManagementFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.opendaylight.controller.config.api.ConfigRegistry;
import org.opendaylight.controller.config.util.ConfigRegistryJMXClient;



public class LispDeviceNetconfConnector implements BundleActivator {
	public static BundleContext bc = null;
	ConfigRegistryTrackerCustomizer customizer;
	private ServiceTracker tracker;
	private LispNetconfConnector connector;
	
	
	@Override
	public void start(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " starting...");
		LispDeviceNetconfConnector.bc = bc;
		
//		customizer = new ConfigRegistryTrackerCustomizer(bc);
//		tracker = new ServiceTracker(bc, ConfigRegistry.class.getName(), customizer);
//		tracker.open();
		
		if (connector == null)
			connector = new LispNetconfConnector();
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " stopping...");
		LispDeviceNetconfConnector.bc = null;
	}
	

	public class ConfigRegistryTrackerCustomizer implements ServiceTrackerCustomizer {
		BundleContext bc;
		LispNetconfConnector connector;
		
		public ConfigRegistryTrackerCustomizer(BundleContext bc) {
			this.bc = bc;
		}
		
		@Override
		public Object addingService(ServiceReference reference) {
			ConfigRegistry registry = (ConfigRegistry) bc.getService(reference);
			
			System.out.println("OMG ADDING CONFIGREGISTRY SERVICE!");

			connector = new LispNetconfConnector(new ConfigRegistryJMXClient(ManagementFactory.getPlatformMBeanServer()));
			return registry;
		}
		

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			// TODO Auto-generated method stub
			
		}
	}
}
