package net.donotturnoff.netsim.util;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static int[] toOctets(long x) {
        if (x == 0) {
            return new int[]{0};
        } else {
            int n = (int) Math.ceil(Math.log(x) / Math.log(256));
            int[] octets = new int[n];
            for (int i = 0; i < n; i++) {
                octets[i] = (int) ((x >>> ((n - i) * 8)) & 0xFF);
            }
            return octets;
        }
    }

    public static int[] toOctets(int[] xs) {
        //TODO: optimise (improve time complexity)
        List<Integer> octetsList = new ArrayList<>();
        for (int x: xs) {
            int[] thisOctets = toOctets(x);
            for (int octet: thisOctets) {
                octetsList.add(octet);
            }
        }
        int[] octets = new int[octetsList.size()];
        for (int i = 0; i < octets.length; i++) {
            octets[i] = octetsList.get(i);
        }
        return octets;
    }

    public static long fromOctets(int[] octets) {
        int n = octets.length;
        long x = 0;
        for (int i = 0; i < n; i++) {
            x += octets[i] << (8*(n-i));
        }
        return x;
    }
}
