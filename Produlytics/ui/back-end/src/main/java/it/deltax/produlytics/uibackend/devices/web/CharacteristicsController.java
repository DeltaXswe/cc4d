package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics")
public class CharacteristicsController {
    private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics;
    private final FindCharacteristicInfoUseCase getCharacteristicInfo;

    public CharacteristicsController(
        GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics,
        FindCharacteristicInfoUseCase getCharacteristicInfo
    ) {
        this.getUnarchivedCharacteristics = getUnarchivedCharacteristics;
        this.getCharacteristicInfo = getCharacteristicInfo;
    }

    @GetMapping("")
    Iterable<CharacteristicTitle> getUnarchivedCharacteristics(@PathVariable("deviceId") int deviceId) {
        var characteristics = getUnarchivedCharacteristics.getByDevice(deviceId);
        if (characteristics.isEmpty()) {
            HashMap<String, Object> key = new HashMap<>();
            key.put("deviceId", deviceId);
            throw new ResourceNotFoundException("device", key);
        }
        else
            return characteristics;
    }

    @GetMapping("{id}")
	CharacteristicDisplayInfo getCharacteristicInfo(
        @PathVariable("deviceId") int deviceId,
        @PathVariable("id") int id
    ) {
        return getCharacteristicInfo.find(deviceId, id)
            .orElseThrow(() -> {
                HashMap<String, Object> keys = new HashMap<>();
                keys.put("deviceId", deviceId);
                keys.put("id", id);
                return new ResourceNotFoundException("characteristics", keys);
            });
    }
}
