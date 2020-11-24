package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.TransmissionControlProtocol;

public class TransmissionControlProtocolPort extends TransportLayerAddress<TransmissionControlProtocol> {

    public TransmissionControlProtocolPort(int location) throws IllegalArgumentException {
        setLocation(location);
    }

    public TransmissionControlProtocolPort(String s) throws IllegalArgumentException {
        setLocation(locationFromString(s));
    }

    @Override
    public boolean isValid(Integer location) {
        return location >= 0 && location <= 65535;
    }

    @Override
    protected Integer locationFromString(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public String toString() {
        return Integer.toString(location);
    }
}
