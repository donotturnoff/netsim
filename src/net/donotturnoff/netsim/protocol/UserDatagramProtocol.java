package net.donotturnoff.netsim.protocol;

public class UserDatagramProtocol extends TransportLayerProtocol {
    public UserDatagramProtocol() {
        super(0x11);
    }
}
