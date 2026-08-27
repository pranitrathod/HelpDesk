package com.crh.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "replacements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Replacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;

    private String courierPartner;

    @OneToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;
}
