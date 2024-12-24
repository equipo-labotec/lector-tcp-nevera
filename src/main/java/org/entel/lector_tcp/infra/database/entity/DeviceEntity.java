package org.entel.lector_tcp.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "version_protocolo",nullable = false)
    private String protocolVersion;
    @Column(name = "imei",nullable = false)
    private String imei;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "crc16_login",nullable = false)
    private String crc16Login;
}
