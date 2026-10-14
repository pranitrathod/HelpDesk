package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="products") public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=80,unique=true) private String sku;
 @Column(nullable=false,length=160) private String name;
 @Column(length=2000) private String description;
 @Column(nullable=false) private boolean active=true;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 protected Product(){} public Product(String sku,String name,String description){this.sku=sku;this.name=name;this.description=description;this.active=true;this.createdAt=Instant.now();}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();}
 public Long getId(){return id;} public String getSku(){return sku;} public String getName(){return name;} public String getDescription(){return description;} public boolean isActive(){return active;}
}
