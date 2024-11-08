package org.entel.lector_tcp.domain.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    private Long id;
    private String protocolVersion;
    private String imei;
    private String password;
    private String crc16Login;

}
