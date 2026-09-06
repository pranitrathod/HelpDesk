package com.pranit.helpdesk.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "audit_logs")
public class AuditLog {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false) private String action;
  @Column(nullable = false) private Long ticketId;
  @Column(nullable = false) private Long actorId;
  @Column(nullable = false, length = 2000) private String details;
  @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
  protected AuditLog() {}
  public AuditLog(String action, Long ticketId, Long actorId, String details) {
    this.action = action;
    this.ticketId = ticketId;
    this.actorId = actorId;
    this.details = details;
  }
}