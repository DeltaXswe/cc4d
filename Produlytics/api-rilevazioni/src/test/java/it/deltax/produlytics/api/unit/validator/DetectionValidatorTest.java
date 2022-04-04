package it.deltax.produlytics.api.unit.validator;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidatorImpl;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceInfoByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DetectionValidatorTest {
	private static class FindDeviceInfoByApiKeyPortMock implements FindDeviceInfoByApiKeyPort {
		private final String apiKey;
		private final DeviceInfo deviceInfo;

		public FindDeviceInfoByApiKeyPortMock(String apiKey, DeviceInfo deviceInfo) {
			this.apiKey = apiKey;
			this.deviceInfo = deviceInfo;
		}

		@Override
		public Optional<DeviceInfo> findDeviceByApiKey(String apiKey) {
			if(apiKey.equals(this.apiKey)) {
				return Optional.of(this.deviceInfo);
			} else {
				return Optional.empty();
			}
		}
	}

	private static class FindCharacteristicInfoPortMock implements FindCharacteristicInfoPort {
		private final CharacteristicId id;
		private final CharacteristicInfo characteristicInfo;

		public FindCharacteristicInfoPortMock(CharacteristicId id, CharacteristicInfo characteristicInfo) {
			this.id = id;
			this.characteristicInfo = characteristicInfo;
		}

		@Override
		public Optional<CharacteristicInfo> findCharacteristic(CharacteristicId characteristicId) {
			if(characteristicId.equals(this.id)) {
				return Optional.of(characteristicInfo);
			} else {
				return Optional.empty();
			}
		}
	}

	@Test
	void checkValid() throws BusinessException {
		String apiKey = "foo";
		DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

		FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey, deviceInfo);

		CharacteristicId characteristicId = new CharacteristicId(42, 69);
		CharacteristicInfo characteristicInfo = new CharacteristicInfo(false);

		FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
			characteristicInfo
		);

		DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
			findCharacteristicInfoPort
		);

		assert detectionValidator.validateAndFindDeviceId(apiKey, 69).equals(characteristicId);
	}

	@Test
	void checkApiKeyInvalid() throws BusinessException {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			String apiKey = "foo";
			DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

			FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey,
				deviceInfo
			);

			CharacteristicId characteristicId = new CharacteristicId(42, 69);
			CharacteristicInfo characteristicInfo = new CharacteristicInfo(false);

			FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
				characteristicInfo
			);

			DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
				findCharacteristicInfoPort
			);

			detectionValidator.validateAndFindDeviceId("invalidFoo", 69);
		});

		assert exception.getCode().equals("notAuthenticated");
		assert exception.getType() == ErrorType.AUTHENTICATION;
	}

	@Test
	void checkDeviceArchived() throws BusinessException {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			String apiKey = "foo";
			DeviceInfo deviceInfo = new DeviceInfo(42, true, false);

			FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey,
				deviceInfo
			);

			CharacteristicId characteristicId = new CharacteristicId(42, 69);
			CharacteristicInfo characteristicInfo = new CharacteristicInfo(false);

			FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
				characteristicInfo
			);

			DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
				findCharacteristicInfoPort
			);

			detectionValidator.validateAndFindDeviceId(apiKey, 69);
		});

		assert exception.getCode().equals("archived");
		assert exception.getType() == ErrorType.ARCHIVED;
	}

	@Test
	void checkCharacteristicNotFound() throws BusinessException {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			String apiKey = "foo";
			DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

			FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey,
				deviceInfo
			);

			CharacteristicId characteristicId = new CharacteristicId(42, 69);
			CharacteristicInfo characteristicInfo = new CharacteristicInfo(false);

			FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
				characteristicInfo
			);

			DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
				findCharacteristicInfoPort
			);

			detectionValidator.validateAndFindDeviceId(apiKey, 70);
		});

		assert exception.getCode().equals("characteristicNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	@Test
	void checkDeviceDeactivated() throws BusinessException {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			String apiKey = "foo";
			DeviceInfo deviceInfo = new DeviceInfo(42, false, true);

			FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey,
				deviceInfo
			);

			CharacteristicId characteristicId = new CharacteristicId(42, 69);
			CharacteristicInfo characteristicInfo = new CharacteristicInfo(false);

			FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
				characteristicInfo
			);

			DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
				findCharacteristicInfoPort
			);

			detectionValidator.validateAndFindDeviceId(apiKey, 69);
		});

		assert exception.getCode().equals("archived");
		assert exception.getType() == ErrorType.ARCHIVED;
	}

	@Test
	void checkCharacteristicArchived() throws BusinessException {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			String apiKey = "foo";
			DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

			FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort = new FindDeviceInfoByApiKeyPortMock(apiKey,
				deviceInfo
			);

			CharacteristicId characteristicId = new CharacteristicId(42, 69);
			CharacteristicInfo characteristicInfo = new CharacteristicInfo(true);

			FindCharacteristicInfoPort findCharacteristicInfoPort = new FindCharacteristicInfoPortMock(characteristicId,
				characteristicInfo
			);

			DetectionValidator detectionValidator = new DetectionValidatorImpl(findDeviceInfoByApiKeyPort,
				findCharacteristicInfoPort
			);

			detectionValidator.validateAndFindDeviceId(apiKey, 69);
		});

		assert exception.getCode().equals("archived");
		assert exception.getType() == ErrorType.ARCHIVED;
	}
}
