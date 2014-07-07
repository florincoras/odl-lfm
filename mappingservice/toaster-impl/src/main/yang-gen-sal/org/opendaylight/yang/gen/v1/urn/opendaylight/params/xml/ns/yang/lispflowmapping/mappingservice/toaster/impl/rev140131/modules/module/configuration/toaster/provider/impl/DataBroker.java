package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.Module;


public interface DataBroker
    extends
    ChildOf<Module>,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.mappingservice.toaster.impl.rev140131.modules.module.configuration.toaster.provider.impl.DataBroker>,
    ServiceRef
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:lispflowmapping:mappingservice:toaster-impl","2014-01-31","data-broker")
    ;


}

