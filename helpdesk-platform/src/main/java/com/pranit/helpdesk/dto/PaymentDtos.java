package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.PaymentGateway;
import com.pranit.helpdesk.domain.PaymentTransactionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public final class PaymentDtos {
  private PaymentDtos() {}

  public static class CreatePaymentRequest {
    @NotBlank private String merchantReference;
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    @NotBlank @Pattern(regexp = "[A-Z]{3}") private String currency;
    @NotNull private PaymentGateway gateway;
    public String getMerchantReference() {
      return merchantReference;
    }
    public void setMerchantReference(String value) {
      merchantReference = value;
    }
    public BigDecimal getAmount() {
      return amount;
    }
    public void setAmount(BigDecimal value) {
      amount = value;
    }
    public String getCurrency() {
      return currency;
    }
    public void setCurrency(String value) {
      currency = value;
    }
    public PaymentGateway getGateway() {
      return gateway;
    }
    public void setGateway(PaymentGateway value) {
      gateway = value;
    }
  }

  public static class PaymentResponse {
    public Long id;
    public String merchantReference;
    public String providerOrderId;
    public String checkoutToken;
    public BigDecimal amount;
    public String currency;
    public PaymentGateway gateway;
    public PaymentTransactionStatus status;
    public PaymentResponse(Long id, String merchantReference, String providerOrderId,
        String checkoutToken, BigDecimal amount, String currency, PaymentGateway gateway,
        PaymentTransactionStatus status) {
      this.id = id;
      this.merchantReference = merchantReference;
      this.providerOrderId = providerOrderId;
      this.checkoutToken = checkoutToken;
      this.amount = amount;
      this.currency = currency;
      this.gateway = gateway;
      this.status = status;
    }
  }
}
