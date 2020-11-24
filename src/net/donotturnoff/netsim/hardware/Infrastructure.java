package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.software.Firmware;
import net.donotturnoff.netsim.software.Interrupt;
import net.donotturnoff.netsim.util.SystemStateException;

public abstract class Infrastructure extends Device {

    protected Firmware firmware;
    protected boolean running;

    public Infrastructure() {
        running = false;
    }

    public void boot() {
        running = true;
        firmware.execute();
    }

    public void halt() throws SystemStateException {
        if (isRunning()) {
            firmware.terminate();
            running = false;
        } else {
            throw new SystemStateException("Device not running");
        }
    }

    public void interrupt(Interrupt<?> interrupt) throws SystemStateException {
        if (!isRunning()) {
            throw new SystemStateException("Device not running");
        } else {
            firmware.interrupt(interrupt);
        }
    }

    public boolean isRunning() {
        return running;
    }
}
