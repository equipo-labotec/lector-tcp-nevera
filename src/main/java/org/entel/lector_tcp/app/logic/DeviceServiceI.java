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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
public class DeviceServiceI implements DeviceService {
    private final DispositivoRepository dispositivoRepository;
    private final DeviceRepository deviceRepository;
    private final NeverasRepository neverasRepository;
    private final WialonProtocolDecoder wialonProtocolDecoder = new WialonProtocolDecoder();
    private final NestleDataPacketMapper nestleDataPacketMapper = new NestleDataPacketMapper();
    private final Logger logger = LoggerFactory.getLogger(DeviceServiceI.class);

    @Override
    public Position decodePositionWialon(Device device, Object message) {
        String typeMessageDecode = wialonProtocolDecoder.getTypeMessage(message);
        logger.info("Tipo de mensaje recibido: {}", typeMessageDecode);

        String dataMessage = wialonProtocolDecoder.getDataMessage(message);
        logger.debug("Datos del mensaje recibidos: {}", dataMessage);

        // Intentar decodificar la posición
        Position position = (Position) wialonProtocolDecoder.decode(typeMessageDecode , dataMessage);

        if (position == null) {
            // Registrar cuando la posición es nula (no se puede decodificar)
            logger.warn("La decodificación de la posición ha fallado. El mensaje no es válido.");
            return null; // No procesar nada si la posición es nula
        }

        // Log cuando la posición ha sido decodificada correctamente
        logger.info("Posición decodificada correctamente: {}", position);

        // Si la posición no es nula, procesar los datos
        try {
            NeverasDataPacket neverasDataPacket = nestleDataPacketMapper.map(position);
            neverasDataPacket.setDeviceCredentials(device);
            logger.debug("Paquete de datos de nevera mapeado: {}", neverasDataPacket);

            // Manejar la posición
            handlerPosition(device, neverasDataPacket);
            logger.info("Posición manejada correctamente para el dispositivo con IMEI: {}", device.getImei());
        } catch (Exception e) {
            // Log de error si ocurre alguna excepción en el procesamiento
            logger.error("Error procesando los datos de posición para el dispositivo con IMEI: {}", device.getImei(), e);
        }

        return position;
    }

    @Override
    public Optional<Device> findByImei(String imei){
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
