package com.gear2go.service;

import com.gear2go.dto.request.AuthenticationRequest;
import com.gear2go.dto.request.RegisterRequest;
import com.gear2go.dto.request.user.PasswordRecoveryRequest;
import com.gear2go.dto.response.AuthenticationResponse;
import com.gear2go.entity.User;
import com.gear2go.entity.enums.Role;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.exception.TokenExpiredException;
import com.gear2go.exception.UserNotFoundException;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .mail(registerRequest.getMail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws ExceptionWithHttpStatusCode {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getMail(),
                        authenticationRequest.getPassword())
        );
        var user = userRepository.findUserByMail(authenticationRequest.getMail()).orElseThrow(UserNotFoundException::new);
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticateGuest() {
        User user = new User(RandomStringUtils.randomAlphanumeric(20) + "@mail.com ", passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(20)), Role.GUEST);
        userRepository.save(user);

        var token = jwtService.generateToken(user);
        user.setExpirationDate(jwtService.extractExpiration(token));

        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public String recoverPassword(PasswordRecoveryRequest passwordRecoveryRequest) throws ExceptionWithHttpStatusCode {
        User temporaryUser = userRepository.findUserByMail(jwtService.extractUsername(passwordRecoveryRequest.token())).orElseThrow(UserNotFoundException::new);

        if (jwtService.isTokenValid(passwordRecoveryRequest.token(), temporaryUser)) {
            User userToRecover = userRepository.findUserByMail(passwordRecoveryRequest.mail()).orElseThrow(UserNotFoundException::new);

            userToRecover.setPassword(passwordRecoveryRequest.newPassword());
            userRepository.save(userToRecover);
            userRepository.delete(temporaryUser);
            return "Success";
        }
        throw new TokenExpiredException();
    }


    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
