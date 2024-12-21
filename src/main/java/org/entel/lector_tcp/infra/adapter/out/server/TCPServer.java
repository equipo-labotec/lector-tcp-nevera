package org.entel.lector_tcp.infra.adapter.out.server;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.ports.out.DeviceService;
import org.entel.lector_tcp.domain.models.Device;
import org.entel.lector_tcp.domain.models.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@AllArgsConstructor
public class TCPServer implements CommandLineRunner {

    private static final int PORT = 5430;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final DeviceService deviceService;  // Interfaz DeviceService inyectada aquí
    private final Set<Device> authorizedIMEIs = new HashSet<>();
    private final Map<Device, List<Position>> imeiPositions = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(TCPServer.class);
    @Override
    public void run(String... args) throws Exception {
        startServer();
    }

    // Inicia el servidor TCP y acepta conexiones de clientes
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor TCP escuchando en el puerto " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Cliente conectado desde {}", clientSocket.getInetAddress());
                new Thread(() -> handleClientConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            logger.warn("Error al iniciar el servidor: {}", e.getMessage());
        }
    }

    // Gestiona la conexión con cada cliente individualmente
    private void handleClientConnection(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            Device device = authenticateClient(in, out); // Autentica y obtiene el Device completo

            if (device != null) {
                processClientData(in, out, device); // Procesa los datos usando Device
            } else {
                rejectUnauthorizedClient(out); // Rechaza el cliente no autorizado
            }

        } catch (IOException e) {
            logger.warn("Error al comunicarse con el cliente: {}", e.getMessage());
        } finally {
            displayPositionMap();
            closeClientSocket(clientSocket);
        }
    }

    // Autentica al cliente y devuelve el Device si está autorizado
    private Device authenticateClient(BufferedReader in, PrintWriter out) throws IOException {
        String inputLine = in.readLine();
        logReceivedMessage(inputLine);

        String imei = extractIMEI(inputLine);
        Device device = getOrAuthenticateDevice(imei);

        if (device != null) {
            out.println("#AL#1");
            logSentMessage("#AL#1");
            logger.info("Cliente autenticado con IMEI: {}", device.getImei());
            return device;
        }
        return null;
    }

    // Obtiene el Device desde el caché o lo autentica si no está en el caché
    private Device getOrAuthenticateDevice(String imei) {
        // Buscar el dispositivo en el conjunto autorizado
        for (Device authorizedDevice : authorizedIMEIs) {
            if (authorizedDevice.getImei().equals(imei)) {
                return authorizedDevice;
            }
        }

        // Si no está en el caché, se llama al servicio para obtener el Device
        Device device = deviceService.findByImei(imei);
        if (device != null) {
            authorizedIMEIs.add(device); // Añadir al caché
        }
        return device;
    }

    // Procesa los datos del cliente autenticado y responde a cada paquete recibido
    private void processClientData(BufferedReader in, PrintWriter out, Device device) throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            logReceivedMessage(inputLine);

            try {
                Position position = deviceService.decodePositionWialon(device, inputLine); // Uso de DeviceService para decodificar
                if (position != null) {
                    logger.info("Datos decodificados para {}: {}", device.getImei(), position);
                    storePositionData(device, position); // Almacena la posición usando Device como clave

                    String dataResponse = "#AD#1";
                    out.println(dataResponse); // Responde con #AD#1 para confirmar recepción
                    logSentMessage(dataResponse);
                } else {
                    logger.warn("Error al decodificar el mensaje: {}", inputLine);
                }
            } catch (Exception e) {
                logger.debug("Error en la decodificación");
            }
        }
    }

    // Rechaza al cliente si no está autorizado
    private void rejectUnauthorizedClient(PrintWriter out) {
        String response = "No autenticado: IMEI no autorizado";
        out.println(response);
        logSentMessage(response);
        logger.warn("Cliente no autorizado");
    }

    // Almacena la posición en el mapa para el Device correspondiente
    private void storePositionData(Device device, Position position) {
        imeiPositions.computeIfAbsent(device, k -> new ArrayList<>()).add(position);
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
                logger.debug("Socket del cliente cerrado.");
            }
        } catch (IOException e) {
            logger.warn("Error al cerrar el socket del cliente: {}", e.getMessage());
        }
    }

    // Registra un mensaje recibido junto con una marca de tiempo
    private void logReceivedMessage(String message) {
        String timestamp = dateFormat.format(new Date());
        logger.info("{}: [<<<] {}", timestamp, message);
    }

    // Registra un mensaje enviado junto con una marca de tiempo
    private void logSentMessage(String message) {
        String timestamp = dateFormat.format(new Date());
        logger.info("{}: [>>>] {}", timestamp, message);
    }

    // Muestra el contenido del mapa de posiciones en formato de tabla
    private void displayPositionMap() {
        System.out.println("Contenido del mapa de posiciones:");
        for (Map.Entry<Device, List<Position>> entry : imeiPositions.entrySet()) {
            Device device = entry.getKey();
            List<Position> positions = entry.getValue();

            logger.info("IMEI: {}", device.getImei());
            logger.info("------------------------------------------------------------------------------------------------------------------------------------------------");
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
