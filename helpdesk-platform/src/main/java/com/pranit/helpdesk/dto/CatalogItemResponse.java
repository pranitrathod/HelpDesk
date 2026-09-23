package com.pranit.helpdesk.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CatalogItemResponse {
  private final Long id;
  private final String code;
  private final String name;
  private final String description;
  private final String category;
  private final BigDecimal basePrice;
  private final boolean active;
}
