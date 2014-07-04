package org.opendaylight.lispflowmapping.mappingservice.toaster;

import org.opendaylight.controller.config.util.ConfigRegistryJMXClient;

//import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
//import org.osgi.framework.ServiceRegistration;


public class Activator implements BundleActivator {
	public static BundleContext bc = null;
//	private HWThread thread = null;
	
	public void start(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " starting...");
		Activator.bc = bc;
		ConfigRegistryJMXClient cr = null;
		if (cr == null);
		
//		Service service = new Service();
//		ServiceRegistration sr = bc.registerService(IService.class.getName(), service, new Hashtable());
//		System.out.println("Service registered: service");
		
//		this.thread = new HWThread();
//		this.thread.start();
	}
	
	public void stop(BundleContext bc) throws Exception {
		System.out.println(bc.getBundle().getHeaders().get(Constants.BUNDLE_NAME) + " stopping...");
//		this.thread.stopThead();
//		this.thread.join();
		Activator.bc = null;
	}
}
