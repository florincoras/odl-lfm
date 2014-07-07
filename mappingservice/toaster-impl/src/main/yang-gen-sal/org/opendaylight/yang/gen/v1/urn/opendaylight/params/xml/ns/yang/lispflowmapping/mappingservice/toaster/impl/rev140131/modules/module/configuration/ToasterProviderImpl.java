package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.binding.Augmentable;


public interface ToasterProviderImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.ToasterProviderImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:lispflowmapping:mappingservice:toaster-impl","2014-01-31","toaster-provider-impl")
    ;

    DataBroker getDataBroker();

}

