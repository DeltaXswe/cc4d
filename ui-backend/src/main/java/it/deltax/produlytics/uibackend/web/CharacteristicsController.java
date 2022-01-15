package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListCharacteristicsByMachineUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characteristics")
public class CharacteristicsController {

    private final ListCharacteristicsByMachineUseCase useCase;

    public CharacteristicsController(ListCharacteristicsByMachineUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{machine}")
    Iterable<CharacteristicLight> listAllCharacteristics(@PathVariable("machine") long machine) {
        return useCase.listCharacteristicsByMachine(machine);
    }
}
