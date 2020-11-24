package net.donotturnoff.netsim.software;

import net.donotturnoff.netsim.hardware.NetworkInterfaceCard;
import net.donotturnoff.netsim.protocol.LinkLayerProtocol;

public abstract class Driver extends SystemSoftware {
    private NetworkInterfaceCard<? extends LinkLayerProtocol> nic;

    public abstract void interrupt(Interrupt<?> interrupt);
}
