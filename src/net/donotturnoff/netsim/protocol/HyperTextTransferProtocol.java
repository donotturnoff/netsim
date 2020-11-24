package net.donotturnoff.netsim.protocol;

import net.donotturnoff.netsim.address.TransmissionControlProtocolPort;

import java.util.Set;

public class HyperTextTransferProtocol extends ApplicationLayerProtocol {
    public HyperTextTransferProtocol() {
        super(Set.of(new TransmissionControlProtocolPort(80)));
    }
}
