package net.donotturnoff.netsim.software;

public class Interrupt<T> {
    private final T data;

    public Interrupt(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}