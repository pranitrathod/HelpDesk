package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.domain.PaymentTransactionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public final class PaymentDtos {

  private PaymentDtos() {}

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CreatePaymentRequest {
    @NotBlank private String merchantReference;
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    @NotBlank @Pattern(regexp = "[A-Z]{3}") private String currency;
    @NotNull private PaymentGateway gateway;
  }

  @Getter
  @AllArgsConstructor
  public static class PaymentResponse {
    private final Long id;
    private final String merchantReference;
    private final String providerOrderId;
    private final String checkoutToken;
    private final BigDecimal amount;
    private final String currency;
    private final PaymentGateway gateway;
    private final PaymentTransactionStatus status;
  }
}
