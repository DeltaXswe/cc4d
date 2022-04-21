package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicByNamePort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;
import lombok.AllArgsConstructor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class DetectionValidatorTest {
  @Test
  void checkValid() throws BusinessException {
    String apiKey = "foo";
    DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

    FindDeviceByApiKeyPort findDeviceByApiKeyPort =
        new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

    CharacteristicId characteristicId = new CharacteristicId(42, 69);
    CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, false);

    FindCharacteristicByNamePort findCharacteristicByNamePort =
        new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

    DetectionValidator detectionValidator =
        new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

    assert detectionValidator.validateAndFindId(apiKey, "bar").equals(characteristicId);
  }

  @Test
  void checkApiKeyInvalid() throws BusinessException {
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              String apiKey = "foo";
              DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

              FindDeviceByApiKeyPort findDeviceByApiKeyPort =
                  new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

              CharacteristicId characteristicId = new CharacteristicId(42, 69);
              CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, false);

              FindCharacteristicByNamePort findCharacteristicByNamePort =
                  new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

              DetectionValidator detectionValidator =
                  new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

              detectionValidator.validateAndFindId("invalidFoo", "bar");
            });

    assert exception.getCode().equals("notAuthenticated");
    assert exception.getType() == ErrorType.AUTHENTICATION;
  }

  @Test
  void checkDeviceArchived() throws BusinessException {
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              String apiKey = "foo";
              DeviceInfo deviceInfo = new DeviceInfo(42, true, false);

              FindDeviceByApiKeyPort findDeviceByApiKeyPort =
                  new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

              CharacteristicId characteristicId = new CharacteristicId(42, 69);
              CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, false);

              FindCharacteristicByNamePort findCharacteristicByNamePort =
                  new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

              DetectionValidator detectionValidator =
                  new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

              detectionValidator.validateAndFindId(apiKey, "bar");
            });

    assert exception.getCode().equals("archived");
    assert exception.getType() == ErrorType.ARCHIVED;
  }

  @Test
  void checkCharacteristicNotFound() throws BusinessException {
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              String apiKey = "foo";
              DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

              FindDeviceByApiKeyPort findDeviceByApiKeyPort =
                  new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

              CharacteristicId characteristicId = new CharacteristicId(42, 69);
              CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, false);

              FindCharacteristicByNamePort findCharacteristicByNamePort =
                  new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

              DetectionValidator detectionValidator =
                  new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

              detectionValidator.validateAndFindId(apiKey, "baz");
            });

    assert exception.getCode().equals("characteristicNotFound");
    assert exception.getType() == ErrorType.NOT_FOUND;
  }

  @Test
  void checkDeviceDeactivated() throws BusinessException {
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              String apiKey = "foo";
              DeviceInfo deviceInfo = new DeviceInfo(42, false, true);

              FindDeviceByApiKeyPort findDeviceByApiKeyPort =
                  new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

              CharacteristicId characteristicId = new CharacteristicId(42, 69);
              CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, false);

              FindCharacteristicByNamePort findCharacteristicByNamePort =
                  new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

              DetectionValidator detectionValidator =
                  new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

              detectionValidator.validateAndFindId(apiKey, "bar");
            });

    assert exception.getCode().equals("archived");
    assert exception.getType() == ErrorType.ARCHIVED;
  }

  @Test
  void checkCharacteristicArchived() throws BusinessException {
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> {
              String apiKey = "foo";
              DeviceInfo deviceInfo = new DeviceInfo(42, false, false);

              FindDeviceByApiKeyPort findDeviceByApiKeyPort =
                  new FindDeviceByApiKeyPortMock(apiKey, deviceInfo);

              CharacteristicId characteristicId = new CharacteristicId(42, 69);
              CharacteristicInfo characteristicInfo = new CharacteristicInfo(69, true);

              FindCharacteristicByNamePort findCharacteristicByNamePort =
                  new FindCharacteristicByNamePortMock(42, "bar", characteristicInfo);

              DetectionValidator detectionValidator =
                  new DetectionValidatorImpl(findDeviceByApiKeyPort, findCharacteristicByNamePort);

              detectionValidator.validateAndFindId(apiKey, "bar");
            });

    assert exception.getCode().equals("archived");
    assert exception.getType() == ErrorType.ARCHIVED;
  }

  private static class FindDeviceByApiKeyPortMock implements FindDeviceByApiKeyPort {
    private final String apiKey;
    private final DeviceInfo deviceInfo;

    public FindDeviceByApiKeyPortMock(String apiKey, DeviceInfo deviceInfo) {
      this.apiKey = apiKey;
      this.deviceInfo = deviceInfo;
    }

    @Override
    public Optional<DeviceInfo> findDeviceByApiKey(String apiKey) {
      if (apiKey.equals(this.apiKey)) {
        return Optional.of(this.deviceInfo);
      } else {
        return Optional.empty();
      }
    }
  }

  @AllArgsConstructor
  private static class FindCharacteristicByNamePortMock implements FindCharacteristicByNamePort {
    private final int deviceId;
    private final String name;
    private final CharacteristicInfo characteristicInfo;

    @Override
    public Optional<CharacteristicInfo> findCharacteristicByName(int deviceId, String name) {
      if (deviceId == this.deviceId && name.equals(this.name)) {
        return Optional.of(characteristicInfo);
      } else {
        return Optional.empty();
      }
    }
  }
}
