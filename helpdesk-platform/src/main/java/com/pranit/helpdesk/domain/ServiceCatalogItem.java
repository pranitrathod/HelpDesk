package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "service_catalog_items")
public class ServiceCatalogItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Column(nullable = false, unique = true, length = 80) private String code;

  @Column(nullable = false, length = 200) private String name;

  @Column(nullable = false, length = 2000) private String description;

  @Column(nullable = false, length = 100) private String category;

  @Column(nullable = false, precision = 12, scale = 2) private BigDecimal basePrice;

  @Column(nullable = false) private boolean active = true;

  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();

  protected ServiceCatalogItem() {}

  public ServiceCatalogItem(
      String code, String name, String description, String category, BigDecimal basePrice) {
    this.code = code;
    this.name = name;
    this.description = description;
    this.category = category;
    this.basePrice = basePrice;
  }

  public Long getId() {
    return id;
  }
  public String getCode() {
    return code;
  }
  public String getName() {
    return name;
  }
  public String getDescription() {
    return description;
  }
  public String getCategory() {
    return category;
  }
  public BigDecimal getBasePrice() {
    return basePrice;
  }
  public boolean isActive() {
    return active;
  }
  public Instant getCreatedAt() {
    return createdAt;
  }
  public void retire() {
    active = false;
  }
}
