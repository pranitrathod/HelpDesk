package com.pranit.helpdesk.payment;

import com.pranit.helpdesk.domain.PaymentGateway;
import java.math.BigDecimal;

public interface PaymentGatewayClient {
  PaymentGateway gateway();
  GatewayPaymentOrder createOrder(String merchantReference, BigDecimal amount, String currency);
  boolean verifyWebhook(String payload, String signature);
}
