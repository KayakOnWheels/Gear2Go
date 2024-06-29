package com.gear2go.dto.request.user;

public record CreateUserRequest(Long id, String firstName, String lastName, String mail, String password) {
}
