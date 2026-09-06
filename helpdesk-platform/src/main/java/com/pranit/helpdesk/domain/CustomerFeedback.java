package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "customer_feedback")
public class CustomerFeedback {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(nullable = false, unique = true) private Long ticketId;
  @Column(nullable = false) private Long customerId;
  @Column(nullable = false) private int rating;
  @Column(length = 2000) private String comment;
  @Column(nullable = false, updatable = false) private Instant submittedAt = Instant.now();

  protected CustomerFeedback() {}
  public CustomerFeedback(Long ticketId, Long customerId, int rating, String comment) {
    this.ticketId = ticketId;
    this.customerId = customerId;
    this.rating = rating;
    this.comment = comment;
  }
  public Long getId() {
    return id;
  }
  public Long getTicketId() {
    return ticketId;
  }
  public Long getCustomerId() {
    return customerId;
  }
  public int getRating() {
    return rating;
  }
  public String getComment() {
    return comment;
  }
  public Instant getSubmittedAt() {
    return submittedAt;
  }
}
