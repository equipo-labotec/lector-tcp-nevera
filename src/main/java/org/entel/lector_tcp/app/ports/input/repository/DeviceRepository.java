package org.entel.lector_tcp.app.ports.input.repository;

import org.entel.lector_tcp.domain.models.Device;

import java.util.Optional;

public interface DeviceRepository {

    Optional<Device> getDeviceByImei(String imei);
}
