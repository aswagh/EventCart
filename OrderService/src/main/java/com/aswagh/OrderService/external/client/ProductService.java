package com.aswagh.OrderService.external.client;

import com.aswagh.OrderService.exceptions.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {

    @PutMapping("/reduceQuantity/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long productId, @RequestParam Long quantity);

    default void fallbakc(Exception e){
        throw new CustomException(
        "Product service is currenty down",
                "PRODUCT_SERVICE_DOWN",
                500);
    }
}
