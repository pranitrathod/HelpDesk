package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.domain.PaymentTransactionStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
  private final Long id;
  private final String merchantReference;
  private final String providerOrderId;
  private final String checkoutToken;
  private final BigDecimal amount;
  private final String currency;
  private final PaymentGateway gateway;
  private final PaymentTransactionStatus status;
}
