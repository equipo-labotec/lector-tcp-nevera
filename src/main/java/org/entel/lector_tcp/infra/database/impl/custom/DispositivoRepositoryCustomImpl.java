package org.entel.lector_tcp.infra.database.impl.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.entel.lector_tcp.domain.dto.TransferenciaDTO;
import org.entel.lector_tcp.infra.database.entity.Dispositivo;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional(readOnly = false)
@AllArgsConstructor
public class DispositivoRepositoryCustomImpl implements DispositivoRepositoryJPA {
    private static Logger LOG = LoggerFactory.getLogger(DispositivoRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public TransferenciaDTO registrarMedicionNevera(String imei, String estado, double bateria_interna, int movimiento, String fecha_hora, 
    		                                       double bateria_externa, double signal, int geo , String latitud , 
    		                                       String longitud, int power ) {
        TransferenciaDTO lectura = new TransferenciaDTO();
        List<TransferenciaDTO> lecturaL = new ArrayList<TransferenciaDTO>();
        StoredProcedureQuery bspL = em.createNamedStoredProcedureQuery(Dispositivo.NamedQuery_registrarMedicionNeveraTransferenciav2);
        bspL.setParameter("_imei", imei);
        bspL.setParameter("_status", estado);
        bspL.setParameter("_bateria_int", bateria_interna);
        bspL.setParameter("_movimiento", movimiento);
        bspL.setParameter("_fecha_hora", fecha_hora);
        bspL.setParameter("_bateria_ext", bateria_externa);
        bspL.setParameter("_signal", signal);
        bspL.setParameter("_geo", geo);
        bspL.setParameter("_latitud", latitud);
        bspL.setParameter("_longitud", longitud);
        bspL.setParameter("_power", power);
        bspL.execute();
        LOG.info("Se ejecuto procedimiento registrarMedicionNevera optimizado");

        lecturaL = bspL.unwrap(org.hibernate.query.Query.class).setResultTransformer(Transformers.aliasToBean(TransferenciaDTO.class)).getResultList();
        em.close();
        if (lecturaL.size() > 0) {
            lectura = lecturaL.get(0);
        }
        return lectura;
    }

    @Override
    public TransferenciaDTO actualizarDispositivoNevera(String latitud, String longitud, int satelite, int estado, String imei) {
        TransferenciaDTO lectura = new TransferenciaDTO();
        StoredProcedureQuery bspL = em.createNamedStoredProcedureQuery(Dispositivo.NamedQuery_actualizarDispositivoNevera);
        bspL.setParameter("_lat", latitud);
        bspL.setParameter("_lon", longitud);
        bspL.setParameter("_sat", satelite);
        bspL.setParameter("_status", estado);
        bspL.setParameter("_imei", imei);
        bspL.execute();
        LOG.info("Se ejecuto procedimiento actualizarDispositivoNevera");

        List<TransferenciaDTO> lecturaL = bspL.unwrap(org.hibernate.query.Query.class).setResultTransformer(Transformers.aliasToBean(TransferenciaDTO.class)).getResultList();
        em.close();
        if (lecturaL.size() > 0) {
            lectura = lecturaL.get(0);
        }
        return lectura;
    }


}
