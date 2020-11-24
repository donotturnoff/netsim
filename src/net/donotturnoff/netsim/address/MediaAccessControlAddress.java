package net.donotturnoff.netsim.address;

import net.donotturnoff.netsim.protocol.Ethernet;

public class MediaAccessControlAddress extends LinkLayerAddress<Ethernet> {

    public MediaAccessControlAddress(int[] location) throws IllegalArgumentException {
        setLocation(location);
    }

    public MediaAccessControlAddress(String s) throws IllegalArgumentException {
        setLocation(locationFromString(s));
    }

    @Override
    public boolean isValid(int[] location) {
        if (location.length != 6) {
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
        for (int i = 0; i < 5; i++) {
            int part = location[i];
            sb.append(String.format("%02X", part));
            sb.append(":");
        }
        sb.append(String.format("%02X", location[5]));
        return sb.toString();
    }

    @Override
    protected int[] locationFromString(String s) {
        String[] parts = s.split(":");
        int[] location = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            location[i] = Integer.parseInt(parts[i], 16);
        }
        return location;
    }
}
