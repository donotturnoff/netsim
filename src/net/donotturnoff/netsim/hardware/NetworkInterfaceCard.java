package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.address.InternetLayerAddress;
import net.donotturnoff.netsim.address.LinkLayerAddress;
import net.donotturnoff.netsim.data.Frame;
import net.donotturnoff.netsim.protocol.InternetLayerProtocol;
import net.donotturnoff.netsim.protocol.LinkLayerProtocol;
import net.donotturnoff.netsim.software.Interrupt;
import net.donotturnoff.netsim.util.HardwareException;
import net.donotturnoff.netsim.util.SystemStateException;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public abstract class NetworkInterfaceCard<L extends LinkLayerProtocol> extends Hardware {
    protected Device device;
    protected Medium<L> medium;
    protected LinkLayerAddress<L> hardwareAddress;
    protected InternetLayerAddress<? extends InternetLayerProtocol> internetAddress, broadcastAddress, netmask;
    protected Queue<Frame> txQueue, rxQueue;
    protected Map<NICStatistic, Integer> stats;
    protected List<Frame> received, transmitted;

    public void setHardwareAddress(LinkLayerAddress<L> hardwareAddress) {
        this.hardwareAddress = hardwareAddress;
    }

    public void setInternetAddress(InternetLayerAddress<? extends InternetLayerProtocol> internetAddress) {
        this.internetAddress = internetAddress;
    }

    public LinkLayerAddress<L> getHardwareAddress() {
        return hardwareAddress;
    }

    public InternetLayerAddress<? extends InternetLayerProtocol> getInternetAddress() {
        return internetAddress;
    }

    public void installOn(Device device) {
        this.device = device;
    }

    public void uninstall() {
        this.device = null;
    }

    public void attach(Medium<L> medium) {
        this.medium = medium;
        medium.attachTo(this);
    }

    public void detach() {
        medium.detachFrom(this);
        this.medium = null;
    }

    public void transmit(Frame frame) {
        try {
            if (medium == null) {
                throw new SystemStateException("Network interface card not connected");
            } else if (device == null) {
                throw new SystemStateException("No parent device");
            } else if (!device.isRunning()) {
                throw new SystemStateException("Device not running");
            } else {
                medium.transmit(frame, this::error);
            }
        } catch (SystemStateException e) {
            error(e);
        }
    }

    public void receive(Frame frame) {
        try {
            if (device == null) {
                throw new SystemStateException("Device not running");
            }
            Interrupt<Frame> i = new Interrupt<>(frame);
            device.interrupt(i);
        } catch (SystemStateException e) {
            error(e);
        }
    }

    public void txQueueAdd(Frame frame) {
        txQueue.add(frame);
    }

    public Frame rxQueuePoll() {
        return rxQueue.poll();
    }

    public void error(Exception e) {
        e.printStackTrace();
    }
}
