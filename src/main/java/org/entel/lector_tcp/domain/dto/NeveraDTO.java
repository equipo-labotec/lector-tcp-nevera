package org.entel.lector_tcp.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NeveraDTO {
    private String latitud;
    private String longitud;
    private int satelite;
    private String fecha;
    private int movimiento;
    private int status;
    private String status_details;
    private double bateria_interna;
    private double bateria_externa;
    private double signal;
    private int geo;
}
