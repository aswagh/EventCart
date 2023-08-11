package com.aswagh.PaymentService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ORDER_ID")
    private Long orderId;
    private String paymentMode;
    private String referenceNumber;
    @Column(name = "DATE")
    private Instant paymentDate;
    @Column(name = "STATUS")
    private String paymentStatus;
    @Column(name = "AMOUNT_PAID")
    private Long amount;
;
}
