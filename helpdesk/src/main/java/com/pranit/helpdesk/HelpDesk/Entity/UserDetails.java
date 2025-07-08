package com.pranit.helpdesk.HelpDesk.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetails {

@Id
@Column(name="Id")
    private int id;

    @Column(name="user_Id")
    private Long userId;

    @Column(name="name")
    private String userName;

    @Column(name="email_address")
    private String email;

    @Column(name="location")
    private String location;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<TicketDetails> tickets;
}
