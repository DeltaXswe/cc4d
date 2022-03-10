package it.deltax.produlytics.uibackend.common.machine.web;

import it.deltax.produlytics.uibackend.common.machine.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.ListAllMachinesUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
public class MachinesController {

    private final ListAllMachinesUseCase useCase;

    public MachinesController(ListAllMachinesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("")
    Iterable<MachineLight> listAllMachines() {
        return useCase.listAll();
    }
}
