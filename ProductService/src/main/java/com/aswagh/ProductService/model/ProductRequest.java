package com.aswagh.ProductService.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductRequest {

    private String productName;
    private Long price;
    private Long quantity;
}
