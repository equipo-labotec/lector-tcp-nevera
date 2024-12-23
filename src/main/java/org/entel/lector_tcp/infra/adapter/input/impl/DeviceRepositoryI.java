package org.entel.lector_tcp.infra.adapter.input.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.infra.adapter.input.mapper.DeviceMapper;
import org.entel.lector_tcp.infra.adapter.input.repository.DeviceEntityRepositoryJPA;
import org.entel.lector_tcp.infra.adapter.input.repository.TblLabDispositivosotroRepository;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class DeviceRepositoryI implements DeviceRepository {
    private final DeviceMapper deviceMapper;
    private final DeviceEntityRepositoryJPA deviceEntityRepositoryJPA;
    private final TblLabDispositivosotroRepository dispositivosOtroRepository;

    @Override
    public Device getDeviceByImei(String imei) {
        String imeiDispositivo = dispositivosOtroRepository.findByLabVchImei(imei).getLabVchImei();
        if (imeiDispositivo == null){
            return null;
        }
        String protocol = "Wialon"; // PROXIMAS IMPLEMENTACIONES SI SE LLEGA A HACER MAS FUNCIONES PORQUE ESTO NO TIENE ESPECIFICADO EN BD
        String password = "NONE";
        String crc16Login = "CR16CODE";
        Device device = new Device();
        device.setImei(imeiDispositivo);
        device.setProtocolVersion(protocol);
        device.setPassword(password);
        device.setCrc16Login(crc16Login);
        System.out.println(device.toString());
        return device;
    }
    private Object handlerNotFound(){
        return null;
    }
}
