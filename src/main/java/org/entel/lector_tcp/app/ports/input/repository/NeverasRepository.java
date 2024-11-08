package org.entel.lector_tcp.app.ports.input.repository;

import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.entel.lector_tcp.domain.models.Position;

public interface NeverasRepository {
    NeverasDataPacket create (NeverasDataPacket neverasDataPacket);
}
