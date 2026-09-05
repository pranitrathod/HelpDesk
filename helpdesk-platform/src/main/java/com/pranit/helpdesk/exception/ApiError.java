package com.pranit.helpdesk.exception;
import java.time.Instant;
import java.util.Map;
public class ApiError {
  public final Instant timestamp = Instant.now();
  public final int status;
  public final String code, message;
  public final Map<String, String> validationErrors;
  public ApiError(int s, String c, String m, Map<String, String> e) {
    status = s;
    code = c;
    message = m;
    validationErrors = e;
  }
}
