package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreateDevice {

	public String generateApiKey() {
		int leftLimit = 48; // from '0'
		int rightLimit = 122; // to 'z'
		int targetStringLength = 32;
		Random random = new Random();

		String apiKey = random.ints(leftLimit, rightLimit + 1)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();

		return apiKey;
	}

	public NewDevice createDevice(String name) {
		return new NewDevice(name, false, false, this.generateApiKey());
	}


}
