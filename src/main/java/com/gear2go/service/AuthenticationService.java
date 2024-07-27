package com.gear2go.service;

import com.gear2go.dto.request.AuthenticationRequest;
import com.gear2go.dto.request.RegisterRequest;
import com.gear2go.dto.response.AuthenticationResponse;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationResponse register (RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .build();

        return null;
    }

    public AuthenticationResponse authenticate (AuthenticationRequest authenticationRequest) {
        return null;
    }
}
