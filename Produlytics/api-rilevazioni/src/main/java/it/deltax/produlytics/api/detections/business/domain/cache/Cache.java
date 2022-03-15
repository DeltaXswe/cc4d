package it.deltax.produlytics.api.detections.business.domain.cache;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.MarkableDetection;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Una finestra sulle ultime rilevazioni di una caratteristica con il supporto per marcarle.
class Cache {
	private final FindLastDetectionsPort findLastDetectionsPort;
	private final MarkOutlierPort markOutlierPort;
	private final int deviceId;
	private final int characteristicId;

	private final Deque<CachedDetection> detections;
	private int maxSize;

	Cache(
		FindLastDetectionsPort findLastDetectionsPort,
		MarkOutlierPort markOutlierPort,
		int deviceId,
		int characteristicId
	) {
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.markOutlierPort = markOutlierPort;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
		this.detections = new ArrayDeque<>();
		this.maxSize = 0;
	}

	private void enqueueDetection(Detection detection) {
		this.detections.addLast(new CachedDetection(detection));
	}

	private MarkableDetection makeMarkableDetection(CachedDetection cachedDetection) {
		return new MarkableCachedDetectionAdaper(cachedDetection, this.markOutlierPort);
	}

	// Ripopola `detections` con le rilevazioni memorizzate.
	void updateSize(int size) {
		if(size > maxSize) {
			List<Detection> newDetections = this.findLastDetectionsPort.findLastDetections(this.deviceId,
				this.characteristicId,
				size
			);
			newDetections.forEach(this::enqueueDetection);
		} else if(size < maxSize) {
			while(detections.size() > size) {
				detections.removeFirst();
			}
		}

		this.maxSize = size;
	}

	// Ritorna uno stream delle ultime `count` rilevazioni (o meno, se non sono presenti).
	Stream<MarkableDetection> lastNDetections(int count) {
		int toSkip = this.detections.size() - Math.min(count, this.detections.size());
		return this.detections.stream().skip(toSkip).map(this::makeMarkableDetection);
	}

	void insertDetection(Detection detection) {
		this.enqueueDetection(detection);

		if(this.detections.size() > this.maxSize) {
			this.detections.removeFirst();
		}
	}
}
