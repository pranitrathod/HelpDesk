package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.PaymentGateway;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePaymentRequest {
  @NotBlank private String merchantReference;
  @NotNull @DecimalMin("0.01") private BigDecimal amount;
  @NotBlank @Pattern(regexp = "[A-Z]{3}") private String currency;
  @NotNull private PaymentGateway gateway;
}
