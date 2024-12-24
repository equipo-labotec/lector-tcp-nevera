package org.entel.lector_tcp.infra.database.mapper;

import org.entel.lector_tcp.infra.database.entity.NeverasDataPacketEntity;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DeviceMapper.class)
public interface NeverasDataPacketMapper {


    // Mapea NeverasDataPacketEntity a NeverasDataPacket
    NeverasDataPacket toModel(NeverasDataPacketEntity entity);

    // Mapea NeverasDataPacket a NeverasDataPacketEntity
    NeverasDataPacketEntity toEntity(NeverasDataPacket model);
}
