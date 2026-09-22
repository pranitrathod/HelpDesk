package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentTransaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String merchantReference;

  @Column(nullable = false, unique = true, length = 100)
  private String idempotencyKey;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentGateway gateway;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentTransactionStatus status = PaymentTransactionStatus.CREATED;

  @Column(length = 120)
  private String providerOrderId;

  @Column(length = 1000)
  private String checkoutToken;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  public PaymentTransaction(
      String merchantReference,
      String idempotencyKey,
      BigDecimal amount,
      String currency,
      PaymentGateway gateway) {
    this.merchantReference = merchantReference;
    this.idempotencyKey = idempotencyKey;
    this.amount = amount;
    this.currency = currency;
    this.gateway = gateway;
  }

  public void markPending(String providerOrderId, String checkoutToken) {
    this.providerOrderId = providerOrderId;
    this.checkoutToken = checkoutToken;
    this.status = PaymentTransactionStatus.PENDING;
  }

  public void markSucceeded() {
    status = PaymentTransactionStatus.SUCCEEDED;
  }

  public void markFailed() {
    status = PaymentTransactionStatus.FAILED;
  }
}
