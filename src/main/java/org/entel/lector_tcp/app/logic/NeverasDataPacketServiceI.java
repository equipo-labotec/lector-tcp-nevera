package org.entel.lector_tcp.app.logic;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.app.ports.out.NeverasDataPacketService;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
@AllArgsConstructor
public class NeverasDataPacketServiceI implements NeverasDataPacketService {
    private final NeverasRepository neverasRepository;

    @Override
    public NeverasDataPacket create(Device device, NeverasDataPacket neverasDataPacket) {
        neverasDataPacket.setDeviceCredentials(device);
        return neverasRepository.create(neverasDataPacket);
    }
}
