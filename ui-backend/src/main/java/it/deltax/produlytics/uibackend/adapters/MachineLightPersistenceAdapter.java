package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.uibackend.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.business.ports.out.ListAllMachinesPort;
import it.deltax.produlytics.uibackend.db.configurazione.repositories.MacchinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
                new MachineLight(
                    macchina.getId(),
                    macchina.getNome()
                )
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<MachineLight> find(long machineId) {
        return repo.findById(machineId)
            .map(macchina ->
                new MachineLight(
                    macchina.getId(),
                    macchina.getNome()
                )
            );
    }
}
