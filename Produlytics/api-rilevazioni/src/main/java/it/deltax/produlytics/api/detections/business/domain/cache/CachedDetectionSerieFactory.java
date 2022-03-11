package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsCalculatorFactory;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

public class CachedDetectionSerieFactory implements DetectionSerieFactory {
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;
	private final List<ControlChart> controlCharts;
	private final LimitsCalculatorFactory limitsCalculatorFactory;

	public CachedDetectionSerieFactory(
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort,
		LimitsCalculatorFactory limitsCalculatorFactory,
		List<ControlChart> controlCharts
	) {
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.insertDetectionPort = insertDetectionPort;
		this.markOutlierPort = markOutlierPort;
		this.limitsCalculatorFactory = limitsCalculatorFactory;
		this.controlCharts = controlCharts;
	}

	@Override
	public DetectionSerie createSerie(int deviceId, int characteristicId) {
		return new CachedDetectionSerie(
			this.findLastDetectionsPort,
			this.insertDetectionPort,
			this.markOutlierPort,
			this.limitsCalculatorFactory.createCalculator(),
			this.controlCharts,
			deviceId,
			characteristicId
		);
	}
}
