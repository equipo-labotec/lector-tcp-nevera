package org.entel.lector_tcp.infra.database.repository;

import org.entel.lector_tcp.infra.database.entity.DispositivosOtros;
import org.entel.lector_tcp.infra.database.info.DispositivoNeverasProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DispositivosOtroRepository extends JpaRepository<DispositivosOtros, Integer> {
  Optional<DispositivoNeverasProjection> findByLabVchImei(String imei);
}