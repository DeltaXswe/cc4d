package it.deltax.produlytics.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/** La classe principale del programma. */
@SpringBootApplication
@EntityScan(value = {"it.deltax.produlytics.persistence"})
public class ApiApplication {
  /**
   * L'entry point del programma.
   *
   * @param args gli argomenti passati al comando di esecuzione
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }
}
