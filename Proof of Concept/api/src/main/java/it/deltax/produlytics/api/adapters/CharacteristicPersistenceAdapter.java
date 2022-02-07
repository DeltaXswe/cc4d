package it.deltax.produlytics.api.adapters;

import it.deltax.produlytics.api.business.domain.Characteristic;
import it.deltax.produlytics.api.business.ports.out.FindCharacteristcPort;
import it.deltax.produlytics.api.db.configurazione.repositories.CaratteristicaRepository;
import it.deltax.produlytics.persistence.configurazione.CaratteristicaId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CharacteristicPersistenceAdapter implements FindCharacteristcPort {

    private final CaratteristicaRepository repo;

    public CharacteristicPersistenceAdapter(CaratteristicaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Characteristic> findCharacteristic(long macchina, String name) {
        return repo.findById(new CaratteristicaId(macchina, name))
            .map(car -> new Characteristic(
                car.getId().getCodice(),
                car.getNome(),
                car.getId().getMacchina(),
                car.getLimiteMin().doubleValue(),
                car.getLimiteMax().doubleValue(),
                car.getMedia().doubleValue()
            ));
    }
}
