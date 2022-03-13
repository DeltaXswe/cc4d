package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ListAllDevicesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
public class MachinesController {

    private final ListAllDevicesUseCase useCase;

    public MachinesController(ListAllDevicesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("")
    Iterable<MachineLight> listAllMachines() {
        return useCase.listAll();
    }
}
