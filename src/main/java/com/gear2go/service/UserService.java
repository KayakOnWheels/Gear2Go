package com.gear2go.service;

import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.enums.Role;
import com.gear2go.entity.User;
import com.gear2go.mapper.UserMapper;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
                createUserRequest.password(),
                Role.USER
        );

        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        String mail;
        Authentication authentication = authenticationService.getAuthentication();
        User userToUpdate = userRepository.findById(updateUserRequest.id()).orElseThrow();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            mail = authentication.getName();
            User currentUser = userRepository.findUserByMail(mail).orElseThrow();

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

    public void deleteUser(Long id) {
        String mail;
        Authentication authentication = authenticationService.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            mail = authentication.getName();
            User currentUser = userRepository.findUserByMail(mail).orElseThrow();
            User userToRemove = userRepository.findById(id).orElseThrow();
            if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getMail().equals(userToRemove.getMail())) {
                userRepository.delete(userToRemove);
            }
        }
    }

}
