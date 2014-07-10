package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.binding.Augmentable;


public interface MsnccProviderImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.MsnccProviderImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:controller:config:mappingservice:netconf:impl","2014-07-06","msncc-provider-impl");;

    DataBroker getDataBroker();
    
    RpcRegistry getRpcRegistry();

}

