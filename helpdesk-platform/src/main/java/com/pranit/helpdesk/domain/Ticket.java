package com.pranit.helpdesk.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "tickets",
    indexes =
    {
      @Index(name = "idx_ticket_status", columnList = "status")
      , @Index(name = "idx_ticket_assignee", columnList = "assignee_id")
    })
public class Ticket {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false) private String title;
  @Column(nullable = false, length = 4000) private String description;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private Priority priority;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private TicketStatus status;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "requester_id")
  private AppUser requester;
  @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "assignee_id") private AppUser assignee;
  @Column(nullable = false, updatable = false) private Instant createdAt;
  @Column(nullable = false) private Instant updatedAt;
  @Column(nullable = false) private Instant slaDueAt;
  protected Ticket() {}
  public Ticket(
      String title, String description, Priority priority, AppUser requester, Instant slaDueAt) {
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
  public Long getId() {
    return id;
  }
  public String getTitle() {
    return title;
  }
  public String getDescription() {
    return description;
  }
  public Priority getPriority() {
    return priority;
  }
  public TicketStatus getStatus() {
    return status;
  }
  public AppUser getRequester() {
    return requester;
  }
  public AppUser getAssignee() {
    return assignee;
  }
  public Instant getCreatedAt() {
    return createdAt;
  }
  public Instant getUpdatedAt() {
    return updatedAt;
  }
  public Instant getSlaDueAt() {
    return slaDueAt;
  }
  public void assign(AppUser user) {
    assignee = user;
    status = TicketStatus.IN_PROGRESS;
  }
  public void changeStatus(TicketStatus value) {
    status = value;
  }
}