package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.ServiceRef;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.ChildOf;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.Module;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>msncc-provider-impl</b>
 * <br />(Source path: <i>META-INF/yang/msncc-provider-impl.yang</i>):
 * <pre>
 * container data-broker {
 *     leaf type {
 *         type service-type-ref;
 *     }
 *     leaf name {
 *         type leafref;
 *     }
 *     uses service-ref {
 *         refine (urn:opendaylight:params:xml:ns:yang:controller:config:mappingservice:netconf:impl?revision=2014-07-06)type {
 *             leaf type {
 *                 type service-type-ref;
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>msncc-provider-impl/modules/module/configuration/(urn:opendaylight:params:xml:ns:yang:controller:config:mappingservice:netconf:impl?revision=2014-07-06)msncc-provider-impl/data-broker</i>
 * <p>To create instances of this class use {@link org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.DataBrokerBuilder}.
 * @see org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.DataBrokerBuilder
 */
public interface DataBroker
    extends
    ChildOf<Module>,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.DataBroker>,
    ServiceRef
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:controller:config:mappingservice:netconf:impl","2014-07-06","data-broker");;


}

