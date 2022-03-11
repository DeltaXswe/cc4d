package it.deltax.produlytics.api.detections.business.domain;

public record IncomingDetection(String apiKey, int characteristicId, double value) {}
