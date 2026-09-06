package com.pranit.helpdesk.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.pranit.helpdesk.config.PaymentGatewayProperties;
import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.exception.PaymentGatewayException;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RazorpayPaymentGatewayClient implements PaymentGatewayClient {
  private final PaymentGatewayProperties.Razorpay properties;

  public RazorpayPaymentGatewayClient(PaymentGatewayProperties properties) {
    this.properties = properties.getRazorpay();
  }

  @Override
  public PaymentGateway gateway() {
    return PaymentGateway.RAZORPAY;
  }

  @Override
  public GatewayPaymentOrder createOrder(
      String merchantReference, BigDecimal amount, String currency) {
    try {
      JsonNode response =
          RestClient.create(properties.getBaseUrl())
              .post()
              .uri("/v1/orders")
              .contentType(MediaType.APPLICATION_JSON)
              .headers(
                  headers -> headers.setBasicAuth(properties.getKeyId(), properties.getKeySecret()))
              .body(Map.of("amount", amount.movePointRight(2).longValueExact(), "currency",
                  currency, "receipt", merchantReference, "payment_capture", 1))
              .retrieve()
              .body(JsonNode.class);
      if (response == null || response.path("id").isMissingNode()) {
        throw new PaymentGatewayException("Razorpay did not return an order id", null);
      }
      return new GatewayPaymentOrder(response.path("id").asText(), response.path("id").asText());
    } catch (Exception exception) {
      throw new PaymentGatewayException("Unable to create a Razorpay order", exception);
    }
  }

  @Override
  public boolean verifyWebhook(String payload, String signature) {
    return PaymentSignatureVerifier.verifiesHmacSha256(
        payload, signature, properties.getKeySecret());
  }
}
