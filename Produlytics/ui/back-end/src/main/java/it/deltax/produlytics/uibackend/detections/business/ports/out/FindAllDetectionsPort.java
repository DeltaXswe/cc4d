package it.deltax.produlytics.uibackend.detections.business.ports.out;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import java.util.List;
import java.util.OptionalLong;

/** La porta per l'ottenimento delle rilevazioni di una caratteristica di una macchina */
public interface FindAllDetectionsPort {
  List<Detection> findAllByCharacteristic(
      int deviceId, int characteristicId, OptionalLong olderThan);
}
