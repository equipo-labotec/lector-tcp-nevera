package org.entel.lector_tcp.infra.adapter.input.impl;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.infra.adapter.input.entity.DeviceEntity;
import org.entel.lector_tcp.infra.adapter.input.mapper.DeviceMapper;
import org.entel.lector_tcp.infra.adapter.input.repository.DeviceEntityRepository;
import org.entel.lector_tcp.infra.exception.EntityNotFound;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DeviceRepositoryI implements DeviceRepository {
    private final DeviceMapper deviceMapper;
    private final DeviceEntityRepository deviceEntityRepository;
    @Override
    public Device getDeviceByImei(String imei) {
        DeviceEntity deviceEntity = deviceEntityRepository.findByImei(imei);
        return deviceMapper.toDevice(deviceEntity);
    }
}
