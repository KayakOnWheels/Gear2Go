package com.gear2go.controller;

import com.gear2go.dto.request.product.CreateProductRequest;
import com.gear2go.dto.request.product.ProductAvailabilityInDateRangeRequest;
import com.gear2go.dto.request.product.UpdateProductRequest;
import com.gear2go.dto.response.ProductResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(productService.getProduct(id));
    }


    @PostMapping("/availability")
    public ResponseEntity<Integer> getProductAvailability(@RequestBody ProductAvailabilityInDateRangeRequest productAvailabilityInDateRangeRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(productService.checkProductAvailabilityInDateRange(productAvailabilityInDateRangeRequest));
    }


    @PostMapping("/add")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(productService.createProduct(createProductRequest));
    }


    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(productService.updateProduct(updateProductRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ExceptionWithHttpStatusCode{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
