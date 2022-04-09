package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.charts.ControlCharts;
import it.deltax.produlytics.api.detections.business.domain.charts.MarkableDetection;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimits;
import it.deltax.produlytics.api.detections.business.domain.limits.ControlLimitsCalculator;
import it.deltax.produlytics.api.detections.business.domain.serie.facade.SeriePortFacade;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

public class DetectionSerieTest {
	@Test
	void testNormal() {
		CharacteristicId characteristicId = new CharacteristicId(69, 42);
		ControlLimits controlLimits = new ControlLimits(10, 70);
		Detection detection = new Detection(characteristicId, Instant.now(), 15);
		List<Detection> lastDetection = List.of(
			new Detection(characteristicId, Instant.now(), 100),
			new Detection(characteristicId, Instant.now(), 90),
			new Detection(characteristicId, Instant.now(), 77)
		);
		final boolean[] detection1Marked = { false };

		SeriePortFacade ports = new SeriePortFacade() {
			@Override
			public void insertDetection(Detection newDetection) {
				assert newDetection == detection;
			}

			@Override
			public List<Detection> findLastDetections(CharacteristicId characteristicIdParam, int count) {
				assert characteristicIdParam == characteristicId;
				assert count == 7;
				return lastDetection;
			}

			@Override
			public void markOutlier(Detection detection) {
				assert detection == lastDetection.get(1);
				detection1Marked[0] = true;
			}
		};

		ControlLimitsCalculator calculator = characteristicIdParam -> {
			assert characteristicIdParam == characteristicId;
			return controlLimits;
		};

		ControlCharts controlCharts = new ControlCharts() {
			@Override
			public int requiredDetectionCount() {
				return 7;
			}

			@Override
			public void analyzeDetections(List<? extends MarkableDetection> markableDetections, ControlLimits limits) {
				assert markableDetections.get(0).value() == lastDetection.get(0).value();
				assert markableDetections.get(1).value() == lastDetection.get(1).value();
				assert markableDetections.get(2).value() == lastDetection.get(2).value();
				assert limits == controlLimits;
				markableDetections.get(1).markOutlier();
			}
		};

		DetectionSerieFactory factory = new DetectionSerieImplFactory(ports, calculator, controlCharts);
		DetectionSerie serie = factory.createSerie(characteristicId);
		serie.insertDetection(detection);

		assert detection1Marked[0];
	}
}
