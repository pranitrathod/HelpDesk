package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant; import java.util.ArrayList; import java.util.List;
@Entity @Table(name="customer_orders") public class PurchaseOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="customer_id") private AppUser customer;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private OrderStatus status;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true) private List<OrderItem> items=new ArrayList<>();
 protected PurchaseOrder(){} public PurchaseOrder(AppUser customer,OrderStatus status){this.customer=customer;this.status=status;this.createdAt=Instant.now();}
 public void addItem(OrderItem item){items.add(item);item.setOrder(this);}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();}
 public Long getId(){return id;} public AppUser getCustomer(){return customer;} public OrderStatus getStatus(){return status;} public Instant getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return items;} public void setStatus(OrderStatus status){this.status=status;}
}
