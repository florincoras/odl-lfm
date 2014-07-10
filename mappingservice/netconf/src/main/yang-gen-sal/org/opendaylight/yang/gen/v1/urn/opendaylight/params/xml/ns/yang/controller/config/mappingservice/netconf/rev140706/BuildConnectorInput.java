package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Host;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.Augmentable;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;


public interface BuildConnectorInput
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput>
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.create("urn:opendaylight:params:xml:ns:yang:controller:config:mappingservice:netconf","2014-07-06","input")
    ;

    /**
      Device address
    **/
    Host getAddress();
    
    /**
      instance name
    **/
    java.lang.String getInstance();
    
    /**
      Password for netconf connection
    **/
    java.lang.String getPassword();
    
    /**
      Device port
    **/
    PortNumber getPort();
    
    /**
      Username for netconf connection
    **/
    java.lang.String getUsername();

}

