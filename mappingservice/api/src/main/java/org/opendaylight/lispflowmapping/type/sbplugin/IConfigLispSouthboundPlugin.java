/*
 * Copyright (c) 2014 Contextream, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.lispflowmapping.type.sbplugin;

/**
 * An interface for configuring the lisp plugin address.
 */
public interface IConfigLispSouthboundPlugin {
    public void setLispAddress(String address);

    public void shouldListenOnXtrPort(boolean shouldListenOnXtrPort);
    public void setXtrPort(int port);
}
