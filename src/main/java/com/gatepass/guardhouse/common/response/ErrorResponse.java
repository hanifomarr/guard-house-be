package com.gatepass.guardhouse.common.response;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final List<String> errors;
    private final Instant timestamp;

    private ErrorResponse(String code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, List.of());
    }

    public static ErrorResponse of(String code, String message, List<String> errors) {
        return new ErrorResponse(code, message, errors);
    }
}
