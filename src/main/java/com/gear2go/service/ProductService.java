package com.gear2go.service;

import com.gear2go.domain.dto.request.product.CreateProductRequest;
import com.gear2go.domain.dto.request.product.ProductAvailabilityInDateRangeRequest;
import com.gear2go.domain.dto.request.product.UpdateProductRequest;
import com.gear2go.domain.dto.response.ProductResponse;
import com.gear2go.entity.Product;
import com.gear2go.mapper.ProductMapper;
import com.gear2go.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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


    public Integer checkProductAvailabilityInDateRange(Long id, LocalDate rentDate, LocalDate returnDate) {
        Product product = productRepository.findById(id).orElseThrow();

        Optional<Integer> rentedInDataRange = productRepository.findNumberOfProductsRentedInDataRange(id, rentDate, returnDate);

        if(rentedInDataRange.isEmpty()) {
            rentedInDataRange = Optional.of(0);
        }
        return product.getStock() - rentedInDataRange.get();
    }

    public Integer checkProductAvailabilityInDateRange(ProductAvailabilityInDateRangeRequest productAvailabilityInDateRangeRequest) {
        Product product = productRepository.findById(productAvailabilityInDateRangeRequest.productId()).orElseThrow();

        Optional<Integer> rentedInDataRange = productRepository.findNumberOfProductsRentedInDataRange(
                product.getId(), productAvailabilityInDateRangeRequest.rentDate(), productAvailabilityInDateRangeRequest.returnDate());

        if(rentedInDataRange.isEmpty()) {
            rentedInDataRange = Optional.of(0);
        }
        return product.getStock() - rentedInDataRange.get();
    }
}
