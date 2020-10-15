package com.arch.stock.resource.errors;

import java.io.Serializable;
import java.util.Map;

public class ParameterizedErrorVM implements Serializable {

    private final String message;
    private final Map<String, String> paramMap;

    public ParameterizedErrorVM(String message, Map<String, String> paramMap) {
        this.message = message;
        this.paramMap = paramMap;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getParams() {
        return paramMap;
    }

}
