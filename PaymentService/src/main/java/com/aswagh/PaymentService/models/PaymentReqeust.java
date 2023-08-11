package com.aswagh.PaymentService.models;

import lombok.Data;

@Data
public class PaymentReqeust {

    private Long orderId;
    private String referenceNumber;
    private Long amount;
    private PaymentMode paymentMode;
}
