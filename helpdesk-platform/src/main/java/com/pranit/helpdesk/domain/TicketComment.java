package com.pranit.helpdesk.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="ticket_comments") public class TicketComment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id") private Ticket ticket;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="author_id") private AppUser author;
 @Column(nullable=false,length=2000) private String message; @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 protected TicketComment(){} public TicketComment(Ticket t,AppUser a,String m){ticket=t;author=a;message=m;}
 public Long getId(){return id;} public AppUser getAuthor(){return author;} public String getMessage(){return message;} public Instant getCreatedAt(){return createdAt;}
}