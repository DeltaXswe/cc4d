package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicInfo;
import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.uibackend.business.ports.out.ListCharacteristicsByMachinePort;
import it.deltax.produlytics.persistence.configurazione.CaratteristicaId;
import it.deltax.produlytics.uibackend.db.configurazione.repositories.CaratteristicaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// object adapter (Target sono le port, Adaptee è la repo)

@Component
public class CharacteristicPersistenceAdapter implements ListCharacteristicsByMachinePort, FindCharacteristicInfoPort {

    private final CaratteristicaRepository repo;

    public CharacteristicPersistenceAdapter(CaratteristicaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CharacteristicLight> listCharacteristicsByMachine(long machine) {
        List<CharacteristicLight> characteristics = new ArrayList<>();
        repo.findByIdMacchina(machine).forEach(caratteristica -> characteristics.add(new CharacteristicLight(caratteristica.getId().getCodice(), caratteristica.getNome(), caratteristica.getId().getMacchina())));
        return characteristics;
    }

    @Override
    public Optional<CharacteristicInfo> find(long machine, String characteristicName) {
        return repo.findById(new CaratteristicaId(machine, characteristicName))
                .map(caratteristica -> new CharacteristicInfo(caratteristica.getLimiteMin(), caratteristica.getLimiteMax(), caratteristica.getMedia()));
    }
}
