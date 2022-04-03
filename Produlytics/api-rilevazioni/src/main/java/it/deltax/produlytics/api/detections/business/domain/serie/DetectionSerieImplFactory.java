package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

// Implementazione canonica di `DetectionSerieFactory` che costruisce una `DetectionSerieImpl`
public class DetectionSerieImplFactory implements DetectionSerieFactory {
	private final InsertDetectionPort insertDetectionPort;
	private final FindLimitsPort findLimitsPort;
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final MarkOutlierPort markOutlierPort;

	private final List<? extends ControlChart> controlCharts;

	public DetectionSerieImplFactory(
		InsertDetectionPort insertDetectionPort,
		FindLimitsPort findLimitsPort,
		FindLastDetectionsPort findLastDetectionsPort,
		MarkOutlierPort markOutlierPort,
		List<? extends ControlChart> controlCharts
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.findLimitsPort = findLimitsPort;
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.markOutlierPort = markOutlierPort;
		this.controlCharts = controlCharts;
	}
	@Override
	public DetectionSerie createSerie(CharacteristicId characteristicId) {
		return new DetectionSerieImpl(
			this.insertDetectionPort,
			this.findLimitsPort,
			this.findLastDetectionsPort,
			this.markOutlierPort,
			this.controlCharts,
			characteristicId
		);
	}
}
