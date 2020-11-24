package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.InternetLayerProtocol;

public abstract class InternetLayerAddress<P extends InternetLayerProtocol> extends Address<P, int[]> {}
