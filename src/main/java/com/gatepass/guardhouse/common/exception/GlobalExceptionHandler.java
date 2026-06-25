package com.gatepass.guardhouse.common.exception;

import com.gatepass.guardhouse.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    //add methodArgumentNotValidException here

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.of(
                "GEN_002",
                "An Unexpected error occurred. Please contact the administrator"
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
