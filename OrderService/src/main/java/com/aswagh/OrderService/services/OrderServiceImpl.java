package com.aswagh.OrderService.services;

import com.aswagh.OrderService.entities.Order;
import com.aswagh.OrderService.exceptions.CustomException;
import com.aswagh.OrderService.external.client.PaymentService;
import com.aswagh.OrderService.external.client.ProductService;
import com.aswagh.OrderService.external.request.PaymentReqeust;
import com.aswagh.OrderService.external.response.ProductResponse;
import com.aswagh.OrderService.model.OrderRequest;
import com.aswagh.OrderService.model.OrderResponse;
import com.aswagh.OrderService.model.PaymentResponse;
import com.aswagh.OrderService.repos.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

//    Order Entity    -> save order data with status as Order Created
//    Product Entity  -> Block product ( Reduce quantity)
//    Payment Entity -> Payment-> successful Or else

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
        log.info("Calling payment service - doPayment");

        PaymentReqeust paymentReqeust
                = PaymentReqeust.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(order.getAmount())
                .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentReqeust);
            orderStatus="PLACED";
        }catch (Exception e){
            log.error("Error occcured in payememt service : changing status to FAILED");
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Oder is placed successfully with Order ID"+order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new CustomException("The requested order with ID {"+orderId+"} is not in database",
                        "NOT_FOUND",
                        404)
        );
        log.info("calling restemplate to get product details");
        ProductResponse productResponse
                = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/"+order.getProductId(),
                ProductResponse.class);

        OrderResponse.ProductDetails productDetails =
                OrderResponse.ProductDetails.builder()
                        .productId(productResponse.getProductId())
                        .price(productResponse.getPrice())
                        .productName(productResponse.getProductName())
                        .quantity(productResponse.getQuantity())
                        .build();
        log.info("calling restemplate to get Payment details");
        PaymentResponse paymentResponse =
                restTemplate.getForObject(
                        "http://PAYMENT-SERVICE/payment/order/"+order.getId(),
                        PaymentResponse.class);

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(OrderResponse.PaymentDetails.builder()
                        .paymentDate(paymentResponse.getPaymentDate())
                        .paymentStatus(paymentResponse.getPaymentStatus())
                        .paymentMode(paymentResponse.getPaymentMode())
                        .build())
                .build();
        return orderResponse;
    }
}
