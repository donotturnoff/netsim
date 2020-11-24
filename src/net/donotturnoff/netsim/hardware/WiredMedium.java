package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.protocol.LinkLayerProtocol;

public abstract class WiredMedium<P extends LinkLayerProtocol> extends Medium<P> {
    public WiredMedium() {
        super();
    }

    public WiredMedium(int txDelay) {
        super(txDelay);
    }
}
