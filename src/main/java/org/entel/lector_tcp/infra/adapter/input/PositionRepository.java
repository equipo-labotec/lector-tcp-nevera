package org.entel.lector_tcp.infra.adapter.input;

import org.entel.lector_tcp.domain.models.Position;

import java.util.List;

public interface PositionRepository {
    // Guarda una posición para un IMEI específico
    void savePosition(String imei, Position position);

    // Recupera todas las posiciones de un IMEI específico
    List<Position> getPositionsByImei(String imei);

    // Recupera todos los IMEIs con posiciones guardadas
    List<String> getAllImeis();

    // Limpia todas las posiciones de un IMEI específico
    void clearPositions(String imei);

    // Limpia todas las posiciones guardadas en el repositorio
    void clearAll();
}
