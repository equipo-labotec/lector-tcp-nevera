package org.entel.lector_tcp.infra.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_dispositivosotros")
public class DispositivosOtros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_int_codigo", nullable = false)
    private Integer id;

    @Column(name = "lab_vch_master", nullable = false, length = 4)
    private String labVchMaster;

    @Column(name = "lab_int_locacion", nullable = false)
    private Integer labIntLocacion;

    @Column(name = "lab_vch_imei", nullable = false, length = 25)
    private String labVchImei;

    @Column(name = "lab_dbl_valorminimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblValorminimo;

    @Column(name = "lab_dbl_valormaximo", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblValormaximo;

    @Column(name = "lab_int_estado", nullable = false)
    private Integer labIntEstado;

    @Column(name = "lab_dbl_minimoporc", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblMinimoporc;

    @Column(name = "lab_dbl_capacidadkg", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblCapacidadkg;

    @ColumnDefault("1")
    @Column(name = "lab_int_mqtt", nullable = false)
    private Integer labIntMqtt;

    @ColumnDefault("0")
    @Column(name = "lab_int_mqtt_suscriber_status")
    private Integer labIntMqttSuscriberStatus;

    @ColumnDefault("'''Sin ruta MQTT'''")
    @Column(name = "lab_vch_url_mqtt", length = 1000)
    private String labVchUrlMqtt;

    @Column(name = "lab_vch_tipo", nullable = false, length = 100)
    private String labVchTipo;

    @ColumnDefault("'sin datos'")
    @Column(name = "lab_vch_latitud", nullable = false, length = 120)
    private String labVchLatitud;

    @ColumnDefault("'sin datos'")
    @Column(name = "lab_vch_longitud", nullable = false, length = 120)
    private String labVchLongitud;

    @ColumnDefault("0.00")
    @Column(name = "lab_dbl_valorminimo_hum", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblValorminimoHum;

    @ColumnDefault("0.00")
    @Column(name = "lab_dbl_valormaximo_hum", nullable = false, precision = 10, scale = 2)
    private BigDecimal labDblValormaximoHum;

    @ColumnDefault("'AUTOMATICO'")
    @Column(name = "lab_vch_modo", length = 250)
    private String labVchModo;

    @ColumnDefault("0.00")
    @Column(name = "lab_dbl_prom_temp", precision = 10, scale = 2)
    private BigDecimal labDblPromTemp;

    @ColumnDefault("0.00")
    @Column(name = "lab_dbl_prom_hum", precision = 10, scale = 2)
    private BigDecimal labDblPromHum;

    @ColumnDefault("'0'")
    @Column(name = "lab_vch_imei_chip", length = 250)
    private String labVchImeiChip;

    @ColumnDefault("'0'")
    @Column(name = "lab_vch_valvula", length = 50)
    private String labVchValvula;

    @ColumnDefault("1")
    @Column(name = "lab_int_status_dis_conf")
    private Integer labIntStatusDisConf;

    @ColumnDefault("'-----------------------'")
    @Column(name = "lab_vch_cod_nevera", length = 250)
    private String labVchCodNevera;

    @ColumnDefault("'sin distribuidor'")
    @Column(name = "lab_vch_distribuidor", length = 250)
    private String labVchDistribuidor;

    @ColumnDefault("'no actualizado'")
    @Column(name = "lab_vch_usuario", nullable = false, length = 100)
    private String labVchUsuario;

    @ColumnDefault("'sin actualizacion'")
    @Column(name = "lab_vch_fecha_usuario", nullable = false, length = 100)
    private String labVchFechaUsuario;

    @ColumnDefault("'1'")
    @Column(name = "lab_vch_version", nullable = false, length = 250)
    private String labVchVersion;

    @ColumnDefault("'1'")
    @Column(name = "lab_int_idcliente", nullable = false, length = 30)
    private String labIntIdcliente;

    @Column(name = "lab_dat_fechaintalacion")
    private LocalDate labDatFechaintalacion;

    @Column(name = "lab_vch_iccid", nullable = false, length = 35)
    private String labVchIccid;

    @Column(name = "lab_int_satelite", nullable = false)
    private Integer labIntSatelite;

    @Column(name = "lab_vch_latitudinst", nullable = false, length = 120)
    private String labVchLatitudinst;

    @Column(name = "lab_vch_longitudinst", nullable = false, length = 120)
    private String labVchLongitudinst;

    @ColumnDefault("1")
    @Column(name = "lab_int_status", nullable = false)
    private Byte labIntStatus;

    @Column(name = "lab_vch_refstatus", length = 30)
    private String labVchRefstatus;

    @ColumnDefault("100")
    @Column(name = "lab_int_radio", nullable = false)
    private Integer labIntRadio;

    @Column(name = "lab_dat_ultimaact")
    private Instant labDatUltimaact;

    @ColumnDefault("0")
    @Column(name = "lab_int_ultimoid", nullable = false)
    private Integer labIntUltimoid;

    @ColumnDefault("''")
    @Column(name = "lab_inst_ubigeo", length = 10)
    private String labInstUbigeo;

    @ColumnDefault("''")
    @Column(name = "lab_censo_latitud", length = 120)
    private String labCensoLatitud;

    @ColumnDefault("''")
    @Column(name = "lab_censo_longitud", length = 120)
    private String labCensoLongitud;

    @ColumnDefault("''")
    @Column(name = "lab_censo_ubigeo", length = 10)
    private String labCensoUbigeo;

    @ColumnDefault("0")
    @Column(name = "lab_vch_contador_desconexiones", nullable = false)
    private Integer labVchContadorDesconexiones;

}