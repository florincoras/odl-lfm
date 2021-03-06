/*
 * Copyright (c) 2014 Contextream, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.lispflowmapping.type.lisp.address;

import org.opendaylight.lispflowmapping.type.AddressFamilyNumberEnum;

public class LispNoAddress extends LispAddress {
    public LispNoAddress() {
        super(AddressFamilyNumberEnum.NO_ADDRESS);
    }

}
