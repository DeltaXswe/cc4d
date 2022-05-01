package it.deltax.produlytics.api.unit.detections.business.domain.series.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacade;
import it.deltax.produlytics.api.detections.business.domain.series.facade.SeriesPortFacadeImpl;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SeriesPortFacadeTest {
  @Test
  void testForwardCorrectly() {
    Detection insertDetection = new Detection(new CharacteristicId(24, 96), Instant.now(), 7);
    InsertDetectionPort insertDetectionPort =
        detection -> {
          assert detection == insertDetection;
        };

    CharacteristicId findLastCharacteristicId = new CharacteristicId(15, 16);
    int findLastCount = 7;
    List<Detection> lastDetections =
        List.of(
            new Detection(findLastCharacteristicId, Instant.now(), 6),
            new Detection(findLastCharacteristicId, Instant.now(), 0));
    FindLastDetectionsPort findLastDetectionsPort =
        (characteristicId, count) -> {
          assert characteristicId == findLastCharacteristicId;
          assert count == findLastCount;
          return lastDetections;
        };

    Detection markOutlierDetection = new Detection(new CharacteristicId(42, 69), Instant.now(), 15);
    MarkOutlierPort markOutlierPort =
        detection -> {
          assert detection == markOutlierDetection;
        };

    SeriesPortFacade seriesPortFacade =
        new SeriesPortFacadeImpl(insertDetectionPort, findLastDetectionsPort, markOutlierPort);

    seriesPortFacade.insertDetection(insertDetection);
    assert seriesPortFacade.findLastDetections(findLastCharacteristicId, findLastCount)
        == lastDetections;
    seriesPortFacade.markOutlier(markOutlierDetection);
  }
}
