package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="ticket_comments") public class TicketComment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id") private Ticket ticket;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="author_id") private AppUser author;
 @Enumerated(EnumType.STRING) @Column(name="author_role",nullable=false,length=30) private UserRole authorRole;
 @Column(nullable=false,length=5000) private String content;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 protected TicketComment(){} public TicketComment(Ticket t,AppUser a,UserRole role,String content){this.ticket=t;this.author=a;this.authorRole=role;this.content=content;this.createdAt=Instant.now();}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();} public Long getId(){return id;} public Ticket getTicket(){return ticket;} public AppUser getAuthor(){return author;} public UserRole getAuthorRole(){return authorRole;} public String getContent(){return content;} public Instant getCreatedAt(){return createdAt;}
}
