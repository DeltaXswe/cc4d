package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;

import java.util.List;

public class DetectionSerieImplFactory implements DetectionSerieFactory {
	private final InsertDetectionPort insertDetectionPort;
	private final FindLimitsPort findLimitsPort;
	private final FindLastDetectionsPort findLastDetectionsPort;

	private final List<ControlChart> controlCharts;

	public DetectionSerieImplFactory(
		InsertDetectionPort insertDetectionPort,
		FindLimitsPort findLimitsPort,
		FindLastDetectionsPort findLastDetectionsPort,
		List<ControlChart> controlCharts
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.findLimitsPort = findLimitsPort;
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.controlCharts = controlCharts;
	}
	@Override
	public DetectionSerie createSerie(int deviceId, int characteristicId) {
		return new DetectionSerieImpl(
			this.insertDetectionPort,
			this.findLimitsPort,
			this.findLastDetectionsPort,
			this.controlCharts,
			deviceId,
			characteristicId
		);
	}
}
