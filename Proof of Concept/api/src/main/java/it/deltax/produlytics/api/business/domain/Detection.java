package it.deltax.produlytics.api.business.domain;

public record Detection(long macchina, String caratteristica, double value, boolean anomalo, long creazioneUtc) {
    
}
