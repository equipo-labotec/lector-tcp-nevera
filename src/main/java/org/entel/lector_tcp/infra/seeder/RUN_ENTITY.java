package org.entel.lector_tcp.infra.seeder;

import org.entel.lector_tcp.infra.adapter.input.entity.DeviceEntity;
import org.entel.lector_tcp.infra.adapter.input.repository.DeviceEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RUN_ENTITY implements CommandLineRunner {

    private final DeviceEntityRepository deviceEntityRepository;

    public RUN_ENTITY(DeviceEntityRepository deviceEntityRepository) {
        this.deviceEntityRepository = deviceEntityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (deviceEntityRepository.count() == 0){
            // Crear los objetos DeviceEntity con base en la información de la imagen
            DeviceEntity device1 = new DeviceEntity(
                    null, // El ID se generará automáticamente
                    "D3W-942", // Version del protocolo (puedes ajustar según sea necesario)
                    "868695060089495", // IMEI del dispositivo
                    "password123", // Password (ejemplo, ajústalo según sea necesario)
                    "crc16_value" // CRC16 Login (ejemplo, ajústalo según sea necesario)
            );

            // Guardar los objetos en la base de datos
            deviceEntityRepository.save(device1);

            System.out.println("Devices inicializados en la base de datos.");
        }

    }
}
