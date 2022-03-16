package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.DeviceInfo;

import java.util.Optional;

// Ottiene le informazioni di una macchina data la sua chiave API.
// Ritorna Optional.empty() se la macchina cercata non esiste.
public interface FindDeviceInfoByApiKeyPort {
	Optional<DeviceInfo> findDeviceByApiKey(String apiKey);
}
