package com.pranit.productsupport.core.domain;
import com.pranit.productsupport.contracts.TicketStatus; import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="ticket_status_history") public class TicketStatusHistory {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="ticket_id") private Ticket ticket;
 @Enumerated(EnumType.STRING) @Column(name="old_status",length=30) private TicketStatus oldStatus;
 @Enumerated(EnumType.STRING) @Column(name="new_status",nullable=false,length=30) private TicketStatus newStatus;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="actor_id") private AppUser actor;
 @Column(length=1000) private String note;
 @Column(name="changed_at",nullable=false,updatable=false) private Instant changedAt;
 protected TicketStatusHistory(){} public TicketStatusHistory(Ticket t,TicketStatus oldStatus,TicketStatus newStatus,AppUser actor,String note){this.ticket=t;this.oldStatus=oldStatus;this.newStatus=newStatus;this.actor=actor;this.note=note;this.changedAt=Instant.now();}
 @PrePersist void prePersist(){if(changedAt==null)changedAt=Instant.now();}
}
