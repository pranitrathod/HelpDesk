package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="service_quotes") public class ServiceQuote {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id") private Ticket ticket;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="created_by") private AppUser createdBy;
 @Column(name="amount_paise",nullable=false) private long amountPaise;
 @Column(nullable=false,length=3) private String currency="INR";
 @Column(nullable=false,length=500) private String description;
 @Column(nullable=false,length=20) private String status="OPEN";
 @Column(name="expires_at",nullable=false) private Instant expiresAt;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 protected ServiceQuote(){} public ServiceQuote(Ticket t,AppUser actor,long amount,String description,Instant expiresAt){this.ticket=t;this.createdBy=actor;this.amountPaise=amount;this.description=description;this.expiresAt=expiresAt;this.createdAt=Instant.now();this.currency="INR";this.status="OPEN";}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();} public Long getId(){return id;} public Ticket getTicket(){return ticket;} public AppUser getCreatedBy(){return createdBy;} public long getAmountPaise(){return amountPaise;} public String getCurrency(){return currency;} public String getDescription(){return description;} public String getStatus(){return status;} public Instant getExpiresAt(){return expiresAt;} public Instant getCreatedAt(){return createdAt;} public void markPaid(){status="PAID";} public void cancel(){status="CANCELLED";}
}
