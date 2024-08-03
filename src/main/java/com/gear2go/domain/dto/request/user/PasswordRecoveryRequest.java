package com.gear2go.domain.dto.request.user;

public record PasswordRecoveryRequest(String token, String mail, String oldPassword, String newPassword) {
}
