package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.PaymentStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketPaymentResponse {
  private final Long id;
  private final BigDecimal amount;
  private final String currency;
  private final String reference;
  private final PaymentStatus status;
}
