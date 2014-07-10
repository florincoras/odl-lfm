package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706;
import java.util.concurrent.Future;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.RemoveConnectorInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.mappingservice.netconf.rev140706.BuildConnectorInput;


/**
**/
public interface LfmNetconfConnectorService
    extends
    RpcService
{




    /**
      Build netconf connector
    **/
    Future<RpcResult<java.lang.Void>> buildConnector(BuildConnectorInput input);
    
    /**
      Removes a given netconf connector
    **/
    Future<RpcResult<java.lang.Void>> removeConnector(RemoveConnectorInput input);

}

