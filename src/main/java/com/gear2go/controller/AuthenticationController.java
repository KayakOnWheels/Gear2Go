package com.gear2go.controller;

import com.gear2go.dto.request.AuthenticationRequest;
import com.gear2go.dto.request.RegisterRequest;
import com.gear2go.dto.request.RequestPasswordRecoveryRequest;
import com.gear2go.dto.request.user.PasswordRecoveryRequest;
import com.gear2go.dto.response.AuthenticationResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @SecurityRequirements
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }


    @SecurityRequirements
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }


    @SecurityRequirements
    @PostMapping("/authenticate-guest")
    public ResponseEntity<AuthenticationResponse> registerGuest() {
        return ResponseEntity.ok(authenticationService.authenticateGuest());
    }


    @PostMapping("/recover-password")
    public ResponseEntity<String> recover(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(authenticationService.recoverPassword(passwordRecoveryRequest));
    }


    @SecurityRequirements
    @PostMapping("/request-recovery")
    public ResponseEntity<String> sendRecoveryMail(@RequestBody RequestPasswordRecoveryRequest requestPasswordRecoveryRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(authenticationService.sendRecoveryMail(requestPasswordRecoveryRequest));
    }

}
