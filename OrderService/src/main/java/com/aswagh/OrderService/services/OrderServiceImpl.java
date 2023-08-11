package com.aswagh.OrderService.services;

import com.aswagh.OrderService.entities.Order;
import com.aswagh.OrderService.external.client.ProductService;
import com.aswagh.OrderService.model.OrderRequest;
import com.aswagh.OrderService.repos.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

//    Order Entity    -> save order data with status as Order Created
//    Product Entitu  -> Block product ( Reduce quantity)
//    Payment Enitity -> Payment-> successfyl Or else

    @Override
    public Long placeOrder(OrderRequest orderRequest) {

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Placing new order after reducing product quantiy in Product Service");
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .amount(orderRequest.getTotalAmount())
                .build();
        order = orderRepository.save(order);
        log.info("Oder is placed successfully with Order ID"+order.getId());
        return order.getId();
    }
}
