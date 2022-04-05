package it.deltax.produlytics.uibackend.detections.business.domain;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;

public record DetectionFilters(
	Optional<Instant> olderThan, Optional<Instant> newerThan, OptionalInt limit
) {}
