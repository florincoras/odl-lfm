/*
 * Copyright (c) 2014 Contextream, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.lispflowmapping.implementation.serializer.address;

import java.nio.ByteBuffer;

import org.opendaylight.lispflowmapping.implementation.util.ByteUtil;
import org.opendaylight.lispflowmapping.implementation.util.LispAFIConvertor;
import org.opendaylight.lispflowmapping.implementation.util.NumberUtil;
import org.opendaylight.lispflowmapping.type.AddressFamilyNumberEnum;
import org.opendaylight.lispflowmapping.type.LispCanonicalAddressFormatEnum;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.LcafApplicationDataAddress;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.LispAFIAddress;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.lcafapplicationdataaddress.AddressBuilder;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.lispaddress.lispaddresscontainer.address.LcafApplicationDataBuilder;
import org.opendaylight.yang.gen.v1.lispflowmapping.rev131031.lispsimpleaddress.PrimitiveAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;

public class LispApplicationDataLCAFAddressSerializer extends LispLCAFAddressSerializer {

    private static final LispApplicationDataLCAFAddressSerializer INSTANCE = new LispApplicationDataLCAFAddressSerializer();

    // Private constructor prevents instantiation from other classes
    private LispApplicationDataLCAFAddressSerializer() {
    }

    public static LispApplicationDataLCAFAddressSerializer getInstance() {
        return INSTANCE;
    }

    @Override
    protected short getLcafLength(LispAFIAddress lispAddress) {
        return (short) (Length.ALL_FIELDS + LispAddressSerializer.getInstance().getAddressSize(
                (LispAFIAddress) ((LcafApplicationDataAddress) lispAddress).getAddress().getPrimitiveAddress()));
    }

    @Override
    protected void serializeData(ByteBuffer buffer, LispAFIAddress lispAddress) {
        LcafApplicationDataAddress applicationDataAddress = ((LcafApplicationDataAddress) lispAddress);
        buffer.put(ByteUtil.partialIntToByteArray(NumberUtil.asInt(applicationDataAddress.getIpTos()), Length.TOC));
        buffer.put((byte) NumberUtil.asShort(applicationDataAddress.getProtocol()));
        if (applicationDataAddress.getLocalPort() != null) {
            buffer.putShort(NumberUtil.asShort(applicationDataAddress.getLocalPort().getValue().shortValue()));
        } else {
            buffer.putShort((short) 0);
        }
        if (applicationDataAddress.getRemotePort() != null) {
            buffer.putShort(NumberUtil.asShort(applicationDataAddress.getRemotePort().getValue().shortValue()));
        } else {
            buffer.putShort((short) 0);
        }
        LispAddressSerializer.getInstance().serialize(buffer,
                (LispAFIAddress) ((LcafApplicationDataAddress) lispAddress).getAddress().getPrimitiveAddress());
    }

    @Override
    protected LcafApplicationDataAddress deserializeData(ByteBuffer buffer, byte res2, short length) {

        LcafApplicationDataBuilder builder = new LcafApplicationDataBuilder();
        byte[] rawIPTos = new byte[3];
        buffer.get(rawIPTos);
        builder.setIpTos(ByteUtil.getPartialInt(rawIPTos));
        builder.setProtocol((short) ByteUtil.getUnsignedByte(buffer));
        builder.setLocalPort(new PortNumber(ByteUtil.asUnsignedShort(buffer.getShort())));
        builder.setRemotePort(new PortNumber(ByteUtil.asUnsignedShort(buffer.getShort())));
        LispAFIAddress address = LispAddressSerializer.getInstance().deserialize(buffer);
        builder.setAfi(AddressFamilyNumberEnum.LCAF.getIanaCode()).setLcafType((short) LispCanonicalAddressFormatEnum.APPLICATION_DATA.getLispCode())
                .setAddress(new AddressBuilder().setPrimitiveAddress((PrimitiveAddress) LispAFIConvertor.toPrimitive(address)).build());

        return builder.build();
    }

    private interface Length {
        int LOCAL_PORT = 2;
        int REMOTE_PORT = 2;
        int TOC = 3;
        int PROTOCOL = 1;
        int ALL_FIELDS = LOCAL_PORT + REMOTE_PORT + TOC + PROTOCOL;
    }

}
