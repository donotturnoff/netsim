package net.donotturnoff.netsim.protocol;

public class TransportLayerProtocol implements Protocol {
    protected final int protocolCode;

    TransportLayerProtocol(int protocolCode) {
        this.protocolCode = protocolCode;
    }

    public int getProtocolCode() {
        return protocolCode;
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSPORT;
    }
}
