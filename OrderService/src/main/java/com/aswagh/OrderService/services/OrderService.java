package com.aswagh.OrderService.services;

import com.aswagh.OrderService.model.OrderRequest;

public interface OrderService {

    Long placeOrder(OrderRequest orderRequest);
}
