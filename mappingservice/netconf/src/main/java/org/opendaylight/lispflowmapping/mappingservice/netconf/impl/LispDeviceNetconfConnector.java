package org.opendaylight.lispflowmapping.mappingservice.netconf.impl;


import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.management.InstanceAlreadyExistsException;

import org.opendaylight.lispflowmapping.mappingservice.netconf.impl.LispNetconfConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.LfmNetconfConnectorService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.NcConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.NcConnectorBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorSeverity;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.common.util.RpcErrors;
import org.opendaylight.controller.sal.common.util.Rpcs;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.common.util.concurrent.Futures;


public class LispDeviceNetconfConnector implements AutoCloseable, LfmNetconfConnectorService{
	  
	   //making this public because this unique ID is required later on in other classes.
	   public static final InstanceIdentifier<NcConnector>  NCC_IID = InstanceIdentifier.builder(NcConnector.class).build();
	   private static final Logger LOG = LoggerFactory.getLogger(LispDeviceNetconfConnector.class);
	      
	   private DataBroker dataProvider;
	   private final ExecutorService executor;
	   private LispNetconfConnector nconfConnector;
	   
	    // We are using multiple threads here. Therefore we need to be careful about concurrency.
	    // In this case we use the taskLock to provide synchronization for the current task.
	    private volatile Future<RpcResult<Void>> currentTask;
	    private final Object taskLock = new Object();
 
	  
	   public LispDeviceNetconfConnector() {
		   LOG.info( "LISP DEVICE NETCONF CONNECTOR CONSTRUCTED" );
		   executor = Executors.newFixedThreadPool(1);
		   nconfConnector = new LispNetconfConnector();
	   }
	    
	   private NcConnector buildNcConnector() {
		   NcConnectorBuilder nccb = new NcConnectorBuilder();
	       return nccb.build();
	   }
	   
	   public void setDataProvider(final DataBroker salDataProvider) {
	        this.dataProvider = salDataProvider;
	        updateStatus();
	   }
	 
	   /**
	    * Implemented from the AutoCloseable interface.
	    */
	   @Override
	   public void close() throws ExecutionException, InterruptedException {
		   executor.shutdown();
	       if (dataProvider != null) {
	            WriteTransaction t = dataProvider.newWriteOnlyTransaction();
	            t.delete(LogicalDatastoreType.OPERATIONAL, NCC_IID);
	            t.commit().get(); // FIXME: This call should not be blocking.
	       }
	   }
	   
	   private void updateStatus() {
		   LOG.info("updating status of lisp netconf connector");
	        if (dataProvider != null) {
	            WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
	            tx.put(LogicalDatastoreType.OPERATIONAL, NCC_IID, buildNcConnector());

	            try {
	                tx.commit().get();
	            } catch (InterruptedException | ExecutionException e) {
	                LOG.warn("Failed to update connector status, operational otherwise", e);
	            }
	        } else {
	            LOG.trace("No data provider configured, not updating status");
	        }
	   }  
	   
	    /**
	     * RestConf RPC call implemented from the LfmNetconfConnectorService interface.
	     */
	    @Override
	    public Future<RpcResult<Void>> buildConnector(final BuildConnectorInput input) {
	        LOG.info("buildConnector: " + input);
	        
	        synchronized (taskLock) {
		        if (currentTask != null && currentTask.isDone()) {
		        	currentTask = null;
		        }
		        
	            if (currentTask != null) {
	                // return an error since we are already toasting some toast.
	                LOG.info( "Connector stuck" );

	                RpcResult<Void> result = Rpcs.<Void> getRpcResult(false, null, Arrays.asList(
	                        RpcErrors.getRpcError( "", "in-use", null, ErrorSeverity.WARNING,
	                                               "Connection already set", ErrorType.APPLICATION, null ) ) );
	                return Futures.immediateFuture(result);
	            } else {
	                // Notice that we are moving the actual call to another thread,
	                // allowing this thread to return immediately.
	                // The MD-SAL design encourages asynchronus programming. If the
	                // caller needs to block until the call is
	                // complete then they can leverage the blocking methods on the
	                // Future interface.
	                currentTask = executor.submit(new MakeConnector(input));
	            }
	        }
	        
	        updateStatus();
	        return currentTask;
	    }
	    
	    
	    private class MakeConnector implements Callable<RpcResult<Void>> {

	        final BuildConnectorInput req;

	        public MakeConnector(final BuildConnectorInput conn) {
	            req = conn;
	        }

	        @Override
	        public RpcResult<Void> call() {
	        	LOG.debug("Called RPC \n");
	            try {
	            	nconfConnector.createNetconfConnector(req.getInstance(), req.getAddress(), req.getPort().getValue(), req.getUsername(), req.getPassword());
	            } catch( InstanceAlreadyExistsException e ) {
	                LOG.info( "Connection already exists!" );
	            }
	            
	            synchronized (taskLock) {
	                currentTask = null;
	            }

	            updateStatus();

	            LOG.debug("Connector built");

	            return Rpcs.<Void> getRpcResult(true, null, Collections.<RpcError> emptySet());
	        }
	    }
	}