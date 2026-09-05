package com.pranit.helpdesk.domain;
import jakarta.persistence.*;
@Entity @Table(name="ticket_locations")
public class TicketLocation {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id",unique=true) private Ticket ticket;
 @Column(nullable=false) private Double latitude; @Column(nullable=false) private Double longitude; @Column(length=500) private String address;
 protected TicketLocation(){} public TicketLocation(Ticket ticket,Double latitude,Double longitude,String address){this.ticket=ticket;this.latitude=latitude;this.longitude=longitude;this.address=address;}
 public Double getLatitude(){return latitude;} public Double getLongitude(){return longitude;} public String getAddress(){return address;}
}
