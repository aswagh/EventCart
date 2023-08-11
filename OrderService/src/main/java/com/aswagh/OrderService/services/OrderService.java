package com.aswagh.OrderService.services;

import com.aswagh.OrderService.model.OrderRequest;
import com.aswagh.OrderService.model.OrderResponse;

public interface OrderService {

    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
