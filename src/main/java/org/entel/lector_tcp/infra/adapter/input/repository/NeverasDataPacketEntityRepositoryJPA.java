
package org.entel.lector_tcp.infra.adapter.input.repository;

import org.entel.lector_tcp.infra.adapter.input.entity.NeverasDataPacketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeverasDataPacketEntityRepositoryJPA extends JpaRepository<NeverasDataPacketEntity, Long> {



}