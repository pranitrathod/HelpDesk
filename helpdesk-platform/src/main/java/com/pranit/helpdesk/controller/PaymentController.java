package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.dto.CreatePaymentRequest;
import com.pranit.helpdesk.dto.PaymentResponse;
import com.pranit.helpdesk.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/checkout")
  public ResponseEntity<PaymentResponse> createCheckout(
      @RequestHeader("Idempotency-Key") String idempotencyKey,
      @Valid @RequestBody CreatePaymentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(paymentService.createCheckout(idempotencyKey, request));
  }

  @PostMapping("/webhooks/razorpay")
  public ResponseEntity<Void> razorpayWebhook(
      @RequestHeader("X-Razorpay-Signature") String signature,
      @RequestBody String payload) {
    paymentService.processWebhook(PaymentGateway.RAZORPAY, payload, signature);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/webhooks/juspay")
  public ResponseEntity<Void> juspayWebhook(
      @RequestHeader("x-juspay-signature") String signature,
      @RequestBody String payload) {
    paymentService.processWebhook(PaymentGateway.JUSPAY, payload, signature);
    return ResponseEntity.ok().build();
  }
}
