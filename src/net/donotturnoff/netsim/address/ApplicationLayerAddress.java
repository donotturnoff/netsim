package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.ApplicationLayerProtocol;

public abstract class ApplicationLayerAddress<P extends ApplicationLayerProtocol> extends Address<P, String> {}
