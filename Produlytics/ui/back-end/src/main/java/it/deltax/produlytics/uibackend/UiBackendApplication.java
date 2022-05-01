package it.deltax.produlytics.uibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * La classe contenente il metodo {@code main}, che fa partire l'esecuzione dell'applicazione.
 */
@SpringBootApplication
@EntityScan(value = {"it.deltax.produlytics.persistence"})
public class UiBackendApplication {
  public static void main(String[] args) {
    SpringApplication.run(UiBackendApplication.class, args);
  }
}
