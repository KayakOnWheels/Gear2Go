package com.gear2go.domain.dto.request.user;

public record UpdateUserRequest(Long id, String firstName, String lastName, String mail, String password) {
}
