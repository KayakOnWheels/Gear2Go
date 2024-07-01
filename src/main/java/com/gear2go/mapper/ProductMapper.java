package com.gear2go.mapper;

import com.gear2go.dto.response.ProductResponse;
import com.gear2go.entity.Product;

import java.util.List;

public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getWeight(),
                product.getPrice(),
                product.getStock()
        );
    }

    public List<ProductResponse> toProductList(List<Product> productList) {
        return productList.stream()
                .map(this::toProductResponse)
                .toList();
    }
}
