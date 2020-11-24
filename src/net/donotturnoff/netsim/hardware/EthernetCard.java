package net.donotturnoff.netsim.hardware;

import net.donotturnoff.netsim.address.MediaAccessControlAddress;
import net.donotturnoff.netsim.protocol.Ethernet;

public class EthernetCard extends NetworkInterfaceCard<Ethernet> {

    public EthernetCard(MediaAccessControlAddress hardwareAddress) {
        setHardwareAddress(hardwareAddress);
    }

}
