package com.pranit.helpdesk.entity;
import com.pranit.helpdesk.model.Role;
import jakarta.persistence.*;
@Entity @Table(name="users") public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String username;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String password;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role=Role.USER;
 public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPassword(){return password;} public void setPassword(String v){password=v;} public Role getRole(){return role;} public void setRole(Role v){role=v;}
}
