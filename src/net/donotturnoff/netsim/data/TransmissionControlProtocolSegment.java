package net.donotturnoff.netsim.data;

import net.donotturnoff.netsim.address.TransmissionControlProtocolPort;
import net.donotturnoff.netsim.protocol.Protocol;
import net.donotturnoff.netsim.util.Util;

public class TransmissionControlProtocolSegment extends Segment {

    public TransmissionControlProtocolSegment(TransmissionControlProtocolPort srcPort, TransmissionControlProtocolPort dstPort, int seqNumber, int ackNumber, int controlBits, int window, Request payload) throws IllegalArgumentException {
        this(srcPort, dstPort, seqNumber, ackNumber, controlBits, window, 0x0, new int[0], payload);
    }

    public TransmissionControlProtocolSegment(TransmissionControlProtocolPort srcPort, TransmissionControlProtocolPort dstPort, int seqNumber, int ackNumber, int controlBits, int window, int urgentPointer, int[] options, Request payload) throws IllegalArgumentException {
        setProtocol(Protocol.TCP);
        setSourcePort(srcPort);
        setDestinationPort(dstPort);
        setSequenceNumber(seqNumber);
        setAcknowledgementNumber(ackNumber);
        setReserved();
        setControlBits(controlBits);
        setWindow(window);
        setUrgentPointer(urgentPointer);
        setOptions(options);
        setPayload(payload);
        setDataOffset();
    }

    private void setSourcePort(TransmissionControlProtocolPort srcPort) {
        setHeader("Source port", Util.toOctets(srcPort.getLocation()));
    }

    private void setDestinationPort(TransmissionControlProtocolPort dstPort) {
        setHeader("Destination port", Util.toOctets(dstPort.getLocation()));
    }

    private void setSequenceNumber(int seqNumber) {
        setHeader("Sequence number", Util.toOctets(seqNumber));
    }

    private void setAcknowledgementNumber(int ackNumber) {
        setHeader("Acknowledgement number", Util.toOctets(ackNumber));
    }

    private void setDataOffset() throws IllegalArgumentException {
        int offset = 5 + headers.get("Options").length/4;
        if (offset > 15) {
            throw new IllegalArgumentException("Data offset field is 4 bits");
        } else {
            setHeader("Data offset", new int[]{offset});
        }
    }

    private void setReserved() {
        setHeader("Reserved", new int[]{0x00});
    }

    private void setControlBits(int controlBits) throws IllegalArgumentException {
        if (controlBits > 63) {
            throw new IllegalArgumentException("Control bits field is 6 bits");
        } else {
            setHeader("Control bits", new int[]{controlBits});
        }
    }

    private void setWindow(int window) throws IllegalArgumentException {
        if (window > 65535) {
            throw new IllegalArgumentException("Window field is 16 bits");
        } else {
            setHeader("Window", Util.toOctets(window));
        }
    }

    public void setChecksum(Packet wrapper) {
        //TODO: implement
    }

    private void setUrgentPointer(int urgentPointer) {
        if (urgentPointer > 65535) {
            throw new IllegalArgumentException("Urgent pointer field is 16 bits");
        } else {
            setHeader("Urgent pointer", Util.toOctets(urgentPointer));
        }
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
            setHeader("Options", paddedOptions);
        }
    }

    public TransmissionControlProtocolPort getSourcePort() {
        return new TransmissionControlProtocolPort((int) Util.fromOctets(getHeader("Source port")));
    }

    public TransmissionControlProtocolPort getDestinationPort() {
        return new TransmissionControlProtocolPort((int) Util.fromOctets(getHeader("Destination port")));
    }

    public int getSequenceNumber() {
        return (int) Util.fromOctets(getHeader("Sequence number"));
    }

    public int getAcknowledgementNumber() {
        return (int) Util.fromOctets(getHeader("Acknowledgement number"));
    }

    public int getDataOffset() {
        return getHeader("Data offset")[0];
    }

    public int getReserved() {
        return getHeader("Reserved")[0];
    }

    public int getControlBits() {
        return getHeader("Control bits")[0];
    }

    public int getWindow() {
        return (int) Util.fromOctets(getHeader("Window"));
    }

    public int getChecksum() {
        return (int) Util.fromOctets(getHeader("Checksum"));
    }

    public int getUrgentPointer() {
        return (int) Util.fromOctets(getHeader("Urgent pointer"));
    }

    public int[] getOptions() {
        return getHeader("Options");
    }

    @Override
    public int size() {
        return getDataOffset()*4+payload.size();
    }

    @Override
    public int[] toOctets() {
        //TODO: implement
        return new int[0];
    }
}
