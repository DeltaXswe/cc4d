package it.deltax.produlytics.api.detections.business.domain.serie;

import it.deltax.produlytics.api.detections.business.domain.*;
import it.deltax.produlytics.api.detections.business.domain.control_chart.ControlChart;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;

import java.util.List;

public class DetectionSerieImpl implements DetectionSerie {
	private final InsertDetectionPort insertDetectionPort;
	private final FindLimitsPort findLimitsPort;
	private final FindLastDetectionsPort findLastDetectionsPort;

	private final List<ControlChart> controlCharts;

	private final int deviceId;
	private final int characteristicId;

	DetectionSerieImpl(
		InsertDetectionPort insertDetectionPort,
		FindLimitsPort findLimitsPort,
		FindLastDetectionsPort findLastDetectionsPort,
		List<ControlChart> controlCharts,
		int deviceId,
		int characteristicId
	) {
		this.insertDetectionPort = insertDetectionPort;
		this.findLimitsPort = findLimitsPort;
		this.findLastDetectionsPort = findLastDetectionsPort;
		this.controlCharts = controlCharts;
		this.deviceId = deviceId;
		this.characteristicId = characteristicId;
	}

	@Override
	public void insertDetection(Detection detection) {
		this.insertDetectionPort.insertDetection(detection);
		List<MarkableDetection> lastDetections = this.detectionsForControlCharts();
		ControlLimits controlLimits = this.computeControlLimits();
		for(ControlChart controlChart : this.controlCharts) {
			int count = controlChart.requiredDetectionCount();
			if(lastDetections.size() >= count) {
				controlChart.analyzeDetections(this.cutLastDetections(lastDetections, count), controlLimits);
			}
		}
	}

	private List<MarkableDetection> detectionsForControlCharts() {
		return this.findLastDetectionsPort.findLastDetections(this.deviceId, this.characteristicId, 15);
	}

	private ControlLimits computeControlLimits() {
		LimitsInfo limitsInfo = this.findLimitsPort.findLimits(this.deviceId, this.characteristicId);
		if(limitsInfo.meanStddev().isPresent()) {
			MeanStddev meanStddev = limitsInfo.meanStddev().get();
			double lowerLimit = meanStddev.mean() - 3 * meanStddev.stddev();
			double upperLimit = meanStddev.mean() + 3 * meanStddev.stddev();
			return new ControlLimits(lowerLimit, upperLimit);
		} else if(limitsInfo.technicalLimits().isPresent()) {
			TechnicalLimits technicalLimits = limitsInfo.technicalLimits().get();
			return new ControlLimits(technicalLimits.lowerLimit(), technicalLimits.upperLimit());
		} else {
			throw new IllegalStateException("Non sono impostati nè i limiti nè l'auto-adjust");
		}
	}

	private List<MarkableDetection> cutLastDetections(List<MarkableDetection> lastDetections, int count) {
		return lastDetections.subList(lastDetections.size() - count, lastDetections.size());
	}
}
