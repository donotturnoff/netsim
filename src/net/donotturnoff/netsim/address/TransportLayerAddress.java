package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.TransportLayerProtocol;

public abstract class TransportLayerAddress<P extends TransportLayerProtocol> extends Address<P, Integer> {}
