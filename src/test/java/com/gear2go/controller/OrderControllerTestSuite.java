package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.order.CreateOrderRequest;
import com.gear2go.dto.response.OrderResponse;
import com.gear2go.service.JwtService;
import com.gear2go.service.OrderService;
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
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@ExtendWith(MockitoExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldReturnOrderResponseListAndStatus200() throws Exception {
        //Given
        List<OrderResponse> orderResponseList = List.of(
                new OrderResponse(1L, LocalDate.now(), 2L, 3L),
                new OrderResponse(2L, LocalDate.now(), 3L, 4L)
        );

        when(orderService.getAllOrders()).thenReturn(orderResponseList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/order/all")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartId").value("3"));
    }

    @Test
    void shouldReturnOrderResponseAndStatus200() throws Exception {
        //Given
        List<OrderResponse> orderResponseList = List.of(
                new OrderResponse(1L, LocalDate.now(), 2L, 3L),
                new OrderResponse(2L, LocalDate.now(), 3L, 4L)
        );

        when(orderService.getOrder(1L)).thenReturn(orderResponseList.get(0));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/order/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").value("3"));
    }

    @Test
    void shouldReturnCreatedProductAndStatus200() throws Exception {
        //Given
        List<OrderResponse> orderResponseList = List.of(
                new OrderResponse(1L, LocalDate.now(), 2L, 3L),
                new OrderResponse(2L, LocalDate.now(), 3L, 4L)
        );

        CreateOrderRequest request = new CreateOrderRequest(1L, 2L);

        when(orderService.createOrderFromCart(request)).thenReturn(orderResponseList.get(0));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/order")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").value("3"));
    }


    @Test
    void shouldReturnStatus204AfterDelete() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/order/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }
}
