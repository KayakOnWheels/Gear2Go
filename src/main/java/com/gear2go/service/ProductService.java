package com.gear2go.service;

import com.gear2go.dto.request.product.CreateProductRequest;
import com.gear2go.dto.request.product.UpdateProductRequest;
import com.gear2go.dto.response.ProductResponse;
import com.gear2go.entity.Product;
import com.gear2go.mapper.ProductMapper;
import com.gear2go.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return productMapper.toProductResponseList(productRepository.findAll());
    }

    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id).orElseThrow());
    }

    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product(
                createProductRequest.name(),
                createProductRequest.weight(),
                createProductRequest.price(),
                createProductRequest.stock()
        );

        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(updateProductRequest.id()).orElseThrow();

        product.setName(updateProductRequest.name());
        product.setWeight(updateProductRequest.weight());
        product.setPrice(updateProductRequest.price());
        product.setStock(updateProductRequest.stock());

        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }
}
