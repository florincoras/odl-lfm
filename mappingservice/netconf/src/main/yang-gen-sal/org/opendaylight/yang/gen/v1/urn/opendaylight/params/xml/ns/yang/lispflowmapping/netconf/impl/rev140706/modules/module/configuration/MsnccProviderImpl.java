package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.impl.rev140706.modules.module.configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.impl.rev140706.modules.module.configuration.msncc.provider.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * <p>This class represents the following YANG schema fragment defined in module <b>msncc-provider-impl</b>
 * <br />(Source path: <i>META-INF/yang/msncc-provider-impl.yang</i>):
 * <pre>
 * case msncc-provider-impl {
 *     container rpc-registry {
 *         leaf type {
 *             type service-type-ref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:opendaylight:params:xml:ns:yang:lispflowmapping:netconf:impl?revision=2014-07-06)type {
 *                 leaf type {
 *                     type service-type-ref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * </pre>
 * The schema path to identify an instance is
 * <i>msncc-provider-impl/modules/module/configuration/(urn:opendaylight:params:xml:ns:yang:lispflowmapping:netconf:impl?revision=2014-07-06)msncc-provider-impl</i>
 */
public interface MsnccProviderImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.lispflowmapping.netconf.impl.rev140706.modules.module.configuration.MsnccProviderImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:lispflowmapping:netconf:impl","2014-07-06","msncc-provider-impl");;

    RpcRegistry getRpcRegistry();

}

