package it.deltax.uibackend.adapters;

import it.deltax.uibackend.business.domain.CharacteristicLight;
import it.deltax.uibackend.business.ports.out.ListAllCharacteristicsPort;
import it.deltax.uibackend.db.repos.CaratteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacteristicPersistenceAdapter implements ListAllCharacteristicsPort {

    @Autowired
    private CaratteristicaRepository repo;

    @Override
    public List<CharacteristicLight> listAllCharacteristics() {
        List<CharacteristicLight> characteristics = new ArrayList<>();
        repo.findAll().forEach(caratteristica -> characteristics.add(new CharacteristicLight(caratteristica.getNome(), caratteristica.getId().getMacchina())));
        return characteristics;
    }
}
