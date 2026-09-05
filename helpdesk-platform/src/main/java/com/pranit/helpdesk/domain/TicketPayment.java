package com.pranit.helpdesk.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "ticket_payments")
public class TicketPayment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "ticket_id", unique = true) private Ticket ticket;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(nullable = false, length = 3) private String currency;
    @Column(nullable = false, unique = true) private String reference;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private PaymentStatus status;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected TicketPayment() { }
    public TicketPayment(Ticket ticket, BigDecimal amount, String currency, String reference) { this.ticket=ticket; this.amount=amount; this.currency=currency; this.reference=reference; this.status=PaymentStatus.PENDING; }
    public Long getId(){return id;} public BigDecimal getAmount(){return amount;} public String getCurrency(){return currency;} public String getReference(){return reference;} public PaymentStatus getStatus(){return status;}
    public void markAuthorized(){status=PaymentStatus.AUTHORIZED;} public void markRefunded(){status=PaymentStatus.REFUNDED;}
}
