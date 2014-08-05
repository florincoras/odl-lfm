package org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf.impl.MsNCCProviderModule;
import org.opendaylight.lispflowmapping.mappingservice.netconf.impl.LispDeviceNetconfConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.LfmNetconfConnectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsNCCProviderModule extends org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf.impl.AbstractMsNCCProviderModule {
    private static final Logger log = LoggerFactory.getLogger(MsNCCProviderModule.class);

    public MsNCCProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public MsNCCProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf.impl.MsNCCProviderModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
    	
        final LispDeviceNetconfConnector lnconfConnector = new LispDeviceNetconfConnector();

        // Register to md-sal
//        DataBroker dataBrokerService = getDataBrokerDependency();
//        lnconfConnector.setDataProvider(dataBrokerService);

        final BindingAwareBroker.RpcRegistration<LfmNetconfConnectorService> rpcRegistration = getRpcRegistryDependency()
                .addRpcImplementation(LfmNetconfConnectorService.class, lnconfConnector);


        // Wrap as AutoCloseable and close registrations to md-sal at close()
        final class AutoCloseableNCC implements AutoCloseable {

            @Override
            public void close() throws Exception {
                rpcRegistration.close();
                lnconfConnector.close();
                log.info("Lisp netconf connector (instance {}) torn down.", this);
            }
        }

        AutoCloseable ret = new AutoCloseableNCC();
        log.info("LISP NETCONF CONNECTOR provider (instance {}) initialized.", ret);
        return ret;

    }

}
