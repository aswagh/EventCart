package com.aswagh.OrderService.external.request;

import com.aswagh.OrderService.model.PaymentMode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentReqeust {

    private Long orderId;
    private String referenceNumber;
    private Long amount;
    private PaymentMode paymentMode;
}
