package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.List;
import java.util.OptionalLong;

public record Detections(
	List<Detection> detections, OptionalLong nextOld, long nextNew
) {}
