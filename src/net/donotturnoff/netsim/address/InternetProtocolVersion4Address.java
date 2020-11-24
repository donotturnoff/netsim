package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.InternetProtocolVersion4;

public class InternetProtocolVersion4Address extends InternetLayerAddress<InternetProtocolVersion4> {

    public InternetProtocolVersion4Address(int[] location) throws IllegalArgumentException {
        setLocation(location);
    }

    public InternetProtocolVersion4Address(String s) throws IllegalArgumentException {
        setLocation(locationFromString(s));
    }

    @Override
    public boolean isValid(int[] location) {
        if (location.length != 4) {
            return false;
        } else {
            for (int part: location) {
                if (part < 0 || part > 255) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int part = location[i];
            sb.append(part);
            sb.append(".");
        }
        sb.append(location[3]);
        return sb.toString();
    }

    @Override
    protected int[] locationFromString(String s) {
        String[] parts = s.split("\\.");
        int[] location = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            location[i] = Integer.parseInt(parts[i]);
        }
        return location;
    }
}
