package org.entel.lector_tcp.infra.config;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.logic.DeviceServiceI;
import org.entel.lector_tcp.app.logic.NeverasDataPacketServiceI;
import org.entel.lector_tcp.app.mapper.NestleDataPacketMapper;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.app.ports.out.DeviceService;
import org.entel.lector_tcp.app.ports.out.NeverasDataPacketService;
import org.entel.lector_tcp.app.protocol.WialonProtocolDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
    private final NeverasRepository neverasRepository;
    private final DeviceRepository deviceRepository;
    @Bean(name = "DeviceServices")
    DeviceService deviceService (){
        return new DeviceServiceI(
                deviceRepository,
                neverasRepository
        );
    }
    @Bean(name = "NeverasDataPacketService")
    NeverasDataPacketService neverasDataPacketService(){
        return new NeverasDataPacketServiceI(
                neverasRepository
        );
    }
}
