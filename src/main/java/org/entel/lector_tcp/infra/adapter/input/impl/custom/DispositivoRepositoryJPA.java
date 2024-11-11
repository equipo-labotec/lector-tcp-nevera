package org.entel.lector_tcp.infra.adapter.input.impl.custom;



import org.entel.lector_tcp.domain.dto.TransferenciaDTO;

public interface DispositivoRepositoryJPA {



    //Neveras
    TransferenciaDTO registrarMedicionNevera(String imei, String estado, double bateria_interna, int movimiento,
                                             String fecha_hora, double bateria_externa, double signal, int geo , String latitud , String longitud ,
                                             int power);

    TransferenciaDTO actualizarDispositivoNevera(String latitud, String longitud, int satelite, int estado, String imei);
}
