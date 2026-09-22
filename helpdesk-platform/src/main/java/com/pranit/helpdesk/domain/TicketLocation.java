package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_locations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketLocation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ticket_id", unique = true)
  private Ticket ticket;

  @Column(nullable = false)
  private Double latitude;

  @Column(nullable = false)
  private Double longitude;

  @Column(length = 500)
  private String address;

  public TicketLocation(
      Ticket ticket, Double latitude, Double longitude, String address) {
    this.ticket = ticket;
    this.latitude = latitude;
    this.longitude = longitude;
    this.address = address;
  }
}
