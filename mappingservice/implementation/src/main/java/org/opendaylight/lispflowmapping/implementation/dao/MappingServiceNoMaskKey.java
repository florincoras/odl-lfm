/*
 * Copyright (c) 2014 Contextream, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.lispflowmapping.implementation.dao;

import org.opendaylight.lispflowmapping.implementation.util.MaskUtil;
import org.opendaylight.lispflowmapping.interfaces.dao.IMappingServiceKey;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.lispaddress.LispAddressContainer;

public class MappingServiceNoMaskKey implements IMappingServiceKey {

    private LispAddressContainer EID;

    public MappingServiceNoMaskKey(LispAddressContainer lispAddressContainer) {
        this.EID = lispAddressContainer;
    }

    public LispAddressContainer getEID() {
        return EID;
    }

    public int getMask() {
        if (MaskUtil.isMaskable(EID.getAddress())) {
            return MaskUtil.getMaxMask(EID.getAddress());
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((EID == null) ? 0 : EID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MappingServiceNoMaskKey other = (MappingServiceNoMaskKey) obj;
        if (EID == null) {
            if (other.EID != null)
                return false;
        } else if (!EID.equals(other.EID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return EID.toString();
    }

}
