package it.deltax.produlytics.uibackend.web.exceptions;

import java.util.HashMap;

public class ResourceNotFoundException extends RuntimeException {

    public record ResourceNotFoundInfo(String reason, String resourceName, HashMap<String, Object> keys) {
    }

    ResourceNotFoundInfo info;

    public ResourceNotFoundException(String resourceName, HashMap<String, Object> keys) {
        this.info = new ResourceNotFoundInfo("Resource not found", resourceName, keys);
    }

    public ResourceNotFoundInfo getInfo() {
        return this.info;
    }
}
