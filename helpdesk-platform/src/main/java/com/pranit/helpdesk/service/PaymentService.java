package com.pranit.helpdesk.service;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.dto.PaymentDtos.CreatePaymentRequest;
import com.pranit.helpdesk.dto.PaymentDtos.PaymentResponse;

public interface PaymentService {
  PaymentResponse createCheckout(String idempotencyKey, CreatePaymentRequest request);
  void processWebhook(PaymentGateway gateway, String payload, String signature);
}
