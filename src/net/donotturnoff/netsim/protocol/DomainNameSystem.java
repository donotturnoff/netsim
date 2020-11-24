package net.donotturnoff.netsim.protocol;

import net.donotturnoff.netsim.address.TransmissionControlProtocolPort;

import java.util.Set;

public class DomainNameSystem extends ApplicationLayerProtocol {

    public DomainNameSystem() {
        super(Set.of(new TransmissionControlProtocolPort(53)));
    }
}
