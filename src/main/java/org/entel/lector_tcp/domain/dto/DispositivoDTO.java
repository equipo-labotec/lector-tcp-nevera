package org.entel.lector_tcp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispositivoDTO {
    private int dis_codigo;
    private String dis_empresa;
    private int dis_cod_locacion;
    private String dis_nombre_locacion;
    private String dis_imei;
    private int dis_estado;
    private int dis_mqtt;
    private String dis_tipo;
    private String dis_latitud;
    private String dis_longitud;
}
