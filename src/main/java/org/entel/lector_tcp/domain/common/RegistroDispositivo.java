package org.entel.lector_tcp.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDispositivo {
    private int loc_id;
    private String imei;
    private String empresa_id;
    private String tipo;
}
