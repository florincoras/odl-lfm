package org.opendaylight.lispflowmapping.netconf.impl;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;

import org.opendaylight.lispflowmapping.netconf.impl.LispNetconfConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.rev140706.BuildConnectorInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.rev140706.LfmNetconfConnectorService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.rev140706.RemoveConnectorInput;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.controller.config.api.ConflictingVersionException;
import org.opendaylight.controller.config.api.ValidationException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;


public class LispDeviceNetconfConnector implements AutoCloseable, LfmNetconfConnectorService {

	   private static final Logger LOG = LoggerFactory.getLogger(LispDeviceNetconfConnector.class);

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

	   /**
	    * Implemented from the AutoCloseable interface.
	    */
	   @Override
	   public void close() throws ExecutionException, InterruptedException {
		   executor.shutdown();
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
	    	SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

	        LOG.trace("Received RPC to buildConnector: " + input);

	        synchronized (taskLock) {
		        if (currentTask != null && currentTask.isDone()) {
		        	currentTask = null;
		        }

	            if (currentTask != null) {
	                // Return an error since we are already doing some work.
	                LOG.debug( "LispDeviceNetconfConnector busy" );
	                return Futures.immediateFailedCheckedFuture(
	                		new TransactionCommitFailedException("", makeNCCInUseError() ) );
	            } else {
	                // Move the actual call to another thread,
	                // allowing this thread to return immediately.
	                currentTask = executor.submit(new MakeConnector(input, futureResult));
	            }
	        }

	        return futureResult;
	    }

	    @Override
	    public Future<RpcResult<Void>> removeConnector(final RemoveConnectorInput input) {
	    	SettableFuture<RpcResult<Void>> futureResult = SettableFuture.create();

	    	synchronized (taskLock) {
		        if (currentTask != null && currentTask.isDone()) {
		        	currentTask = null;
		        }

		        if (currentTask != null) {
		        	LOG.warn("LispDeviceNetconfConnector busy");
	                return Futures.immediateFailedCheckedFuture(
	                		new TransactionCommitFailedException("", makeNCCInUseError() ) );
		        } else {
	                // Move the actual call to another thread,
	                // allowing this thread to return immediately.
		        	currentTask = executor.submit(new RemoveConnector(input, futureResult) );
		        }
	    	}

	    	return futureResult;
	    }


	    private class MakeConnector implements Callable<RpcResult<Void>> {

	        final BuildConnectorInput req;
	        final SettableFuture<RpcResult<Void>> futureResult;

	        public MakeConnector(final BuildConnectorInput conn, SettableFuture<RpcResult<Void>> futureResult) {
	            this.req = conn;
	            this.futureResult = futureResult;
	        }

	        @Override
	        public RpcResult<Void> call() {

	        	try {
	            	nconfConnector.createNetconfConnector(req.getInstance(), req.getAddress(),
	            			req.getPort().getValue(), req.getUsername(), req.getPassword());
		            LOG.info("LispNetconfConnector {} built", req.getInstance());
		            futureResult.set(RpcResultBuilder.<Void>success().build());
	            } catch( InstanceAlreadyExistsException e ) {
	                LOG.error("LispNetconfConnector {} already exists!", req.getInstance());
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "exists", "LispNetconfConnector exists")
	                		.build());
	            } catch (ConflictingVersionException ex) {
	            	LOG.error("LispNetconfConnector {} version exception", req.getInstance());
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "exists", "LispNetconfConnector version exception")
	                		.build());
	            } catch ( ValidationException ex) {
	            	LOG.error("LispNetconfConnector {} validation exception", req.getInstance());
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "exists", "LispNetconfConnector validation exception")
	                		.build());
	            }

	            synchronized (taskLock) {
	                currentTask = null;
	            }

		        return RpcResultBuilder.<Void> success().build();

	        }

	    }

	    private class RemoveConnector implements Callable<RpcResult<Void>> {
	        final RemoveConnectorInput req;
	        final SettableFuture<RpcResult<Void>> futureResult;


	        public RemoveConnector(final RemoveConnectorInput conn, SettableFuture<RpcResult<Void>> futureResult) {
	            this.req = conn;
	            this.futureResult = futureResult;
	        }

	        @Override
	        public RpcResult<Void> call() {
	            try {
	            	nconfConnector.removeNetconfConnector(req.getInstance());
	            	LOG.info("LispNetconfConnector {} removed!", req.getInstance());
	            	futureResult.set(RpcResultBuilder.<Void> success().build());
	            } catch( InstanceNotFoundException e ) {
	                LOG.info("LispNetconfConnector {} doesn't exists!", req.getInstance());
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "no-exist", "LispNetconfConnector doesn't exist")
	                		.build());
	            } catch( ValidationException e ) {
	                LOG.info("LispNetconfConnector {}: Could not validate remove transactions!", req.getInstance());
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "fail", "LispNetconfConnector doesn't exist")
	                		.build());
	            } catch (ConflictingVersionException e) {
	                LOG.error("LispNetconfConnector {}: Cannot remove due to conflicting version", req.getInstance() );
	                futureResult.set(RpcResultBuilder.<Void> failed()
	                		.withError(ErrorType.APPLICATION, "fail", "Conflicting version exception")
	                		.build());
	            } catch (Exception e) {
	            	LOG.error("LispNetconfConnector {} exception while removing: {}", req.getInstance(), e.getClass());
	            	futureResult.set(RpcResultBuilder.<Void> failed()
	            			.withError(ErrorType.APPLICATION, "fail", "Cannot remove: " + req.getInstance())
	            			.build());
	            }

	            synchronized (taskLock) {
	                currentTask = null;
	            }

	            return RpcResultBuilder.<Void> success().build();
	        }
	    }
	}