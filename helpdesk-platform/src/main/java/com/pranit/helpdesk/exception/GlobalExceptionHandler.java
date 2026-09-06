package com.pranit.helpdesk.exception;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<Map<String, String>> notFound(ResourceNotFoundException e) {
    return response(HttpStatus.NOT_FOUND, e.getMessage());
  }
  @ExceptionHandler(ConflictException.class)
  ResponseEntity<Map<String, String>> conflict(ConflictException e) {
    return response(HttpStatus.CONFLICT, e.getMessage());
  }
  @ExceptionHandler(PaymentGatewayException.class)
  ResponseEntity<Map<String, String>> gatewayFailure(PaymentGatewayException e) {
    return response(HttpStatus.BAD_GATEWAY, "The payment provider could not process the request");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Map<String, Object>> invalid(MethodArgumentNotValidException e) {
    Map<String, String> fields = new LinkedHashMap<>();
    e.getBindingResult().getFieldErrors().forEach(
        error -> fields.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(
        Map.of("message", "Request validation failed", "fields", fields));
  }
  private ResponseEntity<Map<String, String>> response(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(Map.of("message", message));
  }
}
