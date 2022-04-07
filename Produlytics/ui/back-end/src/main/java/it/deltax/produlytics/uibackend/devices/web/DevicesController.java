package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DevicesController {

    private final GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase;

    public DevicesController(GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase) {
        this.getUnarchivedDevicesUseCase = getUnarchivedDevicesUseCase;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<TinyDevice>> getUnarchivedDevices() {
        return ResponseEntity.ok(getUnarchivedDevicesUseCase.getUnarchivedDevices());
    }


}
