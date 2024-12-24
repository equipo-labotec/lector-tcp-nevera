package org.entel.lector_tcp.infra.database.adapter.repo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.infra.database.mapper.DeviceMapper;
import org.entel.lector_tcp.infra.database.info.DispositivoNeverasProjection;
import org.entel.lector_tcp.infra.database.repository.DeviceEntityRepositoryJPA;
import org.entel.lector_tcp.infra.database.repository.DispositivosOtroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class DeviceRepositoryI implements DeviceRepository {
    private final DeviceMapper deviceMapper;
    private final DeviceEntityRepositoryJPA deviceEntityRepositoryJPA;
    private final DispositivosOtroRepository dispositivosOtroRepository;

    @Override
    public Optional<Device> getDeviceByImei(String imei) {
        // Intentar encontrar el dispositivo en el repositorio
        return dispositivosOtroRepository.findByLabVchImei(imei).map
                (this::buildDevice);
    }

    private Device buildDevice(DispositivoNeverasProjection deviceProjection) {
        // Crear constantes para valores predeterminados
        final String DEFAULT_PROTOCOL = "Wialon";
        final String DEFAULT_PASSWORD = "NONE";
        final String DEFAULT_CRC16_LOGIN = "CR16CODE";

        // Construir el objeto Device
        Device device = new Device();
        device.setImei(deviceProjection.getLabVchImei());
        device.setProtocolVersion(DEFAULT_PROTOCOL);
        device.setPassword(DEFAULT_PASSWORD);
        device.setCrc16Login(DEFAULT_CRC16_LOGIN);

        return device;
    }

}
