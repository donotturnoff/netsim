package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.address.LinkLayerAddress;
import net.donotturnoff.netsim.address.MediaAccessControlAddress;
import net.donotturnoff.netsim.protocol.Ethernet;
import net.donotturnoff.netsim.protocol.InternetLayerProtocol;
import net.donotturnoff.netsim.protocol.Protocol;
import net.donotturnoff.netsim.util.Util;

//TODO: VLAN tags

public class EthernetFrame extends Frame {

    public EthernetFrame(LinkLayerAddress<Ethernet> dstMac, LinkLayerAddress<Ethernet> srcMac, Packet payload) {
        setProtocol(Protocol.Ethernet);
        setPreamble();
        setSFD();
        setDestinationMAC(dstMac);
        setSourceMAC(srcMac);
        setEtherType(payload.getProtocol());
        setFCS();
        setPayload(payload);
    }

    private void setPreamble() {
        setHeader("Preamble", new int[]{0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA, 0xAA});
    }

    private void setSFD() {
        setHeader("SFD", new int[]{0xAB});
    }

    private void setDestinationMAC(LinkLayerAddress<Ethernet> dstMac) {
        setHeader("Destination MAC", dstMac.getLocation());
    }

    private void setSourceMAC(LinkLayerAddress<Ethernet> srcMac) {
        setHeader("Source MAC", srcMac.getLocation());
    }

    private void setEtherType(InternetLayerProtocol protocol) {
        setHeader("EtherType", Util.toOctets(protocol.getEtherType()));
    }

    private void setFCS() {
        //TODO: implement CRC checksum
        setFooter("FCS", new int[]{0x00});
    }

    public final int[] getPreamble() {
        return getHeader("Preamble");
    }

    public final int[] getSFD() {
        return getHeader("SFD");
    }

    public final MediaAccessControlAddress getDestinationMAC() {
        return new MediaAccessControlAddress(getHeader("Destination MAC"));
    }

    public final MediaAccessControlAddress getSourceMAC() {
        return new MediaAccessControlAddress(getHeader("Source MAC"));
    }

    public final int[] getEtherType() {
        return getHeader("EtherType");
    }

    public final int[] getFCS() {
        return getHeader("FCS");
    }

    @Override
    public int size() {
        //TODO: add VLAN tags fields size
        return 26 + payload.size();
    }

    @Override
    public int[] toOctets() {
        //TODO: implement
        return new int[0];
    }
}
