package org.entel.lector_tcp.infra.config;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.logic.DeviceServiceI;
import org.entel.lector_tcp.app.logic.NeverasDataPacketServiceI;
import org.entel.lector_tcp.app.mapper.NestleDataPacketMapper;
import org.entel.lector_tcp.app.ports.input.repository.DeviceRepository;
import org.entel.lector_tcp.app.ports.input.repository.DispositivoRepository;
import org.entel.lector_tcp.app.ports.input.repository.NeverasRepository;
import org.entel.lector_tcp.app.ports.out.DeviceService;
import org.entel.lector_tcp.app.ports.out.NeverasDataPacketService;
import org.entel.lector_tcp.app.protocol.WialonProtocolDecoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
    private final NeverasRepository neverasRepository;
    private final DeviceRepository deviceRepository;
    private final DispositivoRepository dispositivoRepository;
    @Bean(name = "DeviceServices")
    @Transactional
    DeviceService deviceService (){
        return new DeviceServiceI(
                dispositivoRepository,
                deviceRepository,
                neverasRepository
        );
    }
    @Bean(name = "NeverasDataPacketService")
    @Transactional
    NeverasDataPacketService neverasDataPacketService(){
        return new NeverasDataPacketServiceI(
                neverasRepository
        );
    }
}
