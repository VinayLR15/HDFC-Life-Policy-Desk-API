package com.hdfclife.config;

import com.hdfclife.exception.ClaimNotFoundException;
import com.hdfclife.exception.DuplicatePolicyException;
import com.hdfclife.exception.InvalidClaimException;
import com.hdfclife.exception.PolicyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePolicyNotFound(
            PolicyNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(ClaimNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleClaimNotFound(
            ClaimNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(DuplicatePolicyException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatePolicy(
            DuplicatePolicyException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(InvalidClaimException.class)
    public ResponseEntity<Map<String, String>> handleInvalidClaim(
            InvalidClaimException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", exception.getMessage()));
    }
}