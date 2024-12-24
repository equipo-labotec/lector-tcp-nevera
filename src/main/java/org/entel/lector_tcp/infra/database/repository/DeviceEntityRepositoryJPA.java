package org.entel.lector_tcp.infra.database.repository;

import org.entel.lector_tcp.infra.database.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEntityRepositoryJPA extends JpaRepository<DeviceEntity, Long> {
    DeviceEntity findByImei(String imei);

}