package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListAllCharacteristicsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characteristics")
public class CharacteristicsController {

    @Autowired
    private ListAllCharacteristicsUseCase useCase;

    @GetMapping("")
    Iterable<CharacteristicLight> listAllCharacteristics() {
        return useCase.listAllCharacteristics();
    }
}
