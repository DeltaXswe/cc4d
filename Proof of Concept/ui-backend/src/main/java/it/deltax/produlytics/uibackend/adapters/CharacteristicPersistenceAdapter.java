package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.uibackend.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.business.ports.out.ListCharacteristicsByMachinePort;
import it.deltax.produlytics.persistence.configurazione.CaratteristicaId;
import it.deltax.produlytics.uibackend.db.configurazione.repositories.CaratteristicaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// object adapter (Target sono le port, Adaptee è la repo)

@Component
public class CharacteristicPersistenceAdapter implements ListCharacteristicsByMachinePort, FindCharacteristicPort {

    private final CaratteristicaRepository repo;

    public CharacteristicPersistenceAdapter(CaratteristicaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CharacteristicLight> listByMachine(long machineId) {
        return repo.findByIdMacchina(machineId).stream()
            .map(
                caratteristica -> new CharacteristicLight(
                    caratteristica.getId().getCodice(),
                    caratteristica.getNome(),
                    caratteristica.getId().getMacchina()
                )
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Characteristic> find(long machineId, String characteristicCode) {
        return repo.findById(new CaratteristicaId(machineId, characteristicCode))
            .map(caratteristica ->
                new Characteristic(
                    caratteristica.getId().getCodice(),
                    caratteristica.getNome(),
                    caratteristica.getId().getMacchina(),
                    caratteristica.getLimiteMin(),
                    caratteristica.getLimiteMax(),
                    caratteristica.getMedia()
                )
            );
    }
}
