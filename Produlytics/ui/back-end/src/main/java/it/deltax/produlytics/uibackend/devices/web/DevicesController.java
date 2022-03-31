package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DevicesController {

    private final GetUnarchivedDevicesUseCase useCase;

    public DevicesController(@Qualifier("getUnarchivedDevicesUseCase") GetUnarchivedDevicesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("")
    Iterable<TinyDevice> getDevices() {
        return useCase.getAll();
    }



}
