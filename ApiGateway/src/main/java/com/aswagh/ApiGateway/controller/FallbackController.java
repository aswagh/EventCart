package com.aswagh.ApiGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/orderServiceFallback")
    public String orderServiceFallback(){
        return "Sorry! Order Service is Currently Down.";
    }
    @GetMapping("/productServiceFallback")
    public String productServiceFallback(){
        return "Sorry! Product Service is Currently Down.";
    }
    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback(){
        return "Sorry! Payment Service is Currently Down.";
    }
}
