package org.entel.lector_tcp.app.ports.input.repository;

import org.entel.lector_tcp.domain.models.Device;

public interface DeviceRepository {

    Device getDeviceByImei(String imei);
}
