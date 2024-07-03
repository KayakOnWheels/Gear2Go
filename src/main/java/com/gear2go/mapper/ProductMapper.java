package com.gear2go.mapper;

import com.gear2go.dto.response.ProductResponse;
import com.gear2go.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<ProductResponse> toProductResponseList(List<Product> productList) {
        return productList.stream()
                .map(this::toProductResponse)
                .toList();
    }
}
