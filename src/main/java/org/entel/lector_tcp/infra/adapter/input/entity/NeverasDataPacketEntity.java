package org.entel.lector_tcp.infra.adapter.input.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeverasDataPacketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity deviceCredentials;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "speed")
    private int speed;

    @Column(name = "course")
    private int course;

    @Column(name = "height")
    private int height;

    @Column(name = "satellites")
    private int satellites;

    @Column(name = "hdop")
    private double hdop;

    @Column(name = "inputs")
    private int inputs;

    @Column(name = "outputs")
    private String outputs;

    @Column(name = "adc")
    private double adc;

    @Column(name = "ibutton")
    private String ibutton;

    @Column(name = "crc16_data")
    private String crc16Data;

    // Parámetros específicos de eventos y módem
    @Column(name = "pwr")
    private int pwr; // Estado de energía externa

    @Column(name = "mov")
    private int mov; // Estado de movimiento

    @Column(name = "geo")
    private int geo; // Estado de geocerca

    @Column(name = "csq")
    private int csq; // Nivel de señal de red celular

    @Column(name = "iccid")
    private String iccid; // ICCID de la SIM

    @Column(name = "firmware_version")
    private String firmwareVersion;
}

