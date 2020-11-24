package net.donotturnoff.netsim.protocol;

public class LinkLayerProtocol implements Protocol {
    @Override
    public Layer getLayer() {
        return Layer.LINK;
    }
}
