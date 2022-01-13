package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicTimeseries;
import it.deltax.produlytics.uibackend.web.exceptions.ResourceNotFoundException;
import it.deltax.produlytics.uibackend.business.ports.in.GetCharacteristicTimeseriesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/time_series")
public class TimeseriesController {

    @Autowired
    GetCharacteristicTimeseriesUseCase timeseriesUseCase;

    @GetMapping("/{machine}/{characteristic}")
    public CharacteristicTimeseries getCharacteristicTimeseries(
            @PathVariable long machine,
            @PathVariable String characteristic
    ) {
        Optional<CharacteristicTimeseries> ct = timeseriesUseCase.getCharacteristicTimeSeries(machine, characteristic);
        return ct.orElseThrow(() -> {
            HashMap<String, Object> keys = new HashMap<>();
            keys.put("machine", machine);
            keys.put("characteristic", characteristic);
            return new ResourceNotFoundException("rilevazioni", keys);
        });
    }
}
