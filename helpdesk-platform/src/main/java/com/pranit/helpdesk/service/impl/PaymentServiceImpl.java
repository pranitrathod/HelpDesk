package com.pranit.helpdesk.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.domain.PaymentTransaction;
import com.pranit.helpdesk.dto.CreatePaymentRequest;
import com.pranit.helpdesk.dto.PaymentResponse;
import com.pranit.helpdesk.exception.ConflictException;
import com.pranit.helpdesk.exception.PaymentGatewayException;
import com.pranit.helpdesk.exception.ResourceNotFoundException;
import com.pranit.helpdesk.payment.GatewayPaymentOrder;
import com.pranit.helpdesk.payment.PaymentGatewayClient;
import com.pranit.helpdesk.payment.PaymentGatewayRegistry;
import com.pranit.helpdesk.repository.PaymentTransactionRepository;
import com.pranit.helpdesk.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentTransactionRepository transactions;
  private final PaymentGatewayRegistry gateways;
  private final ObjectMapper objectMapper;

  @Override
  public PaymentResponse createCheckout(
      String idempotencyKey, CreatePaymentRequest request) {
    return transactions.findByIdempotencyKey(idempotencyKey)
        .map(this::response)
        .orElseGet(() -> {
          PaymentTransaction transaction =
              transactions.save(
                  new PaymentTransaction(
                      request.getMerchantReference(),
                      idempotencyKey,
                      request.getAmount(),
                      request.getCurrency(),
                      request.getGateway()));

          PaymentGatewayClient gateway = gateways.get(request.getGateway());
          GatewayPaymentOrder order = gateway.createOrder(
              request.getMerchantReference(),
              request.getAmount(),
              request.getCurrency());

          transaction.markPending(order.getProviderOrderId(), order.getCheckoutToken());
          return response(transaction);
        });
  }

  @Override
  public void processWebhook(
      PaymentGateway gateway, String payload, String signature) {
    PaymentGatewayClient client = gateways.get(gateway);

    if (!client.verifyWebhook(payload, signature)) {
      throw new ConflictException("Payment webhook signature is invalid");
    }

    try {
      JsonNode event = objectMapper.readTree(payload);
      String providerOrderId =
          event.at("/payload/payment/entity/order_id")
              .asText(event.path("order_id").asText());

      if (providerOrderId.isBlank()) {
        throw new PaymentGatewayException(
            "Payment webhook does not contain an order id", null);
      }

      PaymentTransaction transaction =
          transactions.findByProviderOrderId(providerOrderId)
              .orElseThrow(
                  () -> new ResourceNotFoundException(
                      "Payment order", providerOrderId));

      String eventName = event.path("event").asText(
          event.path("status").asText());

      if (eventName.contains("captured")
          || eventName.equalsIgnoreCase("CHARGED")) {
        transaction.markSucceeded();
      }

      if (eventName.contains("failed")
          || eventName.equalsIgnoreCase("FAILED")) {
        transaction.markFailed();
      }
    } catch (PaymentGatewayException exception) {
      throw exception;
    } catch (Exception exception) {
      throw new PaymentGatewayException(
          "Unable to process payment webhook", exception);
    }
  }

  private PaymentResponse response(PaymentTransaction transaction) {
    return new PaymentResponse(
        transaction.getId(),
        transaction.getMerchantReference(),
        transaction.getProviderOrderId(),
        transaction.getCheckoutToken(),
        transaction.getAmount(),
        transaction.getCurrency(),
        transaction.getGateway(),
        transaction.getStatus());
  }
}
