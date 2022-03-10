package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

public class DetectionCacheFactoryImpl implements DetectionCacheFactory {
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final InsertDetectionPort insertDetectionPort;
	private final MarkOutlierPort markOutlierPort;

	public DetectionCacheFactoryImpl(
		FindLastDetectionsPort findLastDetectionsPort,
		InsertDetectionPort insertDetectionPort,
		MarkOutlierPort markOutlierPort
	) {
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.insertDetectionPort = insertDetectionPort;
		this.markOutlierPort = markOutlierPort;
	}

	@Override
	public DetectionCache createCache(int deviceId, int characteristicId) {
		// TODO: 15 non è abbastanza per l'autoAdjust
		return new DetectionCacheImpl(
			this.insertDetectionPort,
			this.findLastDetectionsPort.findLastDetections(deviceId, characteristicId, 15),
			this.markOutlierPort
		);
	}
}
