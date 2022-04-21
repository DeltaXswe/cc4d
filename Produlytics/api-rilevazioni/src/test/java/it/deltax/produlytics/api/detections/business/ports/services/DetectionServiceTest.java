package it.deltax.produlytics.api.detections.business.ports.services;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.services.DetectionsService;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class DetectionServiceTest {
  @Test
  void testValid() throws BusinessException {
    IncomingDetection incomingDetection = new IncomingDetection("foo", "bar", 15);
    CharacteristicId realCharacteristicId = new CharacteristicId(42, 69);
    Detection expectedDetection = new Detection(realCharacteristicId, Instant.now(), 15);
    DetectionValidator validator =
        (apiKey, characteristicName) -> {
          assert apiKey.equals("foo");
          assert characteristicName.equals("bar");
          return realCharacteristicId;
        };
    DetectionQueue queue = new DetectionQueueMock(expectedDetection);
    DetectionsService detectionsService = new DetectionsService(validator, queue);
    detectionsService.processIncomingDetection(incomingDetection);
  }

  @Test
  void testInvalid() {
    IncomingDetection incomingDetection = new IncomingDetection("fooo", "bar", 15);
    CharacteristicId realCharacteristicId = new CharacteristicId(42, 69);
    Detection expectedDetection = new Detection(realCharacteristicId, Instant.now(), 15);
    DetectionValidator validator =
        (apiKey, characteristicId) -> {
          assert apiKey.equals("fooo");
          assert characteristicId.equals("bar");
          throw new BusinessException("bubu", ErrorType.AUTHENTICATION);
        };
    DetectionQueue queue = new DetectionQueueMock(expectedDetection);
    DetectionsService detectionsService = new DetectionsService(validator, queue);
    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> detectionsService.processIncomingDetection(incomingDetection));
    assert exception.getCode().equals("bubu");
    assert exception.getType() == ErrorType.AUTHENTICATION;
  }

  static class DetectionQueueMock implements DetectionQueue {
    private final Detection expectedDetection;

    public DetectionQueueMock(Detection expectedDetection) {
      this.expectedDetection = expectedDetection;
    }

    @Override
    public void enqueueDetection(Detection detection) {
      Instant now = Instant.now();
      assert detection.characteristicId() == this.expectedDetection.characteristicId();
      assert !detection.creationTime().isAfter(now);
      assert !detection.creationTime().isBefore(this.expectedDetection.creationTime());
      assert detection.value() == this.expectedDetection.value();
    }

    @Override
    public void close() {}
  }
}
