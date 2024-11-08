package org.entel.lector_tcp.domain.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NeverasDataPacket {
    private Long id;
    private Device deviceCredentials;
    // Datos del paquete de transmisión
    private String date;
    private String time;
    private String latitude;
    private String longitude;
    private int speed;
    private int course;
    private int height;
    private int satellites;
    private double hdop;
    private int inputs;
    private String outputs;
    private double adc;
    private String ibutton;
    private String crc16Data;
    // Parámetros específicos de eventos y módem
    private int pwr; // Estado de energía externa (0: desconectado, 1: conectado)
    private int mov; // Estado de movimiento (0: detenido, 1: en movimiento)
    private int geo; // Estado de geocerca (0: dentro, 1: fuera)
    private int csq; // Nivel de señal de red celular
    private String iccid; // ICCID de la SIM
    private String firmwareVersion;
}
