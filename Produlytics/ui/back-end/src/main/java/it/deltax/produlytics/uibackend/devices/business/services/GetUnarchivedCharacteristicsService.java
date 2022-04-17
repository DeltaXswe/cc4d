package it.deltax.produlytics.uibackend.devices.business.services;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Il service per l'ottenimento delle caratteristiche non archiviate di una macchina
 */
@Service
public class GetUnarchivedCharacteristicsService implements GetUnarchivedCharacteristicsUseCase {
    private final GetUnarchivedDevicesPort findDevicesPort;
    private final FindAllUnarchivedCharacteristicsPort findCharacteristicsPort;

    /**
     * Il costruttore
     * @param findDevicesPort la porta per ottenere le macchine non archiviate
     * @param findCharacteristicsPort la porta per ottenere tutte le caratteristiche non archiviate di una macchina
     */
    public GetUnarchivedCharacteristicsService(
        GetUnarchivedDevicesPort findDevicesPort,
        FindAllUnarchivedCharacteristicsPort findCharacteristicsPort
    ) {
        this.findDevicesPort = findDevicesPort;
        this.findCharacteristicsPort = findCharacteristicsPort;
    }

    /**
     * Restituisce la lista delle caratteristiche non archiviate di una macchina
     * @param deviceId l'id della macchina
     * @return la lista delle caratteristiche non archiviate
     * @throws BusinessException se la macchina è inesistente
     */
    @Override
    public List<CharacteristicTitle> getByDevice(int deviceId) throws BusinessException {
        if (
            findDevicesPort.getUnarchivedDevices().stream()
                .filter(device -> device.id() == deviceId)
                .toList().isEmpty()
        ) {
            throw new BusinessException("deviceNotFound", ErrorType.NOT_FOUND);
        }

        return this.findCharacteristicsPort.findAllByDeviceId(deviceId);
    }
}
