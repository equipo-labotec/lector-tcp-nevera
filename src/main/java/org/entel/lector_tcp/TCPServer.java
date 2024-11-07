package org.entel.lector_tcp;

import org.entel.lector_tcp.protocol.WialonProtocolDecoder;
import org.entel.lector_tcp.decoder.Position;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TCPServer {

    private static final int PORT = 12345;
    private static final Set<String> authorizedIMEIs = new HashSet<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private final Map<String, List<Position>> imeiPositions = new HashMap<>();

    public static void main(String[] args) {
        initializeAuthorizedIMEIs();
        TCPServer server = new TCPServer();
        server.startServer();
    }

    // Inicializa los IMEIs autorizados al servidor
    private static void initializeAuthorizedIMEIs() {
        authorizedIMEIs.add("868695060089495");
        authorizedIMEIs.add("123456789012345");
        authorizedIMEIs.add("868695060089490");
    }

    // Inicia el servidor TCP y acepta conexiones de clientes
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor TCP escuchando en el puerto " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
                new Thread(() -> handleClientConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Gestiona la conexión con cada cliente individualmente
    private void handleClientConnection(Socket clientSocket) {
        WialonProtocolDecoder decoder = new WialonProtocolDecoder();
        String imei = null;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            imei = authenticateClient(in, out); // Autentica al cliente y obtiene el IMEI

            if (imei != null) {
                processClientData(in, out, decoder, imei); // Procesa los datos del cliente después de autenticación
            } else {
                rejectUnauthorizedClient(out); // Rechaza el cliente no autorizado
            }

        } catch (IOException e) {
            System.out.println("Error al comunicarse con el cliente: " + e.getMessage());
        } finally {
            displayPositionMap();
            closeClientSocket(clientSocket);
        }
    }

    // Autentica al cliente y devuelve el IMEI si está autorizado
    private String authenticateClient(BufferedReader in, PrintWriter out) throws IOException {
        String inputLine = in.readLine();
        logReceivedMessage(inputLine);

        String imei = extractIMEI(inputLine);
        if (imei != null && isAuthorizedIMEI(imei)) {
            out.println("#AL#1");
            logSentMessage("#AL#1");
            System.out.println("Cliente autenticado con IMEI: " + imei);
            return imei;
        }
        return null;
    }

    // Procesa los datos del cliente autenticado y responde a cada paquete recibido
    private void processClientData(BufferedReader in, PrintWriter out, WialonProtocolDecoder decoder, String imei) throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            logReceivedMessage(inputLine);

            try {
                Position position = (Position) decoder.decode(inputLine);
                if (position != null) {
                    System.out.println("Datos decodificados para " + imei + ": " + position);
                    storePositionData(imei, position); // Almacena la posición en el mapa

                    String dataResponse = "#AD#1";
                    out.println(dataResponse); // Responde con #AD#1 para confirmar recepción
                    logSentMessage(dataResponse);
                } else {
                    System.out.println("Error al decodificar el mensaje: " + inputLine);
                }
            } catch (Exception e) {
                System.out.println("Error en la decodificación: " + e.getMessage());
            }
        }
    }

    // Rechaza al cliente si no está autorizado
    private void rejectUnauthorizedClient(PrintWriter out) {
        String response = "No autenticado: IMEI no autorizado";
        out.println(response);
        logSentMessage(response);
        System.out.println("Cliente no autorizado");
    }

    // Almacena la posición en el mapa para el IMEI correspondiente
    private void storePositionData(String imei, Position position) {
        imeiPositions.computeIfAbsent(imei, k -> new ArrayList<>()).add(position);
    }

    // Comprueba si un IMEI está autorizado
    private boolean isAuthorizedIMEI(String imei) {
        return authorizedIMEIs.contains(imei);
    }

    // Extrae el IMEI del mensaje si está presente
    private String extractIMEI(String message) {
        if (message != null && message.contains(";")) {
            return message.split(";")[0].replace("#L#", "");
        }
        return null;
    }

    // Cierra el socket del cliente de manera segura
    private void closeClientSocket(Socket clientSocket) {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                System.out.println("Socket del cliente cerrado.");
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket del cliente: " + e.getMessage());
        }
    }

    // Registra un mensaje recibido junto con una marca de tiempo
    private void logReceivedMessage(String message) {
        String timestamp = dateFormat.format(new Date());
        System.out.println(timestamp + ": [<<<] " + message);
    }

    // Registra un mensaje enviado junto con una marca de tiempo
    private void logSentMessage(String message) {
        String timestamp = dateFormat.format(new Date());
        System.out.println(timestamp + ": [>>>] " + message);
    }

    // Muestra el contenido del mapa de posiciones en formato de tabla
    private void displayPositionMap() {
        System.out.println("Contenido del mapa de posiciones:");
        for (Map.Entry<String, List<Position>> entry : imeiPositions.entrySet()) {
            String imei = entry.getKey();
            List<Position> positions = entry.getValue();

            System.out.println("IMEI: " + imei);
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-20s %-20s %-20s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-20s %-10s %-20s%n",
                    "|Protocol", "|Server Time", "|Device Time", "|Fix Time", "|Outdated", "|Valid", "|Latitude", "|Longitude", "|Altitude", "|Speed", "|Course", "|Address", "|Accuracy", "|Geofence IDs");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

            for (Position position : positions) {
                System.out.printf("%-10s %-20s %-20s %-20s %-10s %-10s %-10.6f %-10.6f %-10.2f %-10.2f %-10.2f %-20s %-10.2f %-20s%n",
                        position.getProtocol(),
                        position.getServerTime(),
                        position.getDeviceTime(),
                        position.getFixTime(),
                        position.isOutdated(),
                        position.isValid(),
                        position.getLatitude(),
                        position.getLongitude(),
                        position.getAltitude(),
                        position.getSpeed(),
                        position.getCourse(),
                        position.getAddress(),
                        position.getAccuracy(),
                        position.getGeofenceIds() != null ? position.getGeofenceIds().toString() : "N/A");
            }
            System.out.println();
        }
    }
}
