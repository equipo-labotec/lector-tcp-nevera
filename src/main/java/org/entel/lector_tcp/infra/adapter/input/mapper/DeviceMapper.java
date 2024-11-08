package org.entel.lector_tcp.infra.adapter.input.mapper;

import org.entel.lector_tcp.infra.adapter.input.entity.DeviceEntity;
import org.entel.lector_tcp.domain.models.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    // Mapea DeviceEntity a Device
    Device toDevice(DeviceEntity deviceEntity);

    // Mapea Device a DeviceEntity
    DeviceEntity toDeviceEntity(Device device);
}
