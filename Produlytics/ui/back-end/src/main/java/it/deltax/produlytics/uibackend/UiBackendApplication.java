package it.deltax.produlytics.uibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(value = {"it.deltax.produlytics.persistence"})
public class UiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UiBackendApplication.class, args);
	}

}
