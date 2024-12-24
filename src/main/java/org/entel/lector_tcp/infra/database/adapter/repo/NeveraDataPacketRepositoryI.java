package org.entel.lector_tcp.infra.database.adapter.repo;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;
import org.entel.lector_tcp.infra.database.entity.NeverasDataPacketEntity;
import org.entel.lector_tcp.infra.database.mapper.NeverasDataPacketMapper;
import org.entel.lector_tcp.infra.database.repository.NeverasDataPacketEntityRepositoryJPA;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NeveraDataPacketRepositoryI implements NeverasRepository {
    private final NeverasDataPacketMapper neverasDataPacketMapper;
    private final NeverasDataPacketEntityRepositoryJPA neverasDataPacketEntityRepository;
    @Override
    public NeverasDataPacket create(NeverasDataPacket neverasDataPacket) {
        NeverasDataPacketEntity neverasDataPacketEntity = neverasDataPacketMapper.toEntity(neverasDataPacket);
        NeverasDataPacketEntity neverasDataPacketSaved = neverasDataPacketEntityRepository.save(neverasDataPacketEntity);
        return  neverasDataPacketMapper.toModel(neverasDataPacketSaved);
    }
}
