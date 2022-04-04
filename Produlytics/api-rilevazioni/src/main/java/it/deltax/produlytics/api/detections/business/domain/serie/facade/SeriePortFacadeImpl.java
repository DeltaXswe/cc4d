package it.deltax.produlytics.api.detections.business.domain.serie.facade;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.List;

public class SeriePortFacadeImpl implements SeriePortFacade {
	private final InsertDetectionPort insertDetectionPort;
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final MarkOutlierPort markOutlierPort;

	public SeriePortFacadeImpl(
		InsertDetectionPort insertDetectionPort,
		FindLastDetectionsPort findLastDetectionsPort,
		MarkOutlierPort markOutlierPort
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.markOutlierPort = markOutlierPort;
	}

	@Override
	public void insertDetection(Detection detection) {
		this.insertDetectionPort.insertDetection(detection);
	}

	@Override
	public List<Detection> findLastDetections(CharacteristicId characteristicId, int count) {
		return this.findLastDetectionsPort.findLastDetections(characteristicId, count);
	}

	@Override
	public void markOutlier(Detection detection) {
		this.markOutlierPort.markOutlier(detection);
	}
}