package it.deltax.produlytics.uibackend.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.ListCharacteristicsByDevicePort;
import it.deltax.produlytics.uibackend.repositories.CaratteristicaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// object adapter (Target sono le port, Adaptee è la repo)

@Component
public class CharacteristicPersistenceAdapter implements ListCharacteristicsByDevicePort, FindCharacteristicPort {

    private final CaratteristicaRepository repo;

    public CharacteristicPersistenceAdapter(CaratteristicaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<CharacteristicLight> listByMachine(int machineId) {
        return repo.findByIdDeviceId(machineId).stream()
            .map(
                caratteristica -> new CharacteristicLight(
                    caratteristica.getId().getId(),
                    caratteristica.getName(),
                    caratteristica.getId().getDeviceId()
                )
            )
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Characteristic> find(int machineId, int id) {
        return repo.findById(new CharacteristicEntityId(machineId, id))
            .map(caratteristica ->
                new Characteristic(
                    caratteristica.getId().getId(),
                    caratteristica.getName(),
                    caratteristica.getId().getDeviceId(),
                    Optional.ofNullable(caratteristica.getLowerLimit()),
                    Optional.ofNullable(caratteristica.getUpperLimit()),
                    Optional.ofNullable(caratteristica.getAverage())
                )
            );
    }
}
