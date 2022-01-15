package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllMachinesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/machines")
public class MachinesController {

    private ListAllMachinesUseCase useCase;

    public MachinesController(ListAllMachinesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("")
    Iterable<MachineLight> listAllMachines() {
        return useCase.listAllMachines();
    }
}
