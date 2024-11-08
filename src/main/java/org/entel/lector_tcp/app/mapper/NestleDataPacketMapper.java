package org.entel.lector_tcp.app.mapper;

import org.entel.lector_tcp.domain.models.Position;
import org.entel.lector_tcp.domain.models.NeverasDataPacket;

import static org.entel.lector_tcp.app.keys.KEYS.KEY_GEO;
import static org.entel.lector_tcp.app.keys.KEYS.KEY_MOV;

public class NestleDataPacketMapper {

    public NeverasDataPacket map(Position position) {
        NeverasDataPacket packet = new NeverasDataPacket();

        // Mapeo de los campos de tiempo y ubicación
        packet.setDate(position.getServerTime().toString());
        packet.setTime(position.getDeviceTime() != null ? position.getDeviceTime().toString() : null);
        packet.setLatitude(String.valueOf(position.getLatitude()));
        packet.setLongitude(String.valueOf(position.getLongitude()));
        packet.setSpeed((int) position.getSpeed());
        packet.setCourse((int) position.getCourse());
        packet.setHeight((int) position.getAltitude());

        // Mapeo de otros campos GPS
        packet.setSatellites(parseIntSafe(position.getAttributes().get("sat")));
        packet.setHdop(parseDoubleSafe(position.getAttributes().get("hdop")));

        // Mapeo de entradas y salidas digitales
        packet.setInputs(parseIntSafe(position.getAttributes().get("input")));
        packet.setOutputs(String.valueOf(position.getAttributes().getOrDefault("output", "NA")));

        // Mapeo de voltaje de la batería
        packet.setAdc(parseDoubleSafe(position.getAttributes().get("adc1")));

        // Mapeo del estado de energía externa, movimiento y geocerca
        packet.setPwr(parseIntSafe(position.getAttributes().get("pwr")));
        packet.setMov(parseIntSafe(position.getAttributes().get(KEY_MOV)));
        packet.setGeo(parseIntSafe(position.getAttributes().get(KEY_GEO)));

        // Mapeo de otros parámetros de módem
        packet.setCsq(parseIntSafe(position.getAttributes().get("csq")));

        // Mapeo del ICCID truncado a los primeros 14 dígitos sin notación científica
        Object iccidValue = position.getAttributes().getOrDefault("iccid", "NA");
        String iccidString = iccidValue instanceof Double ? String.format("%.0f", iccidValue) : iccidValue.toString();
        String truncatedIccid = iccidString.length() > 14 ? iccidString.substring(0, 14) : iccidString;
        packet.setIccid(truncatedIccid);

        packet.setFirmwareVersion(String.valueOf(position.getAttributes().getOrDefault("fw", "NA")));

        // Mapeo del CRC (si está disponible en los atributos)
        packet.setCrc16Data(String.valueOf(position.getAttributes().getOrDefault("crc16", "NA")));

        return packet;
    }

    // Métodos de utilidad para conversiones seguras
    private int parseIntSafe(Object value) {
        try {
            if (value instanceof Double) {
                return ((Double) value).intValue(); // Convierte Double a int si es necesario
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else if (value != null) {
                return Integer.parseInt(value.toString());
            }
        } catch (NumberFormatException e) {
            System.out.println("Advertencia: No se pudo parsear el valor a entero: " + value);
        }
        return 0;
    }

    private double parseDoubleSafe(Object value) {
        try {
            return value != null ? Double.parseDouble(value.toString()) : 0.0;
        } catch (NumberFormatException e) {
            System.out.println("Advertencia: No se pudo parsear el valor a double: " + value);
            return 0.0;
        }
    }
}
