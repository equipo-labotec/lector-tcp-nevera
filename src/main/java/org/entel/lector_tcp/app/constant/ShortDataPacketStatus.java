package org.entel.lector_tcp.app.constant;

public final class ShortDataPacketStatus {

    // Códigos de respuesta del servidor para el paquete #SD#
    public static final String SUCCESS = "#ASD#1";      // Paquete registrado con éxito
    public static final String STRUCTURE_ERROR = "#ASD#-1"; // Error en la estructura del paquete
    public static final String INCORRECT_TIME = "#ASD#0"; // Tiempo incorrecto
    public static final String ERROR_COORDINATES = "#ASD#10"; // Error al recibir las coordenadas
    public static final String ERROR_SPEED_COURSE_ALT = "#ASD#11"; // Error al recibir velocidad, rumbo o altitud
    public static final String ERROR_SATS = "#ASD#12";  // Error al recibir número de satélites
    public static final String CHECKSUM_ERROR = "#ASD#13"; // Error en la verificación del checksum

    // Método para obtener el mensaje correspondiente al código de respuesta
    public static String getResponseMessage(String statusCode) {
        return switch (statusCode) {
            case "#ASD#1" -> "Paquete registrado con éxito.";
            case "#ASD#-1" -> "Error en la estructura del paquete.";
            case "#ASD#0" -> "Tiempo incorrecto.";
            case "#ASD#10" -> "Error al recibir las coordenadas.";
            case "#ASD#11" -> "Error al recibir velocidad, rumbo o altitud.";
            case "#ASD#12" -> "Error al recibir número de satélites.";
            case "#ASD#13" -> "Error en la verificación del checksum.";
            default -> "Código de respuesta desconocido.";
        };
    }
}
