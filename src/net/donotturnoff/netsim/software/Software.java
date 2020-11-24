package net.donotturnoff.netsim.software;

import net.donotturnoff.netsim.util.SystemStateException;

public abstract class Software implements Runnable {
    protected Thread thread;
    protected int pid;
    protected String name;

    public void execute() {
        thread = new Thread(this);
        thread.start();
    }

    public void terminate() throws SystemStateException  {
        if (!isRunning()) {
            throw new SystemStateException("Software not running");
        } else {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return thread.isAlive();
    }
}
