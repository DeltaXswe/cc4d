package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlChartsGroup;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DetectionSerieTest {
  @Test
  void testNormal() {
    CharacteristicId characteristicId = new CharacteristicId(69, 42);
    ControlLimits controlLimits = new ControlLimits(10, 70);
    Detection detection = new Detection(characteristicId, Instant.now(), 15);
    List<Detection> mockDetections =
        List.of(
            new Detection(characteristicId, Instant.now(), 100),
            new Detection(characteristicId, Instant.now(), 90),
            new Detection(characteristicId, Instant.now(), 77));
    final boolean[] detection1Marked = {false};

    SeriePortFacade ports =
        new SeriePortFacade() {
          @Override
          public void insertDetection(Detection newDetection) {
            assert newDetection == detection;
          }

          @Override
          public List<Detection> findLastDetections(
              CharacteristicId characteristicIdParam, int count) {
            assert characteristicIdParam == characteristicId;
            assert count == 7;
            return mockDetections;
          }

          @Override
          public void markOutlier(Detection detection) {
            assert detection == mockDetections.get(1);
            detection1Marked[0] = true;
          }
        };

    ControlLimitsCalculator calculator =
        characteristicIdParam -> {
          assert characteristicIdParam == characteristicId;
          return controlLimits;
        };

    ControlChartsGroup controlChartsGroup =
        new ControlChartsGroup() {
          @Override
          public int requiredDetectionCount() {
            return 7;
          }

          @Override
          public void analyzeDetections(
              List<? extends MarkableDetection> detections, ControlLimits limits) {
            assert detections.get(0).value() == mockDetections.get(0).value();
            assert detections.get(1).value() == mockDetections.get(1).value();
            assert detections.get(2).value() == mockDetections.get(2).value();
            assert limits == controlLimits;
            detections.get(1).markOutlier();
          }
        };

    DetectionSerieFactory factory =
        new DetectionSerieImplFactory(ports, calculator, controlChartsGroup);
    DetectionSerie serie = factory.createSerie(characteristicId);
    serie.insertDetection(detection);

    assert detection1Marked[0];
  }
}
