package org.entel.lector_tcp.domain.common;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DispositivoMQTT {
    private int dis_codigo;

    private String dis_imei;

    private String dis_url;

    private int dis_estado;
    //private int dis_parent;

    private int dis_mqtt;

    private int dis_mqtt_suscriber;

    private String dis_tipo;

    private String dis_latitud;

    private String dis_longitud;
}
