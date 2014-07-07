package org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf_impl;
public class MsNCCProviderModule extends org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf_impl.AbstractMsNCCProviderModule {
    public MsNCCProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public MsNCCProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.lispflowmapping.config.yang.mappingservice.netconf_impl.MsNCCProviderModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        // TODO:implement
        throw new java.lang.UnsupportedOperationException();
    }

}
