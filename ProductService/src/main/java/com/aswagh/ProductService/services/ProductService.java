package com.aswagh.ProductService.services;

import com.aswagh.ProductService.model.ProductRequest;
import com.aswagh.ProductService.model.ProductResponse;

public interface ProductService {

    public Long addProduct(ProductRequest productRequest);

    public ProductResponse getProductById(Long productId);
}
