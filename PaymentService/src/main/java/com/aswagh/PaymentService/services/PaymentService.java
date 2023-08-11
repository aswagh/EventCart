package com.aswagh.PaymentService.services;

import com.aswagh.PaymentService.models.PaymentReqeust;
import com.aswagh.PaymentService.models.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentReqeust paymentReqeust);

    PaymentResponse getPaymentDetails(Long orderID);


}
