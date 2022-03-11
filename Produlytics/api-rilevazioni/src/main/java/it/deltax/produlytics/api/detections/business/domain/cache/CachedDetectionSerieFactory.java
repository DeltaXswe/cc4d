package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.queue.DetectionSerie;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionSerieFactory;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class CachedDetectionSerieFactory implements DetectionSerieFactory {
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;

	public CachedDetectionSerieFactory(
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort
	) {
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.insertDetectionPort = insertDetectionPort;
		this.markOutlierPort = markOutlierPort;
	}

	@Override
	public DetectionSerie createSerie(int deviceId, int characteristicId) {
		// TODO: 15 non è abbastanza per l'autoAdjust
		return new CachedDetectionSerie(
			this.insertDetectionPort,
			this.findLastDetectionsPort.findLastDetections(deviceId, characteristicId, 15),
			this.markOutlierPort
		);
	}
}
