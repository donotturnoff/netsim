package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.protocol.InternetLayerProtocol;

public abstract class Packet extends ProtocolDataUnit<InternetLayerProtocol, int[], Segment> {
}
