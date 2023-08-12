package com.aswagh.OrderService.external.client;

import com.aswagh.OrderService.exceptions.CustomException;
import com.aswagh.OrderService.external.request.PaymentReqeust;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker( name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping("/doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentReqeust paymentReqeust);

    default void fallback(Exception e){
        throw new CustomException(
                "Payment service is currenty down",
                "PAYMENT_SERVICE_DOWN",
                500);
    }
}


