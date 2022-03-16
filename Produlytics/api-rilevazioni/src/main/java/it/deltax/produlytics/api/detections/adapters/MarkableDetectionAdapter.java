package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.MarkableDetection;
import it.deltax.produlytics.api.repositories.DetectionRepository;

import java.time.Instant;

public class MarkableDetectionAdapter implements MarkableDetection {
	private final DetectionRepository detectionRepository;
	private final int deviceId;
	private final int characteristicId;
	private final Instant creationTime;
	private final double value;

	public MarkableDetectionAdapter(
		DetectionRepository detectionRepository, int deviceId, int characteristicId, Instant creationTime, double value
	) {
		this.detectionRepository = detectionRepository;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
		this.creationTime = creationTime;
		this.value = value;
	}

	@Override
	public double value() {
		return this.value;
	}

	@Override
	public void markOutlier() {
		this.detectionRepository.markOutlier(this.deviceId, this.characteristicId, this.creationTime);
	}
}
