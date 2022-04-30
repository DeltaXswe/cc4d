package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedCharacteristicsService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/** Test di unità della classe GetUnarchivedCharacteristicsService */
public class GetUnarchivedCharacteristicsServiceTest {
  private static final TinyDevice device = new TinyDevice(1, "macchina");

  private static final TinyCharacteristic characteristic1 =
      new TinyCharacteristic(1, "temperatura");

  private static final TinyCharacteristic characteristic2 = new TinyCharacteristic(2, "pressione");

  @Test
  void testGetUnarchivedCharacteristics() throws BusinessException {
    GetUnarchivedCharacteristicsService service =
        new GetUnarchivedCharacteristicsService(
            new GetUnarchivedDevicesPortMock(), new FindAllUnarchivedCharacteristicsPortMock());

    assert service.getByDevice(1).equals(List.of(characteristic1, characteristic2));
  }

  @Test
  void testEmpty() throws BusinessException {
    GetUnarchivedCharacteristicsService service =
        new GetUnarchivedCharacteristicsService(
            new GetUnarchivedDevicesPortMock(),
            new FindAllUnarchivedCharacteristicsEmptyPortMock());

    assert service.getByDevice(1).equals(Collections.emptyList());
  }

  @Test
  void testArchivedAndNotFoundDevice() {
    GetUnarchivedCharacteristicsService service =
        new GetUnarchivedCharacteristicsService(
            new GetUnarchivedDevicesNotFoundPortMock(),
            new FindAllUnarchivedCharacteristicsPortMock());

    BusinessException exception =
        assertThrows(BusinessException.class, () -> service.getByDevice(1));
    assert exception.getCode().equals("deviceNotFound");
    assert exception.getType() == ErrorType.NOT_FOUND;
  }

  // Classi mock
  private static class GetUnarchivedDevicesPortMock implements GetUnarchivedDevicesPort {
    public List<TinyDevice> getUnarchivedDevices() {
      return List.of(device);
    }
  }

  private static class GetUnarchivedDevicesNotFoundPortMock implements GetUnarchivedDevicesPort {
    public List<TinyDevice> getUnarchivedDevices() {
      return Collections.emptyList();
    }
  }

  private static class FindAllUnarchivedCharacteristicsPortMock
      implements FindAllUnarchivedCharacteristicsPort {
    @Override
    public List<TinyCharacteristic> findAllByDeviceId(int deviceId) {
      return List.of(characteristic1, characteristic2);
    }
  }

  private static class FindAllUnarchivedCharacteristicsEmptyPortMock
      implements FindAllUnarchivedCharacteristicsPort {
    @Override
    public List<TinyCharacteristic> findAllByDeviceId(int deviceId) {
      return Collections.emptyList();
    }
  }
}
