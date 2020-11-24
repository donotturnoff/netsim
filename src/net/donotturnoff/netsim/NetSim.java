package net.donotturnoff.netsim;

import net.donotturnoff.netsim.address.InternetProtocolVersion4Address;
import net.donotturnoff.netsim.address.MediaAccessControlAddress;
import net.donotturnoff.netsim.address.TransmissionControlProtocolPort;
import net.donotturnoff.netsim.data.*;
import net.donotturnoff.netsim.hardware.*;
import net.donotturnoff.netsim.protocol.Ethernet;
import net.donotturnoff.netsim.software.OperatingSystem;
import net.donotturnoff.netsim.util.SystemStateException;

import java.util.Map;

public class NetSim {

    public static void main(String[] args) {
        Laptop laptop = new Laptop();
        Desktop desktop = new Desktop();
        OperatingSystem laptopOS = new OperatingSystem();
        OperatingSystem desktopOS = new OperatingSystem();

        NetworkInterfaceCard<Ethernet> nic1 = new EthernetCard(new MediaAccessControlAddress("AA:BB:CC:DD:EE:FF"));
        NetworkInterfaceCard<Ethernet> nic2 = new EthernetCard(new MediaAccessControlAddress("FF:EE:DD:CC:BB:AA"));
        nic1.setInternetAddress(new InternetProtocolVersion4Address("192.168.1.2"));
        nic2.setInternetAddress(new InternetProtocolVersion4Address("192.168.1.3"));

        laptop.install(nic1);
        desktop.install(nic2);

        laptop.install(laptopOS);
        desktop.install(desktopOS);

        try {
            laptop.boot();
            desktop.boot();
        } catch (SystemStateException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        WiredMedium<Ethernet> ethernetCable = new EthernetCable(1000);
        nic1.attach(ethernetCable);
        nic2.attach(ethernetCable);

        Request request = new HyperTextTransferProtocolRequest(HyperTextTransferProtocolMethod.GET, "/", "1.0", Map.of(), "<h1>Hello</h1>");
        Segment segment = new TransmissionControlProtocolSegment(new TransmissionControlProtocolPort(12345), new TransmissionControlProtocolPort(80), 0, 0, 0, 0, request);
        Packet packet = new InternetProtocolVersion4Packet(0, 0, 0, 20, (InternetProtocolVersion4Address) nic1.getInternetAddress(), (InternetProtocolVersion4Address) nic2.getInternetAddress(), segment);
        Frame frame = new EthernetFrame(nic2.getHardwareAddress(), nic1.getHardwareAddress(), packet);

        System.out.println("Sending");
        nic1.transmit(frame);
    }
}
