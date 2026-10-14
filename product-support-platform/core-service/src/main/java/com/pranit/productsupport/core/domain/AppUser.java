package com.pranit.productsupport.core.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="app_users") public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="full_name",nullable=false,length=120) private String fullName;
 @Column(nullable=false,length=254,unique=true) private String email;
 @Column(name="password_hash",nullable=false,length=100) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private UserRole role;
 @Column(nullable=false) private boolean active=true;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 protected AppUser() {}
 public AppUser(String fullName,String email,String passwordHash,UserRole role){this.fullName=fullName;this.email=email;this.passwordHash=passwordHash;this.role=role;this.active=true;this.createdAt=Instant.now();}
 @PrePersist void prePersist(){if(createdAt==null)createdAt=Instant.now();}
 public Long getId(){return id;} public String getFullName(){return fullName;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public UserRole getRole(){return role;} public boolean isActive(){return active;} public void setRole(UserRole role){this.role=role;}
}
