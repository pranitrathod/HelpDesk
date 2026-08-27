package com.pranit.helpdesk.HelpDesk.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="comment")
public class CommentsDetails {

    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name="id",nullable = false)
    @JsonIgnore
    private UserDetails user;

    @ManyToOne
    @JoinColumn(name="ticket_id",nullable = false)
    @JsonIgnore
    private TicketDetails ticket;

    @Column(name="message")
    private String message;

    @CreationTimestamp
    private LocalDateTime created_at;


}
