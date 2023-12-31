package com.aswagh.OrderService.controllers;

import com.aswagh.OrderService.model.OrderRequest;
import com.aswagh.OrderService.model.OrderResponse;
import com.aswagh.OrderService.services.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("Received request body for order");
        Long orderID = orderService.placeOrder(orderRequest);
        log.info("Order is placed with Order ID:"+orderID);
        return new ResponseEntity<>(orderID, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable Long orderId){
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse,HttpStatus.FOUND);
    }
}
