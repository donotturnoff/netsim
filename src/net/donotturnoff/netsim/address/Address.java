package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.Protocol;

import java.util.Objects;

public abstract class Address<P extends Protocol, L> {

    protected P protocol;
    protected L location;

    public abstract boolean isValid(L location);

    public final void setProtocol(P protocol) {
        this.protocol = protocol;
    }

    public final void setLocation(L location) throws IllegalArgumentException {
        if (isValid(location)) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("Invalid location");
        }
    }

    public P getProtocol() {
        return protocol;
    }

    public L getLocation() {
        return location;
    }

    protected abstract L locationFromString(String s);

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address<?, ?> a = (Address<?, ?>) o;
        return location.equals(a.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, location);
    }
}
