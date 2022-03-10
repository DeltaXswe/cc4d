package it.deltax.produlytics.uibackend.common.machine.web;

import it.deltax.produlytics.uibackend.common.machine.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.common.machine.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.ResourceNotFoundException;
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
    List<CharacteristicLight> listAllCharacteristics(@PathVariable("machine_id") int machineId) {
        return listCharacteristicsByMachine.listByMachine(machineId);
    }

    @GetMapping("/{machine_id}/characteristic/{id}")
    CharacteristicDisplayInfo getCharacteristicInfo(@PathVariable("machine_id") int machineId, @PathVariable("id") int id) {
        return getCharacteristicInfo.find(machineId, id)
            .orElseThrow(() -> {
                HashMap<String, Object> keys = new HashMap<>();
                keys.put("machineId", machineId);
                keys.put("id", id);
                return new ResourceNotFoundException("characteristics", keys);
            });
    }
}
