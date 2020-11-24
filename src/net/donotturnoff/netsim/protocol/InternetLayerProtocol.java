package net.donotturnoff.netsim.protocol;

public class InternetLayerProtocol implements Protocol {
    protected final int etherType;

    InternetLayerProtocol(int etherType) {
        this.etherType = etherType;
    }

    public int getEtherType() {
        return etherType;
    }

    @Override
    public Layer getLayer() {
        return Layer.INTERNET;
    }
}
