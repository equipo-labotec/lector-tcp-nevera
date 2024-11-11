package org.entel.lector_tcp.app.ports.out;

import org.entel.lector_tcp.app.mapper.NestleDataPacketMapper;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.Position;

public interface DeviceService {
    Position decodePositionWialon(Device device, Object message);
    Device findByImei(String imei);
}
