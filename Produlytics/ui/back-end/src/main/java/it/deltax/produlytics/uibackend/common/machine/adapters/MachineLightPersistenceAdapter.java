package it.deltax.produlytics.uibackend.common.machine.adapters;

import it.deltax.produlytics.uibackend.common.machine.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.ListAllMachinesPort;
import it.deltax.produlytics.uibackend.repositories.MacchinaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MachineLightPersistenceAdapter implements ListAllMachinesPort, FindMachinePort {

    private final MacchinaRepository repo;

    public MachineLightPersistenceAdapter(MacchinaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<MachineLight> listAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
            .map(macchina ->
                new MachineLight(macchina.getId(), macchina.getName())
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<MachineLight> find(int machineId) {
        return repo.findById(machineId)
            .map(macchina ->
                new MachineLight(macchina.getId(), macchina.getName())
            );
    }
}
