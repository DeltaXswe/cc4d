package it.deltax.produlytics.uibackend.detections.business.domain;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public record Detections(
	List<Detection> detections, Optional<Instant> nextOld, Instant nextNew
) {}
