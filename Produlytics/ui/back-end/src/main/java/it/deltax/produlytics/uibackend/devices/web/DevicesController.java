package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DevicesController {

    private final GetUnarchivedDevicesUseCase useCase;

    public DevicesController(GetUnarchivedDevicesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("")
    Iterable<UnarchivedDevice> getDevices() {
        return useCase.getAll();
    }
}
