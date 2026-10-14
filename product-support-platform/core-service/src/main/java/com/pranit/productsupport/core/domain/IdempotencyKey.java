package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="idempotency_keys",uniqueConstraints=@UniqueConstraint(name="uk_idempotency_user_key",columnNames={"user_id","idempotency_key"})) public class IdempotencyKey {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id") private AppUser user;
 @Column(name="idempotency_key",nullable=false,length=100) private String key;
 @Column(name="request_hash",nullable=false,length=64) private String requestHash;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="ticket_id") private Ticket ticket;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 protected IdempotencyKey(){} public IdempotencyKey(AppUser user,String key,String hash){this.user=user;this.key=key;this.requestHash=hash;this.createdAt=Instant.now();}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();}
 public void complete(Ticket ticket){this.ticket=ticket;} public String getRequestHash(){return requestHash;} public Ticket getTicket(){return ticket;}
}
