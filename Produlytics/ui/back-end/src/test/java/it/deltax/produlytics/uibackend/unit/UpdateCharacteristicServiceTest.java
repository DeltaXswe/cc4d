package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicToUpdate;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.services.UpdateCharacteristicService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/** Test di unità della classe UpdateCharacteristicService */
public class UpdateCharacteristicServiceTest {
  @Test
  void testUpdateCharacteristicWithNoAutoAdjust() throws BusinessException {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    service.updateCharacteristic(
        CharacteristicToUpdate.builder()
            .withId(1)
            .withDeviceId(1)
            .withName("lunghezza")
            .withLowerLimit(OptionalDouble.of(10d))
            .withUpperLimit(OptionalDouble.of(100d))
            .withAutoAdjust(false)
            .build());
  }

  @Test
  void testUpdateCharacteristicWithAutoAdjust() throws BusinessException {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    service.updateCharacteristic(
        CharacteristicToUpdate.builder()
            .withId(1)
            .withDeviceId(1)
            .withName("lunghezza")
            .withAutoAdjust(true)
            .withSampleSize(OptionalInt.of(0))
            .build());
  }

  @Test
  void testUpdateArchivedCharacteristic() throws BusinessException {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    service.updateCharacteristic(
        CharacteristicToUpdate.builder()
            .withId(2)
            .withDeviceId(1)
            .withName("lunghezza")
            .withAutoAdjust(true)
            .withSampleSize(OptionalInt.of(0))
            .build());
  }

  @Test
  void testUpdateDuplicateCharacteristic() {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () ->
                service.updateCharacteristic(
                    CharacteristicToUpdate.builder()
                        .withId(1)
                        .withDeviceId(1)
                        .withName("pressione")
                        .withAutoAdjust(true)
                        .withSampleSize(OptionalInt.of(0))
                        .build()));
    assert exception.getCode().equals("duplicateCharacteristicName");
    assert exception.getType() == ErrorType.GENERIC;
  }

  @Test
  void testUpdateCharacteristicWithNoAutoAdjustAndNoLimits() {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () ->
                service.updateCharacteristic(
                    CharacteristicToUpdate.builder()
                        .withId(1)
                        .withDeviceId(1)
                        .withName("lunghezza")
                        .withAutoAdjust(false)
                        .build()));
    assert exception.getCode().equals("invalidValues");
    assert exception.getType() == ErrorType.GENERIC;
  }

  @Test
  void testUpdateCharacteristicWithAutoAdjustAndNoSampleSize() throws BusinessException {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    service.updateCharacteristic(
        CharacteristicToUpdate.builder()
            .withId(1)
            .withDeviceId(1)
            .withName("lunghezza")
            .withAutoAdjust(true)
            .build());
  }

  @Test
  void testUpdateCharacteristicNotFound() {
    UpdateCharacteristicService service =
        new UpdateCharacteristicService(
            new FindDetailedCharacteristicPortMock(), new UpdateCharacteristicPortMock());

    BusinessException exception =
        assertThrows(
            BusinessException.class,
            () ->
                service.updateCharacteristic(
                    CharacteristicToUpdate.builder()
                        .withId(3)
                        .withDeviceId(1)
                        .withName("lunghezza")
                        .withAutoAdjust(true)
                        .withSampleSize(OptionalInt.of(0))
                        .build()));
    assert exception.getCode().equals("characteristicNotFound");
    assert exception.getType() == ErrorType.NOT_FOUND;
  }

  // Classi mock
  private static class FindDetailedCharacteristicPortMock
      implements FindDetailedCharacteristicPort {
    private static final List<DetailedCharacteristic> mockCharacteristics =
        List.of(
            DetailedCharacteristic.builder()
                .withId(1)
                .withDeviceId(1)
                .withName("temperatura")
                .withLowerLimit(OptionalDouble.of(10d))
                .withUpperLimit(OptionalDouble.of(100d))
                .withAutoAdjust(true)
                .withSampleSize(OptionalInt.of(0))
                .build(),
            DetailedCharacteristic.builder()
                .withId(2)
                .withDeviceId(1)
                .withName("pressione")
                .withLowerLimit(OptionalDouble.of(10d))
                .withUpperLimit(OptionalDouble.of(100d))
                .withAutoAdjust(true)
                .withSampleSize(OptionalInt.of(0))
                .withArchived(true)
                .build());

    @Override
    public Optional<DetailedCharacteristic> findByCharacteristic(
        int deviceId, int characteristicId) {
      return mockCharacteristics.stream()
          .filter(c -> c.deviceId() == deviceId && c.id() == characteristicId)
          .findFirst();
    }

    @Override
    public Optional<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
      return mockCharacteristics.stream()
          .filter(c -> c.deviceId() == deviceId && c.name() == name)
          .findFirst();
    }
  }

  private static class UpdateCharacteristicPortMock implements UpdateCharacteristicPort {
    @Override
    public void updateCharacteristic(DetailedCharacteristic characteristic) {}
  }
}
