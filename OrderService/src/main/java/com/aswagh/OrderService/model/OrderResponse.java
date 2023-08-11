package com.aswagh.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private String orderStatus;
    private Long amount;
    private Instant orderDate;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ProductDetails {
        private Long productId;
        private String productName;
        private Long price;
        private Long quantity;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class PaymentDetails {

        private String paymentMode;
        private String referenceNumber;
        private Instant paymentDate;
        private String paymentStatus;
        private Long amount;
    }
}
