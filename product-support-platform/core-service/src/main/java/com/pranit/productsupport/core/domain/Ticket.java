package com.pranit.productsupport.core.domain;
import com.pranit.productsupport.contracts.TicketPriority; import com.pranit.productsupport.contracts.TicketStatus; import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="tickets") public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="customer_id") private AppUser customer;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="order_item_id") private OrderItem orderItem;
 @Column(name="issue_description",nullable=false,length=4000) private String issueDescription;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private TicketStatus status;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private TicketPriority priority;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assigned_to") private AppUser assignedTo;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @Version @Column(nullable=false) private long version;
 protected Ticket(){} public Ticket(AppUser customer,OrderItem orderItem,String issueDescription,TicketPriority priority){this.customer=customer;this.orderItem=orderItem;this.issueDescription=issueDescription;this.priority=priority==null?TicketPriority.MEDIUM:priority;this.status=TicketStatus.OPEN;this.createdAt=Instant.now();this.updatedAt=this.createdAt;}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();if(updatedAt==null)updatedAt=createdAt;}
 @PreUpdate void preUpdate(){updatedAt=Instant.now();}
 public Long getId(){return id;} public AppUser getCustomer(){return customer;} public OrderItem getOrderItem(){return orderItem;} public String getIssueDescription(){return issueDescription;} public TicketStatus getStatus(){return status;} public TicketPriority getPriority(){return priority;} public AppUser getAssignedTo(){return assignedTo;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public long getVersion(){return version;} public void setStatus(TicketStatus status){this.status=status;} public void setAssignedTo(AppUser user){this.assignedTo=user;}
}
