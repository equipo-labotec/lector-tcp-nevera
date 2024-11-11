package org.entel.lector_tcp.domain.dto;

public class ReservorioDTO {
    private String fecha;
    private String hora;
    private String signal;
    private String longitude;
    private String volume;
    private String warning;

    public ReservorioDTO(String fecha, String hora, String signal, String longitude, String volume, String warning) {
        this.fecha = fecha;
        this.hora = hora;
        this.signal = signal;
        this.longitude = longitude;
        this.volume = volume;
        this.warning = warning;
    }

    public ReservorioDTO() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}
