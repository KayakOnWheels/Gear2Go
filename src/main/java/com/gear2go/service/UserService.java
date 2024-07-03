package com.gear2go.service;

import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.User;
import com.gear2go.mapper.UserMapper;
import com.gear2go.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    UserRepository userRepository;
    UserMapper userMapper;

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
    
}
