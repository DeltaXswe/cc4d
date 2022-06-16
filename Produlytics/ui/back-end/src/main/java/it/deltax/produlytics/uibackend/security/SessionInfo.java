package it.deltax.produlytics.uibackend.security;

public record SessionInfo(
    String username,
    boolean admin
) {}
