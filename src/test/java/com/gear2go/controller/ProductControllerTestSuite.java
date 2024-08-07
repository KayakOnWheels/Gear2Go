package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.product.CreateProductRequest;
import com.gear2go.dto.request.product.ProductAvailabilityInDateRangeRequest;
import com.gear2go.dto.request.product.UpdateProductRequest;
import com.gear2go.dto.response.ProductResponse;
import com.gear2go.service.JwtService;
import com.gear2go.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void shouldReturnProductResponseListAndStatus200() throws Exception {
        //Given
        List<ProductResponse> productResponseList = List.of(
                new ProductResponse(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
                new ProductResponse(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));

        when(productService.getAllProducts()).thenReturn(productResponseList);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/product")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("p1"));
    }

    @Test
    void shouldReturnProductResponseListAndStatus200AfterGetProductByIdRequest() throws Exception {
        //Given
        List<ProductResponse> productResponseList = List.of(
                new ProductResponse(1L, "p1", 12.2F, new BigDecimal("12.5"), 12),
                new ProductResponse(2L, "p2", 15.1F, new BigDecimal("2.5"), 4));

        when(productService.getProduct(1L)).thenReturn(productResponseList.get(0));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/product/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("p1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(12.2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(12));
    }


    @Test
    void shouldReturnProductAvailabilityAndStatus200() throws Exception {
        //Given
        ProductAvailabilityInDateRangeRequest request = new ProductAvailabilityInDateRangeRequest(1L, LocalDate.now(), LocalDate.now().plusDays(1));

        when(productService.checkProductAvailabilityInDateRange(request)).thenReturn(4);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/availability")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("4"));
    }

    @Test
    void shouldReturnProductResponseAndStatus200AfterCreateProductRequest() throws Exception {
        //Given
        CreateProductRequest createProductRequest = new CreateProductRequest("Product1", 123.45F, new BigDecimal("12.5"), 12);
        ProductResponse productResponse = new ProductResponse(1L, "Product1", 123.45F, new BigDecimal("12.5"), 12);

        when(productService.createProduct(createProductRequest)).thenReturn(productResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/product/add")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(createProductRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(123.45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(12));
    }

    @Test
    void shouldReturnProductResponseAndStatus200AfterUpdateProductRequest() throws Exception {
        //Given
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(1L, "Prod123", 1.2F, new BigDecimal("13.5"), 12);
        ProductResponse productResponse = new ProductResponse(1L, "Prod123", 1.2F, new BigDecimal("13.5"), 12);

        when(productService.updateProduct(updateProductRequest)).thenReturn(productResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/product/update")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(updateProductRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Prod123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weight").value(1.2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(13.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(12));
    }


    @Test
    void shouldReturnStatus204AfterDelete() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/product/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
