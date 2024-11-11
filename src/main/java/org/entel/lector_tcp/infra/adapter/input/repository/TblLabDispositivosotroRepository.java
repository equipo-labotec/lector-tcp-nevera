package org.entel.lector_tcp.infra.adapter.input.repository;

import org.entel.lector_tcp.infra.adapter.input.entity.TblLabDispositivosotro;
import org.entel.lector_tcp.infra.adapter.input.projection.DispositivoNeverasProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblLabDispositivosotroRepository extends JpaRepository<TblLabDispositivosotro, Integer> {
  DispositivoNeverasProjection findByLabVchImei(String imei);
}