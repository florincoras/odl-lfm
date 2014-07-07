package org.opendaylight.lispflowmapping.mappingservice.toaster_impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;



//import org.opendaylight.lispflowmapping.mappingservice.yang.toaster_provider.impl.ToasterProviderRuntimeMXBean;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.data.DataBrokerService;
import org.opendaylight.controller.sal.binding.api.data.DataModificationTransaction;
import org.opendaylight.controller.sal.common.util.RpcErrors;
import org.opendaylight.controller.sal.common.util.Rpcs;
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.DisplayString;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.MakeToastInput;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.RestockToasterInput;
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.Toaster;
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.Toaster.ToasterStatus;
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.ToasterBuilder;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.ToasterOutOfBreadBuilder;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.ToasterRestocked;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.ToasterRestockedBuilder;
//import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.toaster.rev091120.ToasterService;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorSeverity;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Futures;

public class OpendaylightToaster implements AutoCloseable{
	  
	   //making this public because this unique ID is required later on in other classes.
	   public static final InstanceIdentifier<Toaster>  TOASTER_IID = InstanceIdentifier.builder(Toaster.class).build();
	   private static final Logger LOG = LoggerFactory.getLogger(OpendaylightToaster.class);
	      
	   private static final DisplayString TOASTER_MANUFACTURER = new DisplayString("Opendaylight");
	   private static final DisplayString TOASTER_MODEL_NUMBER = new DisplayString("Model 1 - Binding Aware");
	    
	   private DataBrokerService dataProvider;
	  
	   public OpendaylightToaster() {
	   }
	    
	   private Toaster buildToaster() {
	       //note - we are simulating a device whose manufacture and model are fixed (embedded) into the hardware.
	       //This is why the manufacture and model number are hardcoded.
	       ToasterBuilder tb = new ToasterBuilder();
	       tb.setToasterManufacturer(TOASTER_MANUFACTURER) 
	          .setToasterModelNumber(TOASTER_MODEL_NUMBER) 
	          .setToasterStatus( ToasterStatus.Up );
	       return tb.build();
	   }
	   
	   public void setDataProvider(DataBrokerService salDataProvider) {
	        this. dataProvider = salDataProvider;
	        updateStatus();
	   }
	 
	   /**
	    * Implemented from the AutoCloseable interface.
	    */
	   @Override
	   public void close() throws ExecutionException, InterruptedException {
	       if (dataProvider != null) {
	           final DataModificationTransaction t = dataProvider.beginTransaction();
	           t.removeOperationalData(TOASTER_IID);
	           t.commit().get();
	       }
	   }
	   
	   private void updateStatus() {
	       if (dataProvider != null) {
	           final DataModificationTransaction t = dataProvider.beginTransaction();
	           t.removeOperationalData(TOASTER_IID);
	           t.putOperationalData(TOASTER_IID, buildToaster()); 
	           try {
	               t.commit().get();
	           } catch (InterruptedException | ExecutionException e) {
	               LOG.warn("Failed to update toaster status, operational otherwise", e);
	           }
	       } else {
	           LOG.trace("No data provider configured, not updating status");
	       }
	   }  
	}