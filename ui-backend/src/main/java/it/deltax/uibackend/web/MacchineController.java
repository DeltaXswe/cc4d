package it.deltax.uibackend.web;

import it.deltax.uibackend.business.domain.MachineLight;
import it.deltax.uibackend.business.ports.in.ListAllMachinesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/macchine")
public class MacchineController {

    @Autowired
    private ListAllMachinesUseCase useCase;

    @GetMapping("")
    Iterable<MachineLight> listAllMachines() {
        return useCase.listAllMachines();
    }
}
