package com.gear2go.domain.dto.request.user;

public record CreateUserRequest(String firstName, String lastName, String mail, String password) {
}
