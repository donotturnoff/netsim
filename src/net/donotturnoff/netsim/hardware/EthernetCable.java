package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.protocol.Ethernet;

public class EthernetCable extends WiredMedium<Ethernet> {
    public EthernetCable() {
        super();
    }

    public EthernetCable(int txDelay) {
        super(txDelay);
    }
}
