package net.donotturnoff.netsim.protocol;

public interface Protocol {
    Ethernet Ethernet = new Ethernet();
    InternetProtocolVersion4 IPv4 = new InternetProtocolVersion4();
    AddressResolutionProtocol ARP = new AddressResolutionProtocol();
    InternetProtocolVersion6 IPv6 = new InternetProtocolVersion6();
    TransmissionControlProtocol TCP = new TransmissionControlProtocol();
    UserDatagramProtocol UDP = new UserDatagramProtocol();
    HyperTextTransferProtocol HTTP = new HyperTextTransferProtocol();
    DomainNameSystem DNS = new DomainNameSystem();

    Layer getLayer();
}
