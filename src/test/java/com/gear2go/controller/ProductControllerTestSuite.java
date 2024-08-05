//package com.gear2go.controller;
//
//import com.gear2go.service.JwtService;
//import com.gear2go.dto.response.ProductResponse;
//import com.gear2go.entity.Product;
//import com.gear2go.service.ProductService;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
//@SpringJUnitWebConfig
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(ProductController.class)
//public class ProductControllerTestSuite {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private ProductService productService;
//    @MockBean
//    private JwtService jwtService;
//
//    @Test
//    void shouldGetAllProducts() throws Exception {
//        //Given
//        List<Product> productList = List.of(
//                new Product(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
//                new Product(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));
//
//        List<ProductResponse> productResponseList = List.of(
//                new ProductResponse(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
//                new ProductResponse(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));
//
//        when(productService.getAllProducts()).thenReturn(productResponseList);
//
//        //When & Then
//        mockMvc.perform((MockMvcRequestBuilders.get("/v1/product").contentType(MediaType.APPLICATION_JSON)))
//                .andExpect(MockMvcResultMatchers.status().is(200))
//                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
//    }
//
//    @Test
//    void shouldGetProductById() throws Exception {
//        //Given
//        List<Product> productList = List.of(
//                new Product(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
//                new Product(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));
//
//
//        List<ProductResponse> productResponseList = List.of(
//                new ProductResponse(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
//                new ProductResponse(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));
//
//
//        when(productService.getProduct(1L)).thenReturn(productResponseList.get(0));
//
//        //When & Then
//        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("p1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(12.2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12.5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(12));
//    }
//
//
//}
