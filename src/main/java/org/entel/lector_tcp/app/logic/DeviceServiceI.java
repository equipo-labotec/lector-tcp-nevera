package org.entel.lector_tcp.app.logic;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.mapper.NestleDataPacketMapper;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.app.ports.out.DeviceService;
import org.entel.lector_tcp.app.protocol.WialonProtocolDecoder;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.entel.lector_tcp.domain.models.Position;

@AllArgsConstructor
public class DeviceServiceI implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final NeverasRepository neverasRepository;
    private final WialonProtocolDecoder wialonProtocolDecoder = new WialonProtocolDecoder();
    private final NestleDataPacketMapper nestleDataPacketMapper = new NestleDataPacketMapper();

    @Override
    public Position decodePositionWialon(Device device, Object message) {
        String typeMessageDecode = wialonProtocolDecoder.getTypeMessage(message);
        System.out.println(typeMessageDecode.toString());
        String dataMessage = wialonProtocolDecoder.getDataMessage(message);
        System.out.println(dataMessage.toString());
        Position position = (Position) wialonProtocolDecoder.decode(typeMessageDecode , dataMessage);
        System.out.println(position.toString());
        NeverasDataPacket neverasDataPacket = nestleDataPacketMapper.map(position);
        neverasDataPacket.setDeviceCredentials(device);
        neverasRepository.create(neverasDataPacket);
        return position;
    }

    @Override
    public Device findByImei(String imei){
        return deviceRepository.getDeviceByImei(imei);
    }
}
