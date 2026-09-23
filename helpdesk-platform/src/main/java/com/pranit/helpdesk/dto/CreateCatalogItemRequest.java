package com.pranit.helpdesk.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCatalogItemRequest {
  @NotBlank @Size(max = 80) private String code;
  @NotBlank @Size(max = 200) private String name;
  @NotBlank @Size(max = 2000) private String description;
  @NotBlank @Size(max = 100) private String category;
  @NotNull @DecimalMin("0.00") private BigDecimal basePrice;
}
