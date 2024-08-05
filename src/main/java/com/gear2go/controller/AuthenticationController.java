package com.gear2go.controller;

import com.gear2go.dto.request.AuthenticationRequest;
import com.gear2go.dto.request.RegisterRequest;
import com.gear2go.dto.request.user.PasswordRecoveryRequest;
import com.gear2go.dto.response.AuthenticationResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/authenticate-guest")
    public ResponseEntity<AuthenticationResponse> registerGuest() {
        return ResponseEntity.ok(authenticationService.authenticateGuest());
    }

    @PostMapping("/recover-password")
    public ResponseEntity<String> recover(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(authenticationService.recoverPassword(passwordRecoveryRequest));
    }

}
