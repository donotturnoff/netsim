package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.protocol.Protocol;

import java.util.HashMap;
import java.util.Map;

public abstract class ProtocolDataUnit<P extends Protocol, H, B> {

    protected P protocol;
    protected Map<String, H> headers = new HashMap<>();
    protected B payload;
    protected Map<String, H> footers = new HashMap<>();

    protected void setProtocol(P protocol) {
        this.protocol = protocol;
    }

    protected final void setHeaders(Map<String, H> headers) {
        this.headers = headers;
    }

    protected final void setHeader(String key, H value) {
        headers.put(key, value);
    }

    protected final void setPayload(B payload) {
        this.payload = payload;
    }

    protected final void setFooters(Map<String, H> footers) {
        this.footers = footers;
    }

    protected final void setFooter(String key, H value) {
        footers.put(key, value);
    }

    public final P getProtocol() {
        return protocol;
    }

    public Map<String, H> getHeaders() {
        return headers;
    }

    public final H getHeader(String key) {
        return headers.get(key);
    }

    public final B getPayload() {
        return payload;
    }

    public final Map<String, H> getFooters() {
        return footers;
    }

    public final H getFooter(String key) {
        return footers.get(key);
    }

    public abstract int size();

    public String toString() {
        return this.getClass().getName() +
                "[headers=" +
                headers +
                ", payload=" +
                payload +
                ", footers=" +
                footers +
                ", size=" +
                size() +
                "]";
    }

    public abstract int[] toOctets();
}
