package it.deltax.produlytics.api.business.ports;

import it.deltax.produlytics.api.business.domain.Characteristic;
import it.deltax.produlytics.api.business.domain.CharacteristicLight;
import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;
import it.deltax.produlytics.api.business.ports.in.InsertDetectionUseCase;
import it.deltax.produlytics.api.business.ports.out.FindCharacteristcPort;
import it.deltax.produlytics.api.business.ports.out.InsertDetectionPort;

import java.util.Optional;

public class InsertDetectionService implements InsertDetectionUseCase {

    private final InsertDetectionPort insertDetectionPort;
    private final FindCharacteristcPort findCharacteristcPort;

    public InsertDetectionService(InsertDetectionPort port, FindCharacteristcPort findCharacteristcPort) {
        this.insertDetectionPort = port;
        this.findCharacteristcPort = findCharacteristcPort;
    }

    @Override
    public Optional<Detection> insertRilevazione(DetectionLight rilevazione) {
        Optional<Characteristic> characteristic = findCharacteristcPort.findCharacteristic(
            rilevazione.machine(),
            rilevazione.characteristic()
        );
        return characteristic.map(ch -> {
            double valueDelta = rilevazione.value() - ch.average();
            double topSigma = (ch.upperLimit() - ch.average()) / 3.0;
            double lowerSigma = (ch.average() - ch.lowerLimit()) / 3.0;
            boolean anomalo = valueDelta > 0 ? valueDelta > 2 * topSigma : -valueDelta > 2 * lowerSigma;
            return insertDetectionPort.insertDetection(
                new DetectionLight(
                    rilevazione.machine(),
                    rilevazione.characteristic(),
                    rilevazione.value(),
                    anomalo
                )
            );
        });
    }

}
