package com.pranit.helpdesk.exception;

public class PaymentGatewayException extends RuntimeException {
  public PaymentGatewayException(String message, Throwable cause) {
    super(message, cause);
  }
}
