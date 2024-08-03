package com.gear2go.service;

import com.gear2go.domain.Mail;
import com.gear2go.dto.request.RequestPasswordRecoveryRequest;
import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.User;
import com.gear2go.entity.enums.Role;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.exception.UserNotFoundException;
import com.gear2go.mapper.UserMapper;
import com.gear2go.properties.Endpoints;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final Endpoints endpoints;

    public List<UserResponse> getAllUsers() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse getUser(Long id) throws UserNotFoundException{
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.mail(),
                createUserRequest.password(),
                Role.USER
        );

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest) throws ExceptionWithHttpStatusCode {
        String mail;
        Authentication authentication = authenticationService.getAuthentication();
        User userToUpdate = userRepository.findById(updateUserRequest.id()).orElseThrow(UserNotFoundException::new);

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            mail = authentication.getName();
            User currentUser = userRepository.findUserByMail(mail).orElseThrow(UserNotFoundException::new);

            if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getMail().equals(userToUpdate.getMail())) {
                userToUpdate.setFirstName(updateUserRequest.firstName());
                userToUpdate.setLastName(updateUserRequest.lastName());
                userToUpdate.setMail(updateUserRequest.mail());
                userToUpdate.setPassword(updateUserRequest.password());
                userRepository.save(userToUpdate);
                return userMapper.toUserResponse(userToUpdate);
            }
            return userMapper.toUserResponse(userToUpdate);
        }
        return userMapper.toUserResponse(userToUpdate);
    }

    public void deleteUser(@Nullable Long id) {
        if (id == null) {
            Authentication authentication = authenticationService.getAuthentication();
            userRepository.deleteUserByMail(authentication.getName());
            return;
        }
        userRepository.deleteById(id);
    }

    public String sendRecoveryMail(RequestPasswordRecoveryRequest requestPasswordRecoveryRequest) throws ExceptionWithHttpStatusCode{
        String token = authenticationService.authenticateGuest().getToken();

        Mail mail = new Mail(requestPasswordRecoveryRequest.mail(), "Forgot Password?", token);

        emailService.send(mail);

        return "Recovery mail has been sent";
    }

}
