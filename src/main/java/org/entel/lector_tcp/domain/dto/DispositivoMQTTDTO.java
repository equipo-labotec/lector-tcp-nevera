package org.entel.lector_tcp.domain.dto;

public class DispositivoMQTTDTO {
    private int dispositivo_codigo;
    private String dispositivo_imei;
    private int dispositivo_mqtt;
    private int dispositivo_mqtt_suscriber_status;

    public DispositivoMQTTDTO(int dispositivo_codigo, String dispositivo_imei, int dispositivo_mqtt, int dispositivo_mqtt_suscriber_status) {
        this.dispositivo_codigo = dispositivo_codigo;
        this.dispositivo_imei = dispositivo_imei;
        this.dispositivo_mqtt = dispositivo_mqtt;
        this.dispositivo_mqtt_suscriber_status = dispositivo_mqtt_suscriber_status;
    }

    public int getDispositivo_codigo() {
        return dispositivo_codigo;
    }

    public void setDispositivo_codigo(int dispositivo_codigo) {
        this.dispositivo_codigo = dispositivo_codigo;
    }

    public String getDispositivo_imei() {
        return dispositivo_imei;
    }

    public void setDispositivo_imei(String dispositivo_imei) {
        this.dispositivo_imei = dispositivo_imei;
    }

    public int getDispositivo_mqtt() {
        return dispositivo_mqtt;
    }

    public void setDispositivo_mqtt(int dispositivo_mqtt) {
        this.dispositivo_mqtt = dispositivo_mqtt;
    }

    public int getDispositivo_mqtt_suscriber_status() {
        return dispositivo_mqtt_suscriber_status;
    }

    public void setDispositivo_mqtt_suscriber_status(int dispositivo_mqtt_suscriber_status) {
        this.dispositivo_mqtt_suscriber_status = dispositivo_mqtt_suscriber_status;
    }

    public DispositivoMQTTDTO() {
    }
}
