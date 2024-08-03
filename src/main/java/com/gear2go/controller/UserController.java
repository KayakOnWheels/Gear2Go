package com.gear2go.controller;

import com.gear2go.dto.request.RequestPasswordRecoveryRequest;
import com.gear2go.dto.request.user.CreateUserRequest;
import com.gear2go.dto.request.user.UpdateUserRequest;
import com.gear2go.dto.response.UserResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/request-recovery")
    public ResponseEntity<String> sendRecoveryMail(@RequestBody RequestPasswordRecoveryRequest requestPasswordRecoveryRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(userService.sendRecoveryMail(requestPasswordRecoveryRequest));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(null);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
