package com.pranit.helpdesk.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
    Instant timestamp,
    int status,
    String code,
    String message,
    Map<String, String> validationErrors) {

  public ApiError(
      int status,
      String code,
      String message,
      Map<String, String> validationErrors) {
    this(Instant.now(), status, code, message, validationErrors);
  }
}
