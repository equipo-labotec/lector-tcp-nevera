package org.entel.lector_tcp.infra.database.entity;



import jakarta.persistence.*;
import lombok.*;
import org.entel.lector_tcp.domain.common.DispositivoMQTT;
import org.entel.lector_tcp.domain.dto.DispositivoMQTTDTO;
import org.entel.lector_tcp.domain.common.Operation;
import org.entel.lector_tcp.domain.dto.TransferenciaDTO;

import java.io.Serializable;
import java.security.Timestamp;


@Entity
@Table(name = "dispositivo")

@SqlResultSetMapping(name = "MTransferenciaDTO", classes =
@ConstructorResult(targetClass = TransferenciaDTO.class, columns = {
        @ColumnResult(name = "status", type = Integer.class),
        @ColumnResult(name = "msg", type = String.class)}))

@SqlResultSetMapping(name = "MListaMQTTDispositivos", classes =
@ConstructorResult(targetClass = DispositivoMQTTDTO.class, columns = {
        @ColumnResult(name = "dispositivo_codigo", type = Integer.class),
        @ColumnResult(name = "dispositivo_imei", type = String.class),
        @ColumnResult(name = "dispositivo_mqtt", type = Integer.class),
        @ColumnResult(name = "dispositivo_mqtt_suscriber_status", type = Integer.class)
}))
@SqlResultSetMapping(name = "MActualizarMQTTDispositivo", classes =
@ConstructorResult(targetClass = TransferenciaDTO.class, columns = {
        @ColumnResult(name = "status", type = Integer.class),
        @ColumnResult(name = "msg", type = String.class)}))
@SqlResultSetMapping(name = "MActualizarMQTTDispositivoNeveras", classes =
@ConstructorResult(targetClass = TransferenciaDTO.class, columns = {
        @ColumnResult(name = "status", type = Integer.class),
        @ColumnResult(name = "msg", type = String.class)}))

@SqlResultSetMapping(name = "MDispositivoMQTTDTO", classes =
@ConstructorResult(targetClass = DispositivoMQTT.class, columns = {
        @ColumnResult(name = "dis_codigo", type = Integer.class),
        @ColumnResult(name = "dis_imei", type = String.class),
        @ColumnResult(name = "dis_url", type = String.class),
        @ColumnResult(name = "dis_estado", type = Integer.class),
        //@ColumnResult(name = "dis_parent", type = Integer.class),
        @ColumnResult(name = "dis_mqtt", type = Integer.class),
        @ColumnResult(name = "dis_mqtt_suscriber", type = Integer.class),
        @ColumnResult(name = "dis_tipo", type = String.class),
        @ColumnResult(name = "dis_latitud", type = String.class),
        @ColumnResult(name = "dis_longitud", type = String.class)}))

@SqlResultSetMapping(name = "MDispositivoOperacion", classes =
@ConstructorResult(targetClass = Operation.class, columns = {
        @ColumnResult(name = "msg", type = String.class),
        @ColumnResult(name = "status", type = Integer.class)}))

@SqlResultSetMapping(name = "MDispositivoOperacion2", classes =
@ConstructorResult(targetClass = TransferenciaDTO.class, columns = {
        @ColumnResult(name = "msg", type = String.class),
        @ColumnResult(name = "status", type = Integer.class)}))


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_actualizarEstadoModificado,
                resultSetMappings = {
                        "MActualizarMQTTDispositivo"}, procedureName = "sp_entel_actualizacion_dispositivo_configuracion_geocercas", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_imei", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_estado", type = Integer.class)}),
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_listaDispositivosMQTT,
                resultSetMappings = {"MListaMQTTDispositivos"}, procedureName = "sp_entel_lista_dispositivos_neveras_mqtt_modificado", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_locacionid", type = Integer.class),
        }),
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_actualizarDispositivoMQTT,
                resultSetMappings = {
                        "MActualizarMQTTDispositivo"}, procedureName = "sp_entel_actualizar_mqtt_suscriber_status", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_status", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_url", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_imei", type = String.class)}),
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_actualizarDispositivoNevera,
                resultSetMappings = {
                        "MActualizarMQTTDispositivoNeveras"}, procedureName = "sp_entel_actualizar_mqtt_dispositivo_neveras", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_lat", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_lon", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_sat", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_status", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_imei", type = String.class)}),
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_registrarMedicionNeveraTransferencia,
                resultSetMappings = {
                        "MTransferenciaDTO"}, procedureName = "sp_entel_registrar_medicion_neveras", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_imei", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_status", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_bateria_int", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_movimiento", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_fecha_hora", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_bateria_ext", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_signal", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_geo", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_latitud", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_longitud", type = String.class)
        }),
        @NamedStoredProcedureQuery(name = Dispositivo.NamedQuery_registrarMedicionNeveraTransferenciav2,
                resultSetMappings = {
                        "MTransferenciaDTO"}, procedureName = "sp_entel_registrar_medicion_neverasv2", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_imei", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_status", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_bateria_int", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_movimiento", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_fecha_hora", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_bateria_ext", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_signal", type = Double.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_geo", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_latitud", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_longitud", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "_power", type = Integer.class)
        }),



})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dispositivo implements Serializable {

    public static final String NamedQuery_registrarMedicionNeveraTransferencia = "registrarMedicionNeveraTransferencia";
    public static final String NamedQuery_registrarMedicionNeveraTransferenciav2= "registrarMedicionNeveraTransferenciav2";
    public static final String NamedQuery_listaDispositivosMQTT = "listaDispositivosMQTT";
    public static final String NamedQuery_actualizarDispositivoMQTT = "actualizarDispositivoMQTT";
    public static final String NamedQuery_actualizarDispositivoNevera = "actualizarDispositivoNevera";
    public static final String NamedQuery_actualizarEstadoModificado = "actualizarEstadoModificado";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int idd;
    @Column(name = "master")
    private String masterd;
    @Column(name = "locacion")
    private String locaciond;
    @Column(name = "sensor_id")
    private String sensor_idd;
    @Column(name = "nombre")
    private String nombred;
    @Column(name = "maxtemp")
    private String maxtempd;
    @Column(name = "mintemp")
    private String mintempd;
    @Column(name = "maxhume")
    private String maxhume;
    @Column(name = "minhume")
    private String minhume;
    @Column(name = "status")
    private String statusd;
    @Column(name = "last_update")
    private Timestamp last_updated;

}
