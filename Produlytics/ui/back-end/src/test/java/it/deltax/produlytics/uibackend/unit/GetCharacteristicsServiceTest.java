package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindAllCharacteristicsPort;
import it.deltax.produlytics.uibackend.admins.devices.business.services.GetCharacteristicsService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/** Test di unità della classe GetCharacteristicsService */
public class GetCharacteristicsServiceTest {
  private static final Characteristic characteristic1 = new Characteristic(
      1,
      "temperatura",
      false,
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      false
  );

  private static final Characteristic characteristic2 = new Characteristic(
      2,
      "pressione",
      false,
      Optional.empty(),
      Optional.empty(),
      Optional.empty(),
      true
  );

  @Test
  void testGetCharacteristics() throws BusinessException {
    GetCharacteristicsService service =
        new GetCharacteristicsService(new FindAllCharacteristicsPortMock());

    assert service.getByDevice(1).equals(List.of(characteristic1, characteristic2));
  }

  @Test
  void testNotFound() {
    GetCharacteristicsService service =
        new GetCharacteristicsService(new FindAllCharacteristicsNotFoundPortMock());

    assertThrows(BusinessException.class, () -> service.getByDevice(1));
  }

  // Classi mock
  private static class FindAllCharacteristicsPortMock implements FindAllCharacteristicsPort {
    @Override
    public List<Characteristic> findAllByDeviceId(int deviceId) {
      return List.of(characteristic1, characteristic2);
    }
  }

  private static class FindAllCharacteristicsNotFoundPortMock
      implements FindAllCharacteristicsPort {
    @Override
    public List<Characteristic> findAllByDeviceId(int deviceId) {
      return Collections.emptyList();
    }
  }
}
