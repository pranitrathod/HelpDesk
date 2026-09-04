package com.pranit.helpdesk.domain;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="app_users",uniqueConstraints=@UniqueConstraint(name="uk_user_email",columnNames="email"))
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; @Column(nullable=false) private String email; @Column(nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role; @Column(nullable=false) private boolean active=true; @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 protected AppUser(){} public AppUser(String name,String email,String passwordHash,Role role){this.name=name;this.email=email;this.passwordHash=passwordHash;this.role=role;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public Role getRole(){return role;} public boolean isActive(){return active;}
}