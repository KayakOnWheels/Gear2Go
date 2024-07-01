package com.gear2go.dto.request.user;

public record UpdateUserRequest(Long id, String firstName, String lastName, String mail, String password) {
}
