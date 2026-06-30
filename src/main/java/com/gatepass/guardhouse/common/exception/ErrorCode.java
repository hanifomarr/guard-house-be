package com.gatepass.guardhouse.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


    // User errors
    USER_NOT_FOUND("USER_001", "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("USER_002", "User already exists", HttpStatus.CONFLICT),
    USER_SUSPENDED("USER_003", "User account is suspended", HttpStatus.FORBIDDEN),

    // Auth errors
    INVALID_CREDENTIALS("AUTH_001", "Invalid email or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUTH_002", "You are not authorized", HttpStatus.UNAUTHORIZED),

    // Visitor errors
    VISITOR_NOT_FOUND("VIS_001", "Visitor not found", HttpStatus.NOT_FOUND),
    VISITOR_QR_FAILED("VIS_002", "Visitor QR code has failed", HttpStatus.BAD_REQUEST),
    VISITOR_QR_EXPIRED("VIS_003", "Visitor QR code has expired", HttpStatus.BAD_REQUEST),
    VISITOR_ALREADY_CANCELLED("VIS_004", "Visitor has already been cancelled", HttpStatus.BAD_REQUEST),
    VISITOR_NOT_APPROVED("VIS_005", "Visitor is not approved for entry", HttpStatus.FORBIDDEN),

    // General
    VALIDATION_FAILED("GEN_001", "Validation failed", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("GEN_002", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
