package org.entel.lector_tcp.infra.database.mapper;

import org.entel.lector_tcp.infra.database.entity.DeviceEntity;
import org.entel.lector_tcp.domain.models.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    // Mapea DeviceEntity a Device
    Device toDevice(DeviceEntity deviceEntity);

    // Mapea Device a DeviceEntity
    DeviceEntity toDeviceEntity(Device device);
}
