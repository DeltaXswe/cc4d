package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.web.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/characteristics")
public class CharacteristicsController {

    private final ListCharacteristicsByMachineUseCase listCharacteristicsByMachine;
    private final FindCharacteristicInfoUseCase getCharacteristicInfo;

    public CharacteristicsController(
            ListCharacteristicsByMachineUseCase listCharacteristicsByMachine,
            FindCharacteristicInfoUseCase getCharacteristicInfo
    ) {
        this.listCharacteristicsByMachine = listCharacteristicsByMachine;
        this.getCharacteristicInfo = getCharacteristicInfo;
    }

    @GetMapping("/{machine_id}")
    List<CharacteristicLight> listAllCharacteristics(@PathVariable("machine_id") long machineId) {
        return listCharacteristicsByMachine.listByMachine(machineId);
    }

    @GetMapping("/{machine_id}/{code}")
    CharacteristicDisplayInfo getCharacteristicInfo(@PathVariable("machine_id") long machineId, @PathVariable("code") String code) {
        return getCharacteristicInfo.find(machineId, code)
                .orElseThrow(() -> {
                    HashMap<String, Object> keys = new HashMap<>();
                    keys.put("machineId", machineId);
                    keys.put("code", code);
                    return new ResourceNotFoundException("characteristics", keys);
                });
    }
}
