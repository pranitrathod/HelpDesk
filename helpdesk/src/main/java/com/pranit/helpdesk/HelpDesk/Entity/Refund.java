package com.crh.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String transactionId;

    private String paymentGateway;

    @OneToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    private LocalDateTime processedAt;
}
