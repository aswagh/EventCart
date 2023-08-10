package com.aswagh.ProductService.services;

import com.aswagh.ProductService.entities.Product;
import com.aswagh.ProductService.exceptions.ProductServiceCustomException;
import com.aswagh.ProductService.model.ProductRequest;
import com.aswagh.ProductService.model.ProductResponse;
import com.aswagh.ProductService.repos.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public Long addProduct(ProductRequest productRequest) {

        Product product =
                Product.builder()
                        .productName(productRequest.getProductName())
                        .price(productRequest.getPrice())
                        .quantity(productRequest.getQuantity())
                        .build();
        productRepository.save(product);

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductServiceCustomException("The product with given ID: "+productId+" is not present ","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();

        BeanUtils.copyProperties(product,productResponse);

        return productResponse;
    }
}
