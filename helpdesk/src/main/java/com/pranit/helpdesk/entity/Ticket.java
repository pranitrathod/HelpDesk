package com.pranit.helpdesk.entity;
import com.pranit.helpdesk.model.*; import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="tickets") public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false) private String title; @Column(nullable=false,length=5000) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Priority priority=Priority.MEDIUM; @Enumerated(EnumType.STRING) @Column(nullable=false) private TicketStatus status=TicketStatus.OPEN;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private User createdBy; @ManyToOne(fetch=FetchType.LAZY) private User assignedTo;
 @Column(nullable=false) private LocalDateTime createdAt; private LocalDateTime updatedAt; private LocalDateTime resolvedAt;
 @PrePersist void prePersist(){createdAt=LocalDateTime.now();updatedAt=createdAt;} @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public Priority getPriority(){return priority;} public void setPriority(Priority v){priority=v;} public TicketStatus getStatus(){return status;} public void setStatus(TicketStatus v){status=v;} public User getCreatedBy(){return createdBy;} public void setCreatedBy(User v){createdBy=v;} public User getAssignedTo(){return assignedTo;} public void setAssignedTo(User v){assignedTo=v;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;} public LocalDateTime getResolvedAt(){return resolvedAt;} public void setResolvedAt(LocalDateTime v){resolvedAt=v;}
}
