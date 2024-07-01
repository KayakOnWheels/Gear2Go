package com.gear2go.mapper;

import com.gear2go.dto.response.UserResponse;
import com.gear2go.entity.User;

import java.util.List;

public class UserMapper {


    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMail()
        );
    }

    public List<UserResponse> toUserList(List<User> user) {
        return user.stream()
                .map(this::toUserResponse)
                .toList();
    }
}
