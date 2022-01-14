package it.deltax.produlytics.uibackend.web;

import it.deltax.produlytics.uibackend.business.domain.TimeSeries;
import it.deltax.produlytics.uibackend.business.ports.in.ListDetectionsUseCase;
import it.deltax.produlytics.uibackend.business.ports.in.TimeSeriesUseCase;
import it.deltax.produlytics.uibackend.web.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/time_series")
public class TimeSeriesController {

    @Autowired
    TimeSeriesUseCase listDetectionsUseCase;

    @GetMapping("/{machine}/{characteristic}")
    public TimeSeries getTimeSeries(
            @PathVariable long machine,
            @PathVariable String characteristic
    ) {
        Optional<TimeSeries> ct = listDetectionsUseCase.getTimeSeries(machine, characteristic);
        return ct.orElseThrow(() -> {
            HashMap<String, Object> keys = new HashMap<>();
            keys.put("machine", machine);
            keys.put("characteristic", characteristic);
            return new ResourceNotFoundException("rilevazioni", keys);
        });
    }
}
