package org.entel.lector_tcp.infra.adapter.input;

import org.entel.lector_tcp.domain.models.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPositionRepository implements PositionRepository {

    private final Map<String, List<Position>> imeiPositions = new HashMap<>();

    @Override
    public void savePosition(String imei, Position position) {
        imeiPositions.computeIfAbsent(imei, k -> new ArrayList<>()).add(position);
    }

    @Override
    public List<Position> getPositionsByImei(String imei) {
        return imeiPositions.getOrDefault(imei, new ArrayList<>());
    }

    @Override
    public List<String> getAllImeis() {
        return new ArrayList<>(imeiPositions.keySet());
    }

    @Override
    public void clearPositions(String imei) {
        imeiPositions.remove(imei);
    }

    @Override
    public void clearAll() {
        imeiPositions.clear();
    }
}
