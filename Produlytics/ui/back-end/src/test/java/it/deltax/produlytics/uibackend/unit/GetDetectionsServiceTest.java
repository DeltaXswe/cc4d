package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.detections.business.services.GetDetectionsService;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/** Test di unità della classe GetDetectionsService */
public class GetDetectionsServiceTest {
  private static GetDetectionsService service;

  private static final Detection detection1 = new Detection(1, 100, false);

  private static final Detection detection2 = new Detection(2, 200, false);

  private static final Detection detection3 = new Detection(3, 300, false);

  @Test
  void testGetDetectionsWithNoFilters() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(1, 1, DetectionFilters.builder().build());

    assert detections.detections().equals(List.of(detection1, detection2, detection3));
    assert detections.nextOld().isEmpty();
    assert detections.nextNew() == 3;
  }

  @Test
  void testGetDetectionsWithLimit() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(
            1, 1, DetectionFilters.builder().withLimit(OptionalInt.of(1)).build());

    assert detections.detections().equals(List.of(detection3));
    assert detections.nextOld().getAsLong() == 3;
    assert detections.nextNew() == 3;
  }

  @Test
  void testGetDetectionsWithOlderThan() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsWithOlderThanPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(
            1, 1, DetectionFilters.builder().withOlderThan(OptionalLong.of(3)).build());

    assert detections.detections().equals(List.of(detection1, detection2));
    assert detections.nextOld().isEmpty();
    assert detections.nextNew() == 2;
  }

  @Test
  void testGetDetectionsWithNewerThan() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(
            1, 1, DetectionFilters.builder().withNewerThan(OptionalLong.of(1)).build());

    assert detections.detections().equals(List.of(detection2, detection3));
    assert detections.nextOld().getAsLong() == 2;
    assert detections.nextNew() == 3;
  }

  @Test
  void testGetEmptyDetections() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsEmptyPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(1, 1, DetectionFilters.builder().build());

    assert detections.detections().equals(Collections.emptyList());
    assert detections.nextOld().isEmpty();
  }

  @Test
  void testGetEmptyByFiltersDetections() throws BusinessException {
    service =
        new GetDetectionsService(
            new FindAllDetectionsPortMock(), new FindCharacteristicLimitsPortMock());

    DetectionsGroup detections =
        service.listByCharacteristic(
            1,
            1,
            DetectionFilters.builder()
                .withOlderThan(OptionalLong.of(1))
                .withNewerThan(OptionalLong.of(3))
                .build());

    assert detections.detections().equals(Collections.emptyList());
    assert detections.nextOld().isEmpty();
  }

  @Test
  void testGetNotFoundAndArchived() {
    service =
        new GetDetectionsService(
            new FindAllDetectionsPortMock(), new FindCharacteristicLimitsNotFoundPortMock());

    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () -> service.listByCharacteristic(1, 1, DetectionFilters.builder().build()));
    assert exception.getCode().equals("characteristicNotFound");
    assert exception.getType() == ErrorType.NOT_FOUND;
  }

  // Classi Mock
  private static class FindAllDetectionsPortMock implements FindAllDetectionsPort {
    @Override
    public List<Detection> findAllByCharacteristic(
        int deviceId, int characteristicId, OptionalLong olderThan) {
      return List.of(detection3, detection2, detection1);
    }
  }

  private static class FindAllDetectionsWithOlderThanPortMock implements FindAllDetectionsPort {
    @Override
    public List<Detection> findAllByCharacteristic(
        int deviceId, int characteristicId, OptionalLong olderThan) {
      List<Detection> list = new ArrayList<>(List.of(detection3, detection2, detection1));

      while (!(list.get(0).creationTime() < olderThan.getAsLong())) {
        list.remove(0);
      }

      return list;
    }
  }

  private static class FindAllDetectionsEmptyPortMock implements FindAllDetectionsPort {
    @Override
    public List<Detection> findAllByCharacteristic(
        int deviceId, int characteristicId, OptionalLong olderThan) {
      return Collections.emptyList();
    }
  }

  private static class FindCharacteristicLimitsPortMock implements FindCharacteristicLimitsPort {
    @Override
    public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
      return Optional.of(CharacteristicLimits.newCharacteristicLimits(10d, 100d));
    }
  }

  private static class FindCharacteristicLimitsNotFoundPortMock
      implements FindCharacteristicLimitsPort {
    @Override
    public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
      return Optional.empty();
    }
  }
}
