package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.protocol.LinkLayerProtocol;
import net.donotturnoff.netsim.software.Interrupt;
import net.donotturnoff.netsim.util.SystemStateException;

import java.util.HashSet;
import java.util.Set;

public abstract class Device extends Hardware {
    protected Set<NetworkInterfaceCard<? extends LinkLayerProtocol>> nics;

    public Device() {
        nics = new HashSet<>();
    }

    public abstract void boot() throws SystemStateException;

    public abstract void halt() throws SystemStateException;

    public abstract void interrupt(Interrupt<?> interrupt) throws SystemStateException;

    public abstract boolean isRunning();

    public <L extends LinkLayerProtocol> void install(NetworkInterfaceCard<L> nic) {
        nics.add(nic);
        nic.installOn(this);
    }

    public <L extends LinkLayerProtocol> void uninstall(NetworkInterfaceCard<L> nic) {
        nics.remove(nic);
        nic.uninstall();
    }
}
