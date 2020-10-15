package com.arch.stock.resource.errors;

import java.io.Serializable;

/**
 * View Model for transferring error message with a list of field errors.
 */
public class ErrorApplication implements Serializable {

    private final String message;
    private final String description;

    public ErrorApplication(String message) {
        this(message, null);
    }

    public ErrorApplication(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
