package org.opendaylight.lispflowmapping.mappingservice.netconf.impl;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.opendaylight.lispflowmapping.mappingservice.netconf.impl.LispNetconfConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.LfmNetconfConnectorService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.NcConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.NcConnectorBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.RemoveConnectorInput;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;


public class LispDeviceNetconfConnector implements AutoCloseable, LfmNetconfConnectorService {
		
	   private static final Logger LOG = LoggerFactory.getLogger(LispDeviceNetconfConnector.class);
	   public static final InstanceIdentifier<NcConnector> NCC_IID = InstanceIdentifier.builder(NcConnector.class).build();

	      
	   private DataBroker dataProvider;
	   private final ExecutorService executor;
	   private LispNetconfConnector nconfConnector;
	   
	    // We are using multiple threads here. Therefore we need to be careful about concurrency.
	    // In this case we use the taskLock to provide synchronization for the current task.
	    private volatile Future<RpcResult<Void>> currentTask;
	    private final Object taskLock = new Object();
	  
	   public LispDeviceNetconfConnector() {
		   executor = Executors.newFixedThreadPool(1);
		   nconfConnector = new LispNetconfConnector();
		   LOG.info( "LispDeviceNetconfConnector constructed" );
	   }
	    
//	   public void setDataProvider(final DataBroker salDataProvider) {
//	        this.dataProvider = salDataProvider;
//	        
//	        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
//            tx.put( LogicalDatastoreType.OPERATIONAL, NCC_IID );
//            tx.submit();
//	   }
	 
	   /**
	    * Implemented from the AutoCloseable interface.
	    */
	   @Override
	   public void close() throws ExecutionException, InterruptedException {
		   executor.shutdown();
		   
//	       if (dataProvider != null) {
//				   
//				WriteTransaction t = dataProvider.newWriteOnlyTransaction();
//				t.delete(LogicalDatastoreType.OPERATIONAL, NCC_IID);
//	            
//	            // Non-blocking delete from operational datastore
//				Futures.addCallback(t.submit(), new FutureCallback<Void>() {
//					@Override
//				    public void onSuccess( final Void result ) {
//				        LOG.debug( "Delete LispDeviceNetconfConnector commit result: " + result );
//				    }
//				
//				    @Override
//				    public void onFailure( final Throwable t ) {
//				        LOG.error( "Delete of LispDeviceNetconfConnector failed", t );
//				    }
//				});
//	       }
	   }
	   
	    private RpcError makeNCCInUseError() {
	        return RpcResultBuilder.newWarning( ErrorType.APPLICATION, "in-use",
	                "LispNetconfConnector busy", null, null, null );
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
	                // return an error since we are already doing some work.
	                LOG.debug( "LispDeviceNetconfConnector busy" );
                
	                return Futures.immediateFailedCheckedFuture(
	                		new TransactionCommitFailedException("", makeNCCInUseError() ) );
	            } else {
	                // We are moving the actual call to another thread,
	                // allowing this thread to return immediately.
	                currentTask = executor.submit(new MakeConnector(input));
	            }
	        }
	        
	        return currentTask;
	    }
	    
	    @Override
	    public Future<RpcResult<Void>> removeConnector(final RemoveConnectorInput input) {
	    	synchronized (taskLock) {
		        if (currentTask != null && currentTask.isDone()) {
		        	currentTask = null;
		        }
		        
		        if (currentTask != null) {
		        	LOG.warn("LispNetconfConnector busy");
	                return Futures.immediateFailedCheckedFuture(
	                		new TransactionCommitFailedException("", makeNCCInUseError() ) );
		        } else {
		        	currentTask = executor.submit(new RemoveConnector(input));
		        }
		        
	    	}
	    	
	    	return currentTask;
	    }
	    
	    
	    private class MakeConnector implements Callable<RpcResult<Void>> {

	        final BuildConnectorInput req;

	        public MakeConnector(final BuildConnectorInput conn) {
	            req = conn;
	        }

	        @Override
	        public RpcResult<Void> call() {

	        	try {
	            	nconfConnector.createNetconfConnector(req.getInstance(), req.getAddress(), 
	            			req.getPort().getValue(), req.getUsername(), req.getPassword());
	            } catch( InstanceAlreadyExistsException e ) {
	                LOG.info( "NETCONF connector {} already exists!", req.getInstance() );
	            }
	            
	            synchronized (taskLock) {
	                currentTask = null;
	            }

	            LOG.debug("Connector {} built", req.getInstance());

	            return RpcResultBuilder.<Void> success().build();
	        }
	    }
	    
	    private class RemoveConnector implements Callable<RpcResult<Void>> {
	        final RemoveConnectorInput req;

	        public RemoveConnector(final RemoveConnectorInput conn) {
	            req = conn;
	        }
	        
	        @Override
	        public RpcResult<Void> call() {
	            try {
	            	nconfConnector.removeNetconfConnector(req.getInstance());
	            } catch( InstanceNotFoundException e ) {
	                LOG.info( "NETCONF connector {} doesn't exists!", req.getInstance() );
	            }
	            
	            synchronized (taskLock) {
	                currentTask = null;
	            }

	            LOG.debug("Connector {} removed", req.getInstance());

	            return RpcResultBuilder.<Void> success().build();
	        }
	    }
	}