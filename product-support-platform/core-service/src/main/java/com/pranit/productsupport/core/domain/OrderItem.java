package com.pranit.productsupport.core.domain;
import jakarta.persistence.*;
@Entity @Table(name="order_items") public class OrderItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="order_id") private PurchaseOrder order;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id") private Product product;
 @Column(nullable=false) private int quantity;
 protected OrderItem(){} public OrderItem(PurchaseOrder order,Product product,int quantity){this.order=order;this.product=product;this.quantity=quantity;}
 public Long getId(){return id;} public PurchaseOrder getOrder(){return order;} public Product getProduct(){return product;} public int getQuantity(){return quantity;} void setOrder(PurchaseOrder order){this.order=order;}
}
