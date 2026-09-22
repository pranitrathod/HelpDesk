package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "tickets",
    indexes = {
      @Index(name = "idx_ticket_status", columnList = "status"),
      @Index(name = "idx_ticket_assignee", columnList = "assignee_id")
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 4000)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Priority priority;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TicketStatus status;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "requester_id")
  private AppUser requester;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id")
  private AppUser assignee;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @Column(nullable = false)
  private Instant slaDueAt;

  public Ticket(
      String title,
      String description,
      Priority priority,
      AppUser requester,
      Instant slaDueAt) {
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.requester = requester;
    this.slaDueAt = slaDueAt;
    this.status = TicketStatus.OPEN;
    this.createdAt = Instant.now();
    this.updatedAt = createdAt;
  }

  @PreUpdate
  void touch() {
    updatedAt = Instant.now();
  }

  public void assign(AppUser user) {
    assignee = user;
    status = TicketStatus.IN_PROGRESS;
  }

  public void changeStatus(TicketStatus value) {
    status = value;
  }
}
