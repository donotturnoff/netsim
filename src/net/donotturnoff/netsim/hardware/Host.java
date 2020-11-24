package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.software.Interrupt;
import net.donotturnoff.netsim.software.OperatingSystem;
import net.donotturnoff.netsim.util.SystemStateException;

import java.util.*;

public abstract class Host extends Device {
    protected Set<OperatingSystem> operatingSystems;
    protected OperatingSystem currentOperatingSystem;

    public Host() {
        operatingSystems = new HashSet<>();
        currentOperatingSystem = null;
    }

    public void boot() throws SystemStateException {
        currentOperatingSystem = selectOperatingSystem();
        currentOperatingSystem.execute();
    }

    public void halt() throws SystemStateException {
        if (isRunning()) {
            currentOperatingSystem.terminate();
            currentOperatingSystem = null;
        } else {
            throw new SystemStateException("Device not running");
        }
    }

    public void interrupt(Interrupt<?> interrupt) throws SystemStateException {
        if (!isRunning()) {
            throw new SystemStateException("Device not running");
        } else {
            currentOperatingSystem.interrupt(interrupt);
        }
    }

    public void install(OperatingSystem os) {
        operatingSystems.add(os);
    }

    public void uninstall(OperatingSystem os) {
        operatingSystems.remove(os);
    }

    public boolean isRunning() {
        return currentOperatingSystem != null;
    }

    protected OperatingSystem selectOperatingSystem() throws SystemStateException {
        if (operatingSystems.isEmpty()) {
            throw new SystemStateException("No operating system installed");
        }
        System.out.println("Boot menu:");
        int i = 0;
        List<OperatingSystem> oss = new ArrayList<>();
        for (OperatingSystem os: operatingSystems) {
            oss.add(os);
            System.out.println(i + ") " + os.getName());
        }
        Scanner scanner = new Scanner(System.in);
        int j = -1;
        while (j < 0 || j > oss.size()) {
            j = scanner.nextInt();
        }
        return oss.get(j);
    }

    public OperatingSystem getCurrentOperatingSystem() {
        return currentOperatingSystem;
    }
}
