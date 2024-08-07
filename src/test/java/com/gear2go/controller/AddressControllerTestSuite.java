package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.address.CreateAddressRequest;
import com.gear2go.dto.request.address.UpdateAddressRequest;
import com.gear2go.dto.response.AddressResponse;
import com.gear2go.service.AddressService;
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
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressService addressService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void shouldReturnAddressList() throws Exception {
        //Given
        List<AddressResponse> addressResponseList = List.of(
                new AddressResponse(1L, "Sezamkowa", "12", "4", "99876", "Czesno"),
                new AddressResponse(2L, "Akacjowa", "13A", "5b", "12345", "Roten"));

        when(addressService.getAllAddresses()).thenReturn(addressResponseList);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/address/all")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].street").value("Sezamkowa"));
    }


    @Test
    void shouldGetAddressById() throws Exception {
        //Given
        List<AddressResponse> addressResponseList = List.of(
                new AddressResponse(1L, "Sezamkowa", "12", "4", "99876", "Czesno"),
                new AddressResponse(2L, "Akacjowa", "13A", "5b", "12345", "Roten"));

        when(addressService.getAddress(1L)).thenReturn(addressResponseList.get(0));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/address/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Sezamkowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildingNumber").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartmentNumber").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("99876"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Czesno"));
    }


    @Test
    void shouldGetCurrentUserAddress() throws Exception {
        //Given
        List<AddressResponse> addressResponseList = List.of(
                new AddressResponse(1L, "Sezamkowa", "12", "4", "99876", "Czesno"),
                new AddressResponse(2L, "Akacjowa", "13A", "5b", "12345", "Roten"));

        when(addressService.getAddress()).thenReturn(addressResponseList.get(0));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/address")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Sezamkowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buildingNumber").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apartmentNumber").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("99876"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Czesno"));
    }


    @Test
    void shouldReturnCreatedAddress() throws Exception {
        //Given
        CreateAddressRequest createAddressRequest = new CreateAddressRequest("Akacjowa", "13A", "5b", "12345", "Roten");
        AddressResponse addressResponse = new AddressResponse(2L, "Akacjowa", "13A", "5b", "12345", "Roten");
        when(addressService.createAddress(createAddressRequest)).thenReturn(addressResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/address/add")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(createAddressRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Akacjowa"));
    }


    @Test
    void shouldReturnUpdatedAddress() throws Exception {
        //Given
        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest(2L, "Akacjowa", "13A", "5b", "12345", "Roten");
        AddressResponse addressResponse = new AddressResponse(2L, "Akacjowa", "13A", "5b", "12345", "Roten");
        when(addressService.updateAddress(updateAddressRequest)).thenReturn(addressResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/address")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(updateAddressRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Akacjowa"));
    }


    @Test
    void shouldReturnStatus204AfterDelete() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/address/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
