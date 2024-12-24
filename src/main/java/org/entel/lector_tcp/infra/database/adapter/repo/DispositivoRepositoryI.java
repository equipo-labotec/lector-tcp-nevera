package org.entel.lector_tcp.infra.database.adapter.repo;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.input.repository.DispositivoRepository;
import org.entel.lector_tcp.domain.dto.TransferenciaDTO;
import org.entel.lector_tcp.infra.database.impl.custom.DispositivoRepositoryJPA;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DispositivoRepositoryI implements DispositivoRepository {
    private final DispositivoRepositoryJPA dispositivoStoreProcedureRepository;
    @Override
    public TransferenciaDTO registrarMedicionNevera(
            String imei ,
            String estado,
            double bateria_interna,
            int movimiento,
            String fecha_hora,
            double bateria_externa,
            double signal, int geo,
            String latitud,
            String longitud,
            int power) {
        return dispositivoStoreProcedureRepository.registrarMedicionNevera(
                imei,
                estado,
                bateria_interna,
                movimiento,
                fecha_hora,
                bateria_externa,
                signal,
                geo,
                latitud,
                longitud,
                power
        );
    }

    @Override
    public TransferenciaDTO actualizarDispositivoNevera(
            String latitud,
            String longitud,
            int satelite,
            int estado,
            String imei) {
        return dispositivoStoreProcedureRepository.actualizarDispositivoNevera(
                latitud,
                longitud,
                satelite,
                estado,
                imei
        );
    }

}
