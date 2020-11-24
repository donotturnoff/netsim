package net.donotturnoff.netsim.protocol;

import net.donotturnoff.netsim.address.TransportLayerAddress;

import java.util.Set;

public abstract class ApplicationLayerProtocol implements Protocol {

    protected final Set<TransportLayerAddress<?>> defaultPorts;

    protected ApplicationLayerProtocol(Set<TransportLayerAddress<?>> defaultPorts) {
        this.defaultPorts = defaultPorts;
    }

    public Set<TransportLayerAddress<?>> getDefaultPorts() {
        return defaultPorts;
    }

    @Override
    public Layer getLayer() {
        return Layer.APPLICATION;
    }
}
