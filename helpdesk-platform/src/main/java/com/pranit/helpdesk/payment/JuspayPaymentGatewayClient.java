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
public class JuspayPaymentGatewayClient implements PaymentGatewayClient {
  private final PaymentGatewayProperties.Juspay properties;

  public JuspayPaymentGatewayClient(PaymentGatewayProperties properties) {
    this.properties = properties.getJuspay();
  }

  @Override
  public PaymentGateway gateway() {
    return PaymentGateway.JUSPAY;
  }

  @Override
  public GatewayPaymentOrder createOrder(
      String merchantReference, BigDecimal amount, String currency) {
    try {
      JsonNode response =
          RestClient.create(properties.getBaseUrl())
              .post()
              .uri("/session")
              .contentType(MediaType.APPLICATION_JSON)
              .headers(headers -> {
                headers.setBasicAuth(properties.getApiKey(), "");
                headers.set("x-merchantid", properties.getMerchantId());
              })
              .body(Map.of("order_id", merchantReference, "amount", amount.toPlainString(),
                  "currency", currency, "customer_id", merchantReference))
              .retrieve()
              .body(JsonNode.class);
      if (response == null || response.path("order_id").isMissingNode()) {
        throw new PaymentGatewayException("Juspay did not return an order id", null);
      }
      return new GatewayPaymentOrder(response.path("order_id").asText(),
          response.path("id").asText(response.path("order_id").asText()));
    } catch (Exception exception) {
      throw new PaymentGatewayException("Unable to create a Juspay payment session", exception);
    }
  }

  @Override
  public boolean verifyWebhook(String payload, String signature) {
    return PaymentSignatureVerifier.verifiesHmacSha256(payload, signature, properties.getApiKey());
  }
}
