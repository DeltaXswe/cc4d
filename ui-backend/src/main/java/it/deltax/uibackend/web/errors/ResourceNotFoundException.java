package it.deltax.uibackend.web.errors;

import java.util.HashMap;

public class ResourceNotFoundException extends Exception {

    public final String resourceName;
    public final HashMap<String, Object> keys;

    public ResourceNotFoundException(String resourceName, HashMap<String, Object> keys) {
        this.resourceName = resourceName;
        this.keys = keys;
    }

    public String reason() {
        return String.format("""
                Resource %s with keys %s could not be found
                """,
                resourceName,
                keys
        );
    }
}
