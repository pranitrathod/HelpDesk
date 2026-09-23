package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketComment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ticket_id")
  private Ticket ticket;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author_id")
  private AppUser author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_comment_id")
  private TicketComment parentComment;

  @Column(nullable = false, length = 2000)
  private String message;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  public TicketComment(Ticket ticket, AppUser author, String message) {
    this.ticket = ticket;
    this.author = author;
    this.message = message;
  }

  public TicketComment(
      Ticket ticket, AppUser author, TicketComment parentComment, String message) {
    this.ticket = ticket;
    this.author = author;
    this.parentComment = parentComment;
    this.message = message;
  }
}
