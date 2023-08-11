package com.aswagh.PaymentService.controllers;

import com.aswagh.PaymentService.models.PaymentReqeust;
import com.aswagh.PaymentService.models.PaymentResponse;
import com.aswagh.PaymentService.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentReqeust paymentReqeust){
        Long paymentId = paymentService.doPayment(paymentReqeust);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable Long orderID){
        PaymentResponse paymentResponse =
                paymentService.getPaymentDetails(orderID);
        return new ResponseEntity<>(paymentResponse,HttpStatus.FOUND);
    }
}
