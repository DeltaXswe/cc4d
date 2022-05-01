package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;
import java.util.Random;
import org.springframework.stereotype.Component;

/** Classe per la creazione di una nuova macchina. */
@Component
public class CreateDevice {

  /**
   * Genere una chiave randomica lunga 32 caratteri alfanumerici per l'API.
   *
   * @return la chiave alfanumerica
   */
  public String generateApiKey() {
    int leftLimit = 48; // da '0'
    int rightLimit = 122; // a 'z'
    int targetStringLength = 32;
    Random random = new Random();

    String apiKey =
        random
            .ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    return apiKey;
  }

  /**
   * Crea una nuova macchina con il nome dato.
   *
   * @param name il nome della nuova macchina
   * @return una nuova macchina con nome dato, attivata, non archiviata e con chiave dell'API
   *     randomica
   */
  public NewDevice createDevice(String name) {
    return new NewDevice(name, false, false, this.generateApiKey());
  }
}
