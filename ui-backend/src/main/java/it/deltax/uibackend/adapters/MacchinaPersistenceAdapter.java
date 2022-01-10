package it.deltax.uibackend.adapters;

import it.deltax.uibackend.business.domain.MachineLight;
import it.deltax.uibackend.business.ports.out.ListAllMachinesPort;
import it.deltax.uibackend.db.repos.MacchinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MacchinaPersistenceAdapter implements ListAllMachinesPort {

    @Autowired
    private MacchinaRepository repo;

    @Override
    public List<MachineLight> listAllMachines() {
        List<MachineLight> macchine = new ArrayList<>();
        repo.findAll().forEach(macchina -> {
            macchine.add(new MachineLight(macchina.getCod(), macchina.getName()));
        });
        return macchine;
    }
}
