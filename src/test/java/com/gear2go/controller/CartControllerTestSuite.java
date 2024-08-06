package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.cart.AddProductToCartRequest;
import com.gear2go.dto.request.cart.UpdateCartRentDatesRequest;
import com.gear2go.dto.response.CartItemResponse;
import com.gear2go.dto.response.CartResponse;
import com.gear2go.service.CartService;
import com.gear2go.service.JwtService;
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
@WebMvcTest(CartController.class)
public class CartControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    CartResponse prepareData() {
        List<CartItemResponse> cartItemList = List.of(
                new CartItemResponse(1L, 2L, 3, BigDecimal.valueOf(4.99)),
                new CartItemResponse(2L, 32L, 5, BigDecimal.valueOf(34.43)));

        return new CartResponse(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(1),
                cartItemList, BigDecimal.valueOf(39.42));
    }

    @Test
    void shouldGetAllCarts() throws Exception {
        //Given
        List<CartResponse> cartResponseList = List.of(prepareData());

        when(cartService.getAllCarts()).thenReturn(cartResponseList);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/cart/all")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartItemResponseList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice").value("39.42"));
    }

    @Test
    void shouldGetCurrentUserCart() throws Exception {
        //Given
        CartResponse cartResponse = prepareData();

        when(cartService.getUserCart()).thenReturn(cartResponse);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/cart")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value("39.42"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList[0].price").value(4.99));
    }

    @Test
    void shouldReturnCartWithUpdatedDates() throws Exception {
        //Given
        CartResponse cartResponse = prepareData();
        UpdateCartRentDatesRequest request = new UpdateCartRentDatesRequest(LocalDate.now(), LocalDate.now().plusDays(1));

        when(cartService.updateCartRentDates(request)).thenReturn(cartResponse);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .put("/v1/cart/dates")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value("39.42"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList[0].price").value(4.99));
    }

    @Test
    void shouldReturnCartWithAddedProducts() throws Exception {
        //Given
        CartResponse cartResponse = prepareData();
        AddProductToCartRequest request = new AddProductToCartRequest(2L, 2);

        when(cartService.addOrSubtractProductToCart(request)).thenReturn(cartResponse);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .put("/v1/cart")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").value("39.42"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItemResponseList[0].price").value(4.99));
    }

}
