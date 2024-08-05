package com.gear2go.dto.request.user;

public record PasswordRecoveryRequest(String token, String newPassword, String mail) {
}
