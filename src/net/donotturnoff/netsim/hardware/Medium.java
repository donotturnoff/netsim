package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.data.Frame;
import net.donotturnoff.netsim.protocol.LinkLayerProtocol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Medium<P extends LinkLayerProtocol> extends Hardware {
    private final int txDelay;
    private boolean busy;
    private final Set<NetworkInterfaceCard<P>> nics;
    private final List<Frame> transmitted;

    Medium() {
        this.txDelay = 0;
        this.busy = false;
        this.nics = new HashSet<>();
        this.transmitted = new ArrayList<>();
    }

    Medium(int txDelay) {
        this.txDelay = txDelay;
        this.busy = false;
        this.nics = new HashSet<>();
        this.transmitted = new ArrayList<>();
    }

    public final void attachTo(NetworkInterfaceCard<P> nic) {
        nics.add(nic);
    }

    public final void detachFrom(NetworkInterfaceCard<P> nic) {
        nics.remove(nic);
    }

    public void transmit(Frame data, Consumer<Exception> errorHandler) {
        Runnable transmitter =
                () -> {
                    try {
                        busy = true;
                        Thread.sleep(txDelay);
                        for (NetworkInterfaceCard<P> nic: nics) {
                            nic.receive(data);
                        }
                        busy = false;
                    } catch (Exception e) {
                        errorHandler.accept(e);
                    }
                };
        Thread txThread = new Thread(transmitter);
        txThread.start();
    }

    public final boolean isBusy() {
        return busy;
    }

    public final List<Frame> getTransmitted() {
        return transmitted;
    }
}
