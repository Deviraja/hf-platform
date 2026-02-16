package com.example.hf.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    var msg = ex.getBindingResult().getAllErrors().stream()
        .findFirst().map(e -> e.getDefaultMessage()).orElse("Validation error");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ApiError(Instant.now(), 400, "Bad Request", msg, req.getRequestURI())
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ApiError(Instant.now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI())
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new ApiError(Instant.now(), 500, "Internal Server Error", ex.getMessage(), req.getRequestURI())
    );
  }
}
