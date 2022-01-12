package it.deltax.uibackend.adapters;

import it.deltax.uibackend.business.domain.CharacteristicInfo;
import it.deltax.uibackend.business.domain.CharacteristicLight;
import it.deltax.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.uibackend.business.ports.out.ListAllCharacteristicsPort;
import it.deltax.uibackend.db.configurazione.model.CaratteristicaId;
import it.deltax.uibackend.db.configurazione.repository.CaratteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CharacteristicPersistenceAdapter implements ListAllCharacteristicsPort, FindCharacteristicInfoPort {

    @Autowired
    private CaratteristicaRepository repo;

    @Override
    public List<CharacteristicLight> listAllCharacteristics() {
        List<CharacteristicLight> characteristics = new ArrayList<>();
        repo.findAll().forEach(caratteristica -> characteristics.add(new CharacteristicLight(caratteristica.getNome(), caratteristica.getId().getMacchina())));
        return characteristics;
    }

    @Override
    public Optional<CharacteristicInfo> find(long machine, String characteristicName) {
        return repo.findById(new CaratteristicaId(machine, characteristicName))
                .map(caratteristica -> new CharacteristicInfo(caratteristica.getLimiteMin(), caratteristica.getLimiteMax(), caratteristica.getMedia()));
    }
}
