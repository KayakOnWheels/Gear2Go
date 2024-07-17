package com.gear2go.service;

import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.request.user.UserCredentialsRequest;
import com.gear2go.dto.response.LoginValidationStatusResponse;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.User;
import com.gear2go.mapper.UserMapper;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse getUser(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow());
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.mail(),
                createUserRequest.password()
        );

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(updateUserRequest.id()).orElseThrow();

        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setMail(updateUserRequest.mail());
        user.setPassword(updateUserRequest.password());

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    public LoginValidationStatusResponse loginValidation(UserCredentialsRequest userCredentialsRequest) {

        User user = userRepository.findUserByMail(userCredentialsRequest.mail()).orElseThrow();

        if(user.getPassword().equals(userCredentialsRequest.password())) {
            SecureRandom random = new SecureRandom();
            Integer authToken = random.nextInt();
            user.setAuthToken(authToken);
            userRepository.save(user);

            return new LoginValidationStatusResponse("OK");
        }
        return new LoginValidationStatusResponse("Bad Credentials");
    }
    
}
