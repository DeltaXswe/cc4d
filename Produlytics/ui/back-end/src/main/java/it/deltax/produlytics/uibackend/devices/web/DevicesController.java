package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedDevicesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Il controller per le richieste effettuate relative alle macchine
 */
@RestController
@RequestMapping("/devices")
public class DevicesController {
    private final GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase;


    /**
     * Il costruttore
     * @param getUnarchivedDevicesUseCase l'interfaccia per il caso d'uso di ottenimento delle macchine non archiviate
     */
    public DevicesController(GetUnarchivedDevicesUseCase getUnarchivedDevicesUseCase) {
        this.getUnarchivedDevicesUseCase = getUnarchivedDevicesUseCase;
    }

    /**
     * Riceve le chiamate all'endpoint REST per l'ottenimento delle macchine non archiviate
     * @return lo stato HTTP e la lista delle macchine non archiviate, ognuna con id e nome
     */
    @GetMapping("")
    public ResponseEntity<Iterable<TinyDevice>> getUnarchivedDevices() {
        return ResponseEntity.ok(this.getUnarchivedDevicesUseCase.getUnarchivedDevices());
    }
}
