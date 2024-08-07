package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.RequestPasswordRecoveryRequest;
import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.service.JwtService;
import com.gear2go.service.UserService;
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
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldReturnUserResponseListAndStatus200() throws Exception {
        //Given
        List<UserResponse> userResponseList = List.of(
                new UserResponse(1L, "Anthonio", "Antonello", "aa@mail.com"),
                new UserResponse(2L, "Lisa", "Lawn", "ll@mail.com"));

        when(userService.getAllUsers()).thenReturn(userResponseList);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/user")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Anthonio"));
    }


    @Test
    void shouldReturnUserResponseAndStatus200AfterGetUserByIdRequest() throws Exception {
        //Given
        List<UserResponse> userResponseList = List.of(
                new UserResponse(1L, "Anthonio", "Antonello", "aa@mail.com"),
                new UserResponse(2L, "Lisa", "Lawn", "ll@mail.com"));

        when(userService.getUser(2L)).thenReturn(userResponseList.get(1));

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .get("/v1/user/2")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Lawn"));
    }


    @Test
    void shouldReturnStringAndStatus200AfterSendRecoveryMailRequest() throws Exception {
        //Given
        RequestPasswordRecoveryRequest request = new RequestPasswordRecoveryRequest("ll@mail.com");

        when(userService.sendRecoveryMail(request)).thenReturn("Recovery mail has been sent");

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .post("/v1/user/request-recovery")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("Recovery mail has been sent"));
    }


    @Test
    void shouldReturnUserResponseAndStatus200AfterRegisterUserRequest() throws Exception {
        //Given
        List<UserResponse> userResponseList = List.of(
                new UserResponse(1L, "Anthonio", "Antonello", "aa@mail.com"),
                new UserResponse(2L, "Lisa", "Lawn", "ll@mail.com"));

        CreateUserRequest request = new CreateUserRequest("Anthonio", "Antonello", "aa@mail.com", "Pass123");

        when(userService.createUser(request)).thenReturn(userResponseList.get(0));

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .post("/v1/user/register")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Anthonio"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Antonello"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value("aa@mail.com"));
    }


    @Test
    void shouldReturnUserResponseAndStatus200AfterUpdateUserRequest() throws Exception {
        //Given
        List<UserResponse> userResponseList = List.of(
                new UserResponse(1L, "Anthonio", "Antonello", "aa@mail.com"),
                new UserResponse(2L, "Lisa", "Lawn", "ll@mail.com"));

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(null, "Anthonio", "Antonello", "aa@mail.com", "Pass123");

        when(userService.updateUser(updateUserRequest)).thenReturn(userResponseList.get(0));

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .put("/v1/user/update")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .content(objectMapper.writeValueAsString(updateUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Anthonio"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Antonello"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value("aa@mail.com"));
    }


    @Test
    void shouldReturnStatus204AfterDelete() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void shouldReturnStatus204AfterDeleteById() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/1")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
