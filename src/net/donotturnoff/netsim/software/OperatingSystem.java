package net.donotturnoff.netsim.software;

import net.donotturnoff.netsim.util.SystemStateException;

import java.util.HashSet;
import java.util.Set;

public class OperatingSystem extends KernelSpaceSoftware {

    private final Set<UserspaceSoftware> software;

    public OperatingSystem() {
        pid = 0;
        name = "Generic OS";
        software = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<UserspaceSoftware> getSoftware() {
        return software;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.interrupted()) {
                break;
            }
        }
    }

    public void interrupt(Interrupt<?> interrupt) throws SystemStateException {
        if (!isRunning()) {
            throw new SystemStateException("Operating system not running");
        }
        System.out.println(interrupt.getData());
    }

    public void install(UserspaceSoftware software) {
        this.software.add(software);
    }

    public void uninstall(UserspaceSoftware software) {
        this.software.remove(software);
    }
}
