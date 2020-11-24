package net.donotturnoff.netsim.software;

public abstract class Firmware extends KernelSpaceSoftware {
    public abstract void interrupt(Interrupt<?> interrupt);
}
