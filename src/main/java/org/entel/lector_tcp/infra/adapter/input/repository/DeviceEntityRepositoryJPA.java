package org.entel.lector_tcp.infra.adapter.input.repository;

import org.entel.lector_tcp.infra.adapter.input.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEntityRepositoryJPA extends JpaRepository<DeviceEntity, Long> {
    DeviceEntity findByImei(String imei);

}