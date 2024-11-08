package org.entel.lector_tcp.infra.adapter.input.impl;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.entel.lector_tcp.infra.adapter.input.entity.NeverasDataPacketEntity;
import org.entel.lector_tcp.infra.adapter.input.mapper.NeverasDataPacketMapper;
import org.entel.lector_tcp.infra.adapter.input.repository.NeverasDataPacketEntityRepository;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NeveraDataPacketRepositoryI implements NeverasRepository {
    private final NeverasDataPacketMapper neverasDataPacketMapper;
    private final NeverasDataPacketEntityRepository neverasDataPacketEntityRepository;
    @Override
    public NeverasDataPacket create(NeverasDataPacket neverasDataPacket) {
        NeverasDataPacketEntity neverasDataPacketEntity = neverasDataPacketMapper.toEntity(neverasDataPacket);
        NeverasDataPacketEntity neverasDataPacketSaved = neverasDataPacketEntityRepository.save(neverasDataPacketEntity);
        return  neverasDataPacketMapper.toModel(neverasDataPacketSaved);
    }
}
