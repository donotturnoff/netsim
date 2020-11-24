package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.address.InternetProtocolVersion4Address;
import net.donotturnoff.netsim.address.InternetLayerAddress;
import net.donotturnoff.netsim.protocol.InternetProtocolVersion4;
import net.donotturnoff.netsim.protocol.Protocol;
import net.donotturnoff.netsim.protocol.TransportLayerProtocol;
import net.donotturnoff.netsim.util.Util;

public class InternetProtocolVersion4Packet extends Packet {

    public InternetProtocolVersion4Packet(int id, int flags, int fragmentOffset, int ttl, InternetProtocolVersion4Address srcAddr, InternetProtocolVersion4Address dstAddr, Segment payload) throws IllegalArgumentException {
        this(0x0, 0x0, id, flags, fragmentOffset, ttl, srcAddr, dstAddr, new int[0], payload);
    }

    public InternetProtocolVersion4Packet(int dscp, int ecn, int id, int flags, int fragmentOffset, int ttl, InternetProtocolVersion4Address srcAddr, InternetProtocolVersion4Address dstAddr, int[] options, Segment payload) throws IllegalArgumentException {
        setProtocol(Protocol.IPv4);
        setVersion();
        setDSCP(dscp);
        setECN(ecn);
        setIdentification(id);
        setFlags(flags);
        setFragmentOffset(fragmentOffset);
        setTTL(ttl);
        setProtocolCode(payload.getProtocol());
        setSourceIPAddress(srcAddr);
        setDestinationIPAddress(dstAddr);
        setOptions(options);
        setPayload(payload);
        setIHL();
        setTotalLength();
        setHeaderChecksum();
    }

    private void setVersion() {
        setHeader("Version", new int[]{0x4});
    }

    private void setDSCP(int dscp) throws IllegalArgumentException {
        if (dscp < 0 || dscp > 63) {
            throw new IllegalArgumentException("DSCP field is 6 bits");
        } else {
            setHeader("DSCP", new int[]{dscp});
        }
    }

    private void setIHL() {
        int ihl = 5 + headers.get("Options").length/4;
        setHeader("IHL", new int[]{ihl});
    }

    private void setECN(int ecn) throws IllegalArgumentException {
        if (ecn < 0 || ecn > 3) {
            throw new IllegalArgumentException("ECN field is 2 bits");
        } else {
            setHeader("ECN", new int[]{ecn});
        }
    }

    private void setTotalLength() {
        int totalLength = getIHL() + payload.size()/4;
        setHeader("Total length", Util.toOctets(totalLength));
    }

    private void setIdentification(int id) throws IllegalArgumentException {
        if (id < 0 || id > 65535) {
            throw new IllegalArgumentException("Identification field is 16 bits");
        } else {
            setHeader("Identification", Util.toOctets(id));
        }
    }

    private void setFlags(int flags) throws IllegalArgumentException {
        if (flags < 0 || flags > 3) {
            throw new IllegalArgumentException("Flags field is 3 bits, with MSB reserved");
        } else {
            setHeader("Flags", new int[]{flags});
        }
    }

    private void setFragmentOffset(int fragmentOffset) throws IllegalArgumentException {
        if (fragmentOffset < 0 || fragmentOffset > 8191) {
            throw new IllegalArgumentException("Fragment offset field is 16 bits");
        } else {
            setHeader("Identification", Util.toOctets(fragmentOffset));
        }
    }

    private void setTTL(int ttl) throws IllegalArgumentException {
        if (ttl < 0 || ttl > 255) {
            throw new IllegalArgumentException("TTL field is 8 bits");
        } else {
            setHeader("TTL", new int[]{ttl});
        }
    }

    private void setProtocolCode(TransportLayerProtocol protocol) {
        setHeader("Protocol", new int[]{protocol.getProtocolCode()});
    }

    private void setHeaderChecksum() {
        //TODO: implement
    }

    private void setSourceIPAddress(InternetLayerAddress<InternetProtocolVersion4> srcAddr) {
        setHeader("Source IP address", srcAddr.getLocation());
    }

    private void setDestinationIPAddress(InternetLayerAddress<InternetProtocolVersion4> dstAddr) {
        setHeader("Destination IP address", dstAddr.getLocation());
    }

    private void setOptions(int[] options) throws IllegalArgumentException {
        if (options.length > 40) {
            throw new IllegalArgumentException("Options field is 320 bits maximum");
        } else {
            int paddedSize = (int) (4 * Math.round(options.length / 4.));
            int[] paddedOptions = new int[paddedSize];
            System.arraycopy(options, 0, paddedOptions, 0, options.length);
            for (int i = options.length; i < paddedSize; i++) {
                paddedOptions[i] = 0x00;
            }
            setHeader("Options", Util.toOctets(paddedOptions));
        }
    }

    public int getVersion() {
        return getHeader("Version")[0];
    }

    public int getIHL() {
        return getHeader("IHL")[0];
    }

    public int getDSCP() {
        return getHeader("DSCP")[0];
    }

    public int getECN() {
        return getHeader("ECN")[0];
    }

    public int getTotalLength() {
        return (int) Util.fromOctets( getHeader("Total length"));
    }

    public int getIdentification() {
        return (int) Util.fromOctets(getHeader("Identification"));
    }

    public int getFlags() {
        return getHeader("Flags")[0];
    }

    public int getFragmentOffset() {
        return (int) Util.fromOctets(getHeader("Fragment offset"));
    }

    public int getTTL() {
        return getHeader("TTL")[0];
    }

    public int getProtocolCode() {
        return getHeader("Protocol")[0];
    }

    public int getHeaderChecksum() {
        return (int) Util.fromOctets(getHeader("Header checksum"));
    }

    public InternetProtocolVersion4Address getSourceIPAddress() {
        return new InternetProtocolVersion4Address(getHeader("Source IP address"));
    }

    public InternetProtocolVersion4Address getDestinationIPAddress() {
        return new InternetProtocolVersion4Address(getHeader("Destination IP address"));
    }

    public int[] getOptions() {
        return getHeader("Options");
    }

    @Override
    public int size() {
        return getTotalLength()*4;
    }

    @Override
    public int[] toOctets() {
        //TODO: implement
        return new int[0];
    }
}
