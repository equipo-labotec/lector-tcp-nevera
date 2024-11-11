package org.entel.lector_tcp.app.logic;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.mapper.NestleDataPacketMapper;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.app.ports.input.repository.DispositivoRepository;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.app.ports.out.DeviceService;
import org.entel.lector_tcp.app.protocol.WialonProtocolDecoder;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.entel.lector_tcp.domain.models.Position;

import java.util.Date;

@AllArgsConstructor
public class DeviceServiceI implements DeviceService {
    private final DispositivoRepository dispositivoRepository;
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
        Position position = (Position) wialonProtocolDecoder.decode(typeMessageDecode , dataMessage); //si aqui devuelve null como lo pdoria manejar en el servidor para que no haga nada ?
        System.out.println(position.toString());
        // Si la posición es null, se omite el procesamiento
        if (position != null) {

            NeverasDataPacket neverasDataPacket = nestleDataPacketMapper.map(position);
            neverasDataPacket.setDeviceCredentials(device);
            handlerPosition(device, neverasDataPacket);
        }
        return position;
    }

    @Override
    public Device findByImei(String imei){
        return deviceRepository.getDeviceByImei(imei);
    }

    private void handlerPosition(Device device, NeverasDataPacket position) {
        String latitude = position.getLatitude();
        String longitude =position.getLongitude();
        int satellites = position.getSatellites();
        int estado = position.getInputs();
        String imei = device.getImei();
        double bateria_interna = position.getAdc();
        int movimiento = position.getMov();
        String fecha_hora= new Date().toInstant().toString();
        double bateria_externa = position.getPwr();
        double signal = position.getCsq();
        int geo = position.getGeo();
        int power = position.getPwr();


        dispositivoRepository.actualizarDispositivoNevera(
                latitude,
                longitude,
                satellites,
                estado,
                imei);

        dispositivoRepository.registrarMedicionNevera(
                imei,
                String.valueOf(estado),
                bateria_interna,
                movimiento,
                fecha_hora,
                bateria_externa ,
                signal,
                geo,
                latitude,
                longitude,
                power

        );

    }
}
