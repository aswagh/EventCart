package com.aswagh.PaymentService.services;

import com.aswagh.PaymentService.entities.Payment;
import com.aswagh.PaymentService.exceptions.CustomPaymentException;
import com.aswagh.PaymentService.models.PaymentReqeust;
import com.aswagh.PaymentService.models.PaymentResponse;
import com.aswagh.PaymentService.repos.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Long doPayment(PaymentReqeust paymentReqeust) {
        log.info("Proceeding with payment for order id {}",paymentReqeust.getOrderId());
        Payment payment = Payment.builder()
                .orderId(paymentReqeust.getOrderId())
                .paymentStatus("SUCCESS")
                .paymentDate(Instant.now())
                .referenceNumber(paymentReqeust.getReferenceNumber())
                .amount(paymentReqeust.getAmount())
                .paymentMode(paymentReqeust.getPaymentMode().name())
                .build();
        paymentRepository.save(payment);
        log.info("Payment Successfully");

        return payment.getId();
    }

    @Override
    public PaymentResponse getPaymentDetails(Long orderID) {
        Payment payment = paymentRepository.findByOrderId(orderID);

//                .orElseThrow(() -> new CustomPaymentException("Requested Payment Details not available ",
//                        "PAYMENT_HISTORY_NOT_FOUND"));
        PaymentResponse paymentResponse =
                PaymentResponse.builder()
                        .paymentStatus(payment.getPaymentStatus())
                        .paymentDate(payment.getPaymentDate())
                        .amount(payment.getAmount())
                        .paymentMode(payment.getPaymentMode())
                        .referenceNumber(payment.getReferenceNumber())
                        .build();
        return paymentResponse;
    }
}
