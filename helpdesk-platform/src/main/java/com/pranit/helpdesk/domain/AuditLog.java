package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audit_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String action;

  @Column(nullable = false)
  private Long ticketId;

  @Column(nullable = false)
  private Long actorId;

  @Column(nullable = false, length = 2000)
  private String details;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  public AuditLog(String action, Long ticketId, Long actorId, String details) {
    this.action = action;
    this.ticketId = ticketId;
    this.actorId = actorId;
    this.details = details;
  }
}
