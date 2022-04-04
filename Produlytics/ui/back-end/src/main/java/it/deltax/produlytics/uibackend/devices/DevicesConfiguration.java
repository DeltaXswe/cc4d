package it.deltax.produlytics.uibackend.devices;

import it.deltax.produlytics.uibackend.devices.business.GetUnarchivedDevicesService;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DevicesConfiguration {
    @Bean
    GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase(
            GetUnarchivedDevicesPort port
    ) {
        return new GetUnarchivedDevicesService(port);
    }
}
