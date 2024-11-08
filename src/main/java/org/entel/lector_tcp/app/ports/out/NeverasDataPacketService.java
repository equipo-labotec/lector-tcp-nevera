package org.entel.lector_tcp.app.ports.out;

import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;

public interface NeverasDataPacketService {
    NeverasDataPacket create (Device device, NeverasDataPacket neverasDataPacket);
}
