package com.gear2go.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gear2go.dto.request.AuthenticationRequest;
import com.gear2go.dto.request.RegisterRequest;
import com.gear2go.dto.request.user.PasswordRecoveryRequest;
import com.gear2go.dto.response.AuthenticationResponse;
import com.gear2go.service.AuthenticationService;
import com.gear2go.service.JwtService;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void shouldReturnToken() throws Exception {
        //Given
        RegisterRequest registerRequest = new RegisterRequest("Janko", "Muzykant", "jm@mail.com", "pass123");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("afasfbjklgaubc134dsg");

        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        //When & Then
        mockMvc.perform((MockMvcRequestBuilders
                        .post("/v1/auth/register")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("afasfbjklgaubc134dsg"));
    }


    @Test
    void shouldReturnTokenAfterRequestToAuthenticate() throws Exception {
        //Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("jm@mail.com", "pass123");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("afasfbjklgaubc134dsg");

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/authenticate")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("afasfbjklgaubc134dsg"));
    }


    @Test
    void shouldReturnTokenAfterRequestToAuthenticateGuest() throws Exception {
        //Given
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("afasfbjklgaubc134dsg");

        when(authenticationService.authenticateGuest()).thenReturn(authenticationResponse);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/authenticate-guest")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("afasfbjklgaubc134dsg"));
    }


    @Test
    void shouldReturnStringAfterRequestToRecoverPassword() throws Exception {
        //Given
        PasswordRecoveryRequest passwordRecoveryRequest = new PasswordRecoveryRequest("jdsmfiodjf123", "pass123", "jm@mail.com");

        when(authenticationService.recoverPassword(passwordRecoveryRequest)).thenReturn("Success");

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/auth/recover-password")
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRecoveryRequest)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().string("Success"));
    }

}
