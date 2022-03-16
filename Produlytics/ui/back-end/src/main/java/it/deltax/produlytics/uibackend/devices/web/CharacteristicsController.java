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
import java.util.List;

@RestController
@RequestMapping("/characteristics")
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

    @GetMapping("/{device_id}")
    List<CharacteristicTitle> getCharacteristics(@PathVariable("device_id") int deviceId) {
        return getUnarchivedCharacteristics.getByDevice(deviceId);
    }

    @GetMapping("/{device_id}/characteristic/{id}")
	CharacteristicDisplayInfo getCharacteristicInfo(@PathVariable("device_id") int deviceId, @PathVariable("id") int id) {
        return getCharacteristicInfo.find(deviceId, id)
            .orElseThrow(() -> {
                HashMap<String, Object> keys = new HashMap<>();
                keys.put("deviceId", deviceId);
                keys.put("id", id);
                return new ResourceNotFoundException("characteristics", keys);
            });
    }
}
