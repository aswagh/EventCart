package com.aswagh.ProductService.services;

import com.aswagh.ProductService.entities.Product;
import com.aswagh.ProductService.exceptions.ProductServiceCustomException;
import com.aswagh.ProductService.model.ProductRequest;
import com.aswagh.ProductService.model.ProductResponse;
import com.aswagh.ProductService.repos.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
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

    @Override
    public void reduceQuntity(Long productId, Long quntity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                ()-> new ProductServiceCustomException("Product not found with given ID:{"+productId+"}",
                        "PRODUCT_NOT_FOUND"));

        if (product.getQuantity()< quntity){
            throw new ProductServiceCustomException(
                    "Sorry! only "+product.getQuantity()+" products are available",
                    "INSUFFICIENT_PRODUCTS");
        }

        product.setQuantity(product.getQuantity() - quntity);
        productRepository.save(product);

        log.info("Quntity has been reduced for product {} to {} by {}",product.getProductName(),product.getQuantity(),quntity);
    }
}
