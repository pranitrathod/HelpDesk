package com.pranit.helpdesk.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingRequestHeaderException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  ResponseEntity<ApiError> notFound(ResourceNotFoundException exception) {
    return response(
        HttpStatus.NOT_FOUND,
        "RESOURCE_NOT_FOUND",
        exception.getMessage(),
        null);
  }

  @ExceptionHandler(ConflictException.class)
  ResponseEntity<ApiError> conflict(ConflictException exception) {
    return response(
        HttpStatus.CONFLICT,
        "CONFLICT",
        exception.getMessage(),
        null);
  }

  @ExceptionHandler(InvalidTicketStateException.class)
  ResponseEntity<ApiError> invalidTicketState(InvalidTicketStateException exception) {
    return response(
        HttpStatus.CONFLICT,
        "INVALID_TICKET_STATE",
        exception.getMessage(),
        null);
  }

  @ExceptionHandler(PaymentGatewayException.class)
  ResponseEntity<ApiError> paymentGateway(PaymentGatewayException exception) {
    return response(
        HttpStatus.BAD_GATEWAY,
        "PAYMENT_GATEWAY_ERROR",
        exception.getMessage(),
        null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiError> validation(MethodArgumentNotValidException exception) {
    Map<String, String> fields = new LinkedHashMap<>();
    exception.getBindingResult()
        .getFieldErrors()
        .forEach(error -> fields.put(error.getField(), error.getDefaultMessage()));

    return response(
        HttpStatus.BAD_REQUEST,
        "VALIDATION_ERROR",
        "Request validation failed",
        fields);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  ResponseEntity<ApiError> malformedRequest(HttpMessageNotReadableException exception) {
    return response(
        HttpStatus.BAD_REQUEST,
        "MALFORMED_REQUEST",
        "Request body is malformed or contains an invalid value",
        null);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  ResponseEntity<ApiError> missingHeader(MissingRequestHeaderException exception) {
    return response(
        HttpStatus.BAD_REQUEST,
        "MISSING_REQUEST_HEADER",
        "Required request header '" + exception.getHeaderName() + "' is missing",
        null);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<ApiError> invalidParameter(MethodArgumentTypeMismatchException exception) {
    return response(
        HttpStatus.BAD_REQUEST,
        "INVALID_PARAMETER",
        "Request parameter '" + exception.getName() + "' has an invalid value",
        null);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiError> unexpected(Exception exception) {
    log.error("Unhandled exception while processing request", exception);

    return response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "INTERNAL_SERVER_ERROR",
        "An unexpected error occurred",
        null);
  }

  private ResponseEntity<ApiError> response(
      HttpStatus status,
      String code,
      String message,
      Map<String, String> validationErrors) {
    return ResponseEntity
        .status(status)
        .body(new ApiError(status.value(), code, message, validationErrors));
  }
}
