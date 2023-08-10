package com.aswagh.OrderService.model;

import lombok.Data;

import java.time.Instant;

@Data
public class OrderRequest {

    private Long productId;
    private Long quantity;
    private Long totalAmount;
    private PaymentMode paymentMode;
}
