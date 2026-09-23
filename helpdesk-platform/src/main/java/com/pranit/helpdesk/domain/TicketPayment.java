package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketPayment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ticket_id", unique = true)
  private Ticket ticket;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, unique = true)
  private String reference;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  public TicketPayment(
      Ticket ticket, BigDecimal amount, String currency, String reference) {
    this.ticket = ticket;
    this.amount = amount;
    this.currency = currency;
    this.reference = reference;
    this.status = PaymentStatus.PENDING;
  }

  public void markAuthorized() {
    status = PaymentStatus.AUTHORIZED;
  }

  public void markRefunded() {
    status = PaymentStatus.REFUNDED;
  }
}
